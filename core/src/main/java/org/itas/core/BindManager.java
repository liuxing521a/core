package org.itas.core;

import static org.itas.core.bytecode.Type.resourceType;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import org.itas.core.BindManager.ModuleBinding.ModuleBindingBuilder;
import org.itas.core.BindManager.XmlConfigBinding.ConfigBindingBuilder;
import org.itas.core.OnService.Called;
import org.itas.core.Pool.DataPool;
import org.itas.core.XmlInfo.XmlInfoBuilder;
import org.itas.core.bytecode.ByteCodes;
import org.itas.core.bytecode.ByteCodes.ClassType;
import org.itas.core.util.Constructors;
import org.itas.util.Utils.Objects;

public final class BindManager {

  public static ModuleBindingBuilder makeModuleBinding() {
	return new ModuleBindingBuilder();
  }
  
  public static ConfigBindingBuilder makeConfigBinding() {
	return new ConfigBindingBuilder();
  }
  
  protected abstract static class BindingImpl implements Builder {
	  
	protected Class<?> parent;
	
	protected String pack;

	public BindingImpl setParent(Class<?> parent) {
	  this.parent = parent;
	  return this;
	}

	public BindingImpl setPack(String pack) {
	  this.pack = pack;
	  return this;
	}
	  
  }
	
  static class ModuleBinding implements Constructors, Binding {

    private final Class<?> parent;
    private final String pack;
    private final DBSync dbSync;
    private final DataPool dataPool;
	
    private ModuleBinding(Class<?> parent, 
        String pack, DBSync dbSync, DataPool dataPool) {
	  this.parent = parent;
      this.pack = pack;
	  this.dbSync = dbSync;
	  this.dataPool = dataPool;
    }
	 
    @Override
    public void bind() throws Exception {
	  final List<Class<?>> classList = 
	      ByteCodes.loadClass(parent, pack, ClassType.CTCLASS);
	 
	  for (final Class<?> clazz : classList) {
		final GameObject gameObject = newInstance(clazz, new Object[]{""});
	   
		dataPool.setUP(new Called() {
		  @Override @SuppressWarnings("unchecked")
		  public <T> T callBack() {
			return (T)gameObject;
		  }
		});
		
	    dbSync.setUP(new Called() {
	      @Override @SuppressWarnings("unchecked")
		  public <T> T callBack() {
		    return (T)gameObject.getClass();
		  }
		});
	    
	    dbSync.createTable(gameObject);
	    dbSync.alterTable(gameObject);
	  }
    }
	
    public static class ModuleBindingBuilder extends BindingImpl {

	  private DBSync dbSync;
		 
	  private DataPool dataPool;
		
	  private ModuleBindingBuilder() {
	  }
		
	  public ModuleBindingBuilder setDbSync(DBSync dbSync) {
	    this.dbSync = dbSync;
	    return this;
	  }

	  public ModuleBindingBuilder setDataPool(DataPool dataPool) {
	    this.dataPool = dataPool;
	    return this;
	  }
	  
	  @Override
	  public ModuleBindingBuilder setParent(Class<?> parent) {
	    this.parent = parent;
	    return this;
	  }

	  @Override
	  public ModuleBindingBuilder setPack(String pack) {
	    this.pack = pack;
	    return this;
	  }

	  public Binding builder() {
	    return new ModuleBinding(parent, pack, dbSync, dataPool);
	  }
    }
  }
  
  static class XmlConfigBinding implements Constructors, Binding {
	  
    private final Class<?> parent;
    private final String pack;
	
    private XmlConfigBinding(Class<?> parent, String pack) {
	  this.parent = parent;
      this.pack = pack;
    }

	@Override
	public void bind() throws Exception {
	  final List<Class<?>> classList = 
          ByteCodes.loadClass(parent, pack, ClassType.CLASS);
	   
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
		
	}
	
    public static class ConfigBindingBuilder extends BindingImpl {

	  private ConfigBindingBuilder() {
	  }
		
	  @Override
	  public ConfigBindingBuilder setParent(Class<?> parent) {
	    this.parent = parent;
	    return this;
	  }

	  @Override
	  public ConfigBindingBuilder setPack(String pack) {
	    this.pack = pack;
	    return this;
	  }

	  public Binding builder() {
	    return new XmlConfigBinding(parent, pack);
	  }
    }
  }
  
  private static class ResourceBinding {

	public void bind(Class<?> clazz, String pack) {
		
	}
	  
  }
}
