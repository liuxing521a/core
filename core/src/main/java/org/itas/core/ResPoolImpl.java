package org.itas.core;

import static org.itas.core.bytecode.Type.resourceType;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.itas.core.Pool.ResPool;
import org.itas.core.XmlInfo.XmlInfoBuilder;
import org.itas.core.util.ClassLoaders;
import org.itas.core.util.Constructors;
import org.itas.util.ItasException;
import org.itas.util.Logger;
import org.itas.util.Utils.Objects;

@SuppressWarnings("unchecked")
final class ResPoolImpl implements ResPool, ClassLoaders, Constructors {

  /** 所有对象集合*/
  private static Map<String, Resource> allResources;
	
  /** 分类列表*/
  private static Map<Class<?>, List<Resource>> childResources;
  
  private ResPoolImpl(List<Resource> resourceList) {
	checkIsNull();
	initialized(resourceList);
  }

  @Override
  public void setUP(Called... back) {
	final List<XmlInfo> loads = new ArrayList<>();
	final Map<String, Resource> resMap = new HashMap<>();

	Class<?>[] classList = loadClass("");
	for (final Class<?> clazz : classList) {
	  if (!resourceType.is(clazz) ||
	      Modifier.isAbstract(clazz.getModifiers())) {
		continue;
	  }
	  
	  final XmlHandler handle = new ResourceHandler((Class<? extends Resource>)clazz);
	  final List<Map<String, String>> attributes = handle.parse(back[0].callBack());
      final XmlInfoBuilder xmlInfoBuild = XmlInfo.newXmlInfoBuilder()
            .setHandle(handle);
      
      Resource source;
      for (Map<String, String> attribute : attributes) {
		source = newInstance(clazz, new Object[]{attribute.get("Id")});
		XmlInfo xmlInfo = xmlInfoBuild.addAttribute(attribute)
		      .addXmlBean(source).builder();
		loads.add(xmlInfo);
		
		if (Objects.nonNull(resMap.put(source.getId(), source))) {
		  throw new DoubleException("class:" + source.getClass().getSimpleName() + 
		      " and class:" + clazz.getSimpleName() + " with same id=" + source.getId());
		}
	  }
	}
	
	final Map<Class<?>, List<Resource>> typeResMap = new HashMap<>();
		
	try {
	  for (XmlInfo xmlInfo : loads) {
	    for (int i = 0; i < xmlInfo.beanSize(); i++) {
	      xmlInfo.getXmlBean(i).load(
	          xmlInfo.getHandle().getFields(), xmlInfo.getAttribute(i));
	    }
		  
		  typeResMap.put(xmlInfo.getHandle().getClazz(), 
		      Collections.unmodifiableList(xmlInfo.getXmlBean()));
	  }
	} catch (Exception e) {
	  throw new ItasException(e);
	}
	
	allResources = Collections.unmodifiableMap(resMap);
	childResources = Collections.unmodifiableMap(typeResMap);
	
	Logger.trace("res size is :{}", allResources.size());		
  }

  @Override
  public void destoried() {
	allResources = null;
	childResources = null;
  }
  
  public Resource get(String Id) {
	if (Id == null || Id.length() == 0) {
	  return null;
	}
		
	return allResources.get(Id);
  }

  public List<Resource> get(Class<? extends Resource> clazz) {
    return childResources.get(clazz);
  }
  
  public static final class ResPoolImplBuilder implements Builder {

	@Override
	public ResPoolImpl builder() {
		return new ResPoolImpl(null);
	}
  }
  
  private void checkIsNull() {
    if (allResources != null || childResources != null) {
      throw new ItasException("respool can't initialized again...");
    } 
  }
  
  private void initialized(List<Resource> resourceList) {
    final Map<String, Resource> resMap = new HashMap<>();
    final Map<Class<?>, List<Resource>> childReMap = new HashMap<>();
	 
	Resource old;
	for (Resource resource : resourceList) {
	  old = resMap.put(resource.getId(), resource);
	  
	  if (old != null) {
	    throw new DoubleException("class:" + resource.getClass().getSimpleName() + 
	        " and class:" + old.getClass().getSimpleName() + " with same id=" + old.getId());
	  }
	}
  }
  
}

class XmlInfo {
	
  private final XmlHandler handle;
  private final List<Resource> xmlBean;
  private final List<Map<String, String>> attributes;
	
  private XmlInfo(XmlHandler handle, 
	  List<Resource> xmlBean, List<Map<String, String>> attributes) {
    this.handle = handle;
	this.xmlBean = xmlBean;
	this.attributes = attributes;
  }
  
  public int beanSize() {
	return attributes.size();
  }
	
  public XmlHandler getHandle() {
	return handle;
  }
	
  public Map<String, String> getAttribute(int index) {
	  return attributes.get(index);
  }
  
  public Resource getXmlBean(int index) {
	  return xmlBean.get(index);
  }

  public List<Map<String, String>> getAttributes() {
	return attributes;
  }
	
  public List<Resource> getXmlBean() {
	return xmlBean;
  }
	
  public static XmlInfoBuilder newXmlInfoBuilder(){
	 return new XmlInfoBuilder();
  } 
  
  public static class XmlInfoBuilder implements Builder {

	private XmlHandler handle;
	private List<Resource> xmlBean;
	private List<Map<String, String>> attributes;
	
	public XmlInfoBuilder setHandle(XmlHandler handle) {
	  this.handle =  handle;
	  return this;
	}
	
	public XmlInfoBuilder addXmlBean(Resource res) {
	  xmlBean.add(res);
	  return this;
	}
	
	public XmlInfoBuilder addAttribute(Map<String, String> attributeMap) {
	  attributes.add(attributeMap);
	  return this;
	}
	
	XmlInfoBuilder() {
	  xmlBean = new ArrayList<>();
	  attributes = new ArrayList<>();
	}

	@Override
	public XmlInfo builder() {
	  return new XmlInfo(handle, xmlBean, attributes);
	}
  }
}