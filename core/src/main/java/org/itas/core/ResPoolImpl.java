package org.itas.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.itas.core.Pool.ResPool;
import org.itas.core.util.Constructors;
import org.itas.util.Logger;

final class ResPoolImpl implements ResPool, Binding, Constructors {

  /** 所有对象集合*/
  private static Map<String, Resource> allResources;
	
  /** 分类列表*/
  private static Map<Class<?>, List<Resource>> childResources;
  
  private ResPoolImpl() {
  }

  @Override
  public void bind(Called call) throws Exception {
	checkInitialized();
	final List<XmlInfo> xmlInfos = call.callBack();
	
	final Map<String, Resource> resMap = new HashMap<>();
	final Map<Class<?>, List<Resource>> childresMap = new HashMap<>();

	Resource old;
	for (final XmlInfo xmlInfo : xmlInfos) {
	  
	  final List<Resource> childList = new ArrayList<>(xmlInfo.size());
      for (final Resource xmlBean : xmlInfo.getXmlBeans()) {
	    childList.add(xmlBean);
	    old = resMap.put(xmlBean.getId(), xmlBean);
	    
		if (old != null) {
		  throw new DoubleException("class:[" + xmlBean.getClass().getName() + 
		      "]\n class:[" + old.getClass().getName() + "] with same id=" + old.getId());
		}
	  }
      
      if (!childList.isEmpty()) {
	    childresMap.put(childList.get(0).getClass(), Collections.unmodifiableList(childList));
      }
	}
	
	final Map<Class<?>, List<Resource>> typeResMap = new HashMap<>();
		
	for (final XmlInfo xmlInfo : xmlInfos) {
      xmlInfo.load();
	}
	
	allResources = Collections.unmodifiableMap(resMap);
	childResources = Collections.unmodifiableMap(typeResMap);
	Logger.trace("res size is :{}", allResources.size());		
  }

  @Override
  public void unBind() {
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
  
  private void checkInitialized() {
	if (childResources != null || allResources != null) {
	  throw new DoubleException("can't initialized agin");
	}
  }
  
  public static ResPoolImplBuilder makeBuilder() {
	return new ResPoolImplBuilder();
  }
  
  public static class ResPoolImplBuilder implements Builder {
	
	private ResPoolImplBuilder() {
	}

	@Override
	public ResPool builder() {
	  return new ResPoolImpl();
	}
  }
}

class XmlInfo {
	
  private final XmlHandler handle;
  private List<Resource> xmlBeans;
  private List<Map<String, String>> attributes;
	
  private XmlInfo(XmlHandler handle, 
      List<Resource> xmlBean, List<Map<String, String>> attributes) {
    this.handle = handle;
	this.xmlBeans = xmlBean;
  }
  
  public int size() {
	return xmlBeans.size();
  }
	
  public XmlHandler getHandle() {
	return handle;
  }
	
  public void load() throws Exception {
	Resource resBean;
	for (int i = 0; i < size(); i ++) {
	  resBean = xmlBeans.get(i);
	  resBean.load(handle.getFields(), attributes.get(i));
	}
  }
  
  public List<Resource> getXmlBeans() {
	return xmlBeans;
  }

  public List<Map<String, String>> getAttributes() {
	return attributes;
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