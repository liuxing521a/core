package org.itas.core;

import static org.itas.core.bytecode.Type.resourceType;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.itas.core.BindManager.ModuleBinding.ModuleBindingBuilder;
import org.itas.core.BindManager.XmlResourceBinding.ConfigBindingBuilder;
import org.itas.core.Binding.Called;
import org.itas.core.XmlInfo.XmlInfoBuilder;
import org.itas.core.bytecode.ByteCodes;
import org.itas.core.bytecode.ByteCodes.ClassType;
import org.itas.core.util.Constructors;

public class BindManager {

  public static ModuleBindingBuilder makeModuleBinding() {
	return new ModuleBindingBuilder();
  }
  
  public static ConfigBindingBuilder makeConfigBinding() {
	return new ConfigBindingBuilder();
  }
  
  public interface BeanBinding {
	
	abstract void bind() throws Exception;
	
	abstract void unBind() throws Exception;
  
  }
  
  abstract static class BindingImplBuilder implements Builder {
	  
	protected Class<?> parent;
	
	protected String pack;
	  
  }
	
  static class ModuleBinding implements Constructors, BeanBinding {

    private final Class<?> parent;
    private final String pack;
    private final DBSync dbSync;
    private final Binding binding;
	
    private ModuleBinding(Class<?> parent, 
        String pack, DBSync dbSync, Binding dataPool) {
	  this.parent = parent;
      this.pack = pack;
	  this.dbSync = dbSync;
	  this.binding = dataPool;
    }
	 
    @Override @SuppressWarnings("unchecked")
    public void bind() throws Exception {
	  final List<Class<?>> classList = 
	      ByteCodes.loadClass(parent, pack, ClassType.CTCLASS);
	 
	  final List<GameObject> gameObjects = new ArrayList<>(classList.size());
	  GameObject gameObject;
	  for (final Class<?> clazz : classList) {
		gameObject = newInstance(clazz, new Object[]{""});
		gameObjects.add(gameObject);
	  }
	  
	  dbSync.bind(new Called() {
		@Override
		public <T> T callBack() {
		  return (T) gameObjects;
		}
	  });
	  binding.bind(new Called() {
		@Override
		public <T> T callBack() {
		  return (T) gameObjects;
		}
	  });
	  
	  dbSync.createTable(gameObjects);
	  dbSync.alterTable(gameObjects);
    }
    
    @Override
    public void unBind() throws Exception {
	  dbSync.unBind();
    }
	
    public static class ModuleBindingBuilder extends BindingImplBuilder {

	  private DBSync dbSync;
		 
	  private Binding binding;
		
	  private ModuleBindingBuilder() {
	  }
		
	  public ModuleBindingBuilder setDbSync(DBSync dbSync) {
	    this.dbSync = dbSync;
	    return this;
	  }

	  public ModuleBindingBuilder setBinding(Binding binding) {
	    this.binding = binding;
	    return this;
	  }
	  
	  public ModuleBindingBuilder setParent(Class<?> parent) {
	    this.parent = parent;
	    return this;
	  }

	  public ModuleBindingBuilder setPack(String pack) {
	    this.pack = pack;
	    return this;
	  }

	  public BeanBinding builder() {
	    return new ModuleBinding(parent, pack, dbSync, binding);
	  }
    }
  }
  
  static class XmlResourceBinding implements Constructors, BeanBinding {
	  
    private final Class<?> parent;
    private final String pack;
    private final Binding binding;
	
    private XmlResourceBinding(Class<?> parent, String pack, Binding binding) {
	  this.parent = parent;
	  this.pack = pack;
      this.binding = binding;
    }

	@SuppressWarnings("unchecked")
	@Override
	public void bind() throws Exception {
	  final List<Class<?>> classList = 
          ByteCodes.loadClass(parent, pack, ClassType.CLASS);
	  
	  final List<XmlInfo> xmlInfos = new ArrayList<>(classList.size());
	  
	  for (final Class<?> clazz : classList) {
	    if (!resourceType.is(clazz) ||
	        Modifier.isAbstract(clazz.getModifiers())) {
		  continue;
	    }
		  
	    final XmlHandler handle = new ResourceHandler((Class<? extends Resource>)clazz);
	    final XmlInfoBuilder xmlInfoBuild = XmlInfo.newXmlInfoBuilder();
		xmlInfoBuild.setHandle(handle);
	      
		Resource source;
		final List<Map<String, String>> attributes = handle.parse(pack);
		for (Map<String, String> attribute : attributes) {
		  source = newInstance(clazz, new Object[]{attribute.get("Id")});
		  xmlInfoBuild.addAttribute(attribute).addXmlBean(source);
		}
		
		xmlInfos.add(xmlInfoBuild.builder());
	  }
		
	  binding.bind(new Called() {
		@Override
		public <T> T callBack() {
		  return (T) xmlInfos;
		}
	  });
	}
	
	@Override
	public void unBind() throws Exception {
	  binding.unBind();
	}
	
    public static class ConfigBindingBuilder extends BindingImplBuilder {

	  private ConfigBindingBuilder() {
	  }
	  
	  private Binding binding;
		
	  public ConfigBindingBuilder setParent(Class<?> parent) {
	    this.parent = parent;
	    return this;
	  }

	  public ConfigBindingBuilder setPack(String pack) {
	    this.pack = pack;
	    return this;
	  }
	  
	  public ConfigBindingBuilder setBinding(Binding binding) {
	    this.binding = binding;
	    return this;
	  }

	  public BeanBinding builder() {
	    return new XmlResourceBinding(parent, pack, binding);
	  }
    }
  }
  
  private static class XmlConfigBinding {

	public void bind(Class<?> clazz, String pack) {
		
	}
	  
  }
}
