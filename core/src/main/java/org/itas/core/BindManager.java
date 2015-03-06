package org.itas.core;

import java.util.List;

import org.itas.core.BindManager.ModuleBinding.ModuleBindingBuilder;
import org.itas.core.OnService.Called;
import org.itas.core.Pool.DataPool;
import org.itas.core.bytecode.ByteCodes;
import org.itas.core.bytecode.ByteCodes.ClassType;
import org.itas.core.util.Constructors;

public final class BindManager {

  public static ModuleBindingBuilder makeModuleBinding() {
	return new ModuleBindingBuilder();
  }
	
  static class ModuleBinding implements Constructors, Binding {

    private final DBSync dbSync;
	 
    private final DataPool dataPool;
	
    private ModuleBinding(DBSync dbSync, DataPool dataPool) {
	  this.dbSync = dbSync;
	  this.dataPool = dataPool;
    }
	 
    @Override
    public void bind(Class<?> parent, String pack) throws Exception {
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
	
    public static class ModuleBindingBuilder implements Builder {

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

	  public ModuleBinding builder() {
	    return new ModuleBinding(dbSync, dataPool);
	  }
    }
  }
  
  private static class XmlConfigBinding  {

	public void bind(Class<?> clazz, String pack) {
		
	}
  }
  
  private static class ResourceBinding {

	public void bind(Class<?> clazz, String pack) {
		
	}
	  
  }
}
