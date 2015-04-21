package org.itas.core.resources;

import java.util.List;
import java.util.Map;

import org.itas.core.Pool.ResPool;
import org.itas.core.Resource;

@SuppressWarnings("unchecked")
class ResourcePoolImpl implements ResPool {

  static Map<String, Resource> RES_MAP;
  static Map<Class<?>, List<Resource>> RES_ARRAY_MAP;
  
  private ResourcePoolImpl() {
  }
  
  @Override
	public void reloading() {
		
	}
  
	@Override 
  public <T extends Resource> T get(String Id) {
		if (Id == null || Id.length() == 0) {
		  return null;
		}
			
		return (T)RES_MAP.get(Id);
  }
	
  @Override
  public <T extends Resource> List<T> get(Class<T> clazz) {
    return (List<T>)RES_ARRAY_MAP.get(clazz);
  }
}

