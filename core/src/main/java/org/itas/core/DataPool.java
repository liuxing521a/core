package org.itas.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.itas.core.bytecode.Type;
import org.itas.core.util.Constructors;
import org.itas.util.ItasException;
import org.itas.util.Utils.Objects;
import org.itas.util.Utils.TimeUtil;
import org.itas.util.cache.Cache;
import org.itas.util.cache.LocalCache;

import com.google.common.collect.Maps;

/**
 * <p>对象池 </p>
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014-3-19
 */
final class DataPool implements Constructors {

  /** 数据库操作工具*/
  private DBSync dbSync;
	
  /** 模型数据集 */
  private final Map<Class<?>, GameObject> classModules;
	
  /** 前缀和class映射*/
  private final Map<String, GameObject> prefixModules;

  /** 对象缓存的缓存 */
  private final Map<String, Cache<String, GameObject>> dataCaches;
	
  private DataPool() {
	classModules = Maps.newHashMap();
	prefixModules = new HashMap<>();
	dataCaches = new HashMap<>();
  }

  void bind(List<Class<? extends GameObject>> classList) throws Exception {
	int  capicity = Integer.parseInt(System.getProperty("capicity"));
	long lifeTime = 
	    Long.parseLong(System.getProperty("aliveTime"))*TimeUtil.MILLIS_PER_HOUR;

	for (Class<?> clazz : classList) {
      if (Type.gameObjectType.is(clazz)) {
    	GameObject gameObject = newInstance(clazz, new Object[]{""});
//		Cache<String, GameObject> cache = new LocalCache<>(clazz.getSimpleName(), 1024, lifeTime + TimeUtil.MILLIS_PER_HOUR);
		Cache<String, GameObject> gameObjectCache = new LocalCache<>(
				clazz.getSimpleName(), capicity*1024, lifeTime);
		classModules.put(clazz, gameObject);
		prefixModules.put(gameObject.PRIFEX(), gameObject);
		dataCaches.put(gameObject.PRIFEX(), gameObjectCache);
		continue;
      } 

      throw new ItasException(
    	  "error:class must extends from Game[" + clazz.getName() + "]");
	}
  }

  protected void put(GameObject data) {
	obtainCache(data).put(data.getId(), data);
  }

  protected GameObject get(String Id) {
	  GameObject gameObject = loadGameObject(Id);
	  if (Objects.nonNull(gameObject)) {
		  gameObject.setUpdateTime(TimeUtil.systemTime());
	  } else {
		  gameObject = obtainModule(Id).autoInstance(Id);
	  }
	  
	  return gameObject;
  }
  
  protected GameObject get(Class<? extends GameObject> clazz, String Id) {
	GameObject gameObject = loadGameObject(clazz, Id);
	if (Objects.nonNull(gameObject)) {
	  gameObject.setUpdateTime(TimeUtil.systemTime());
	} else {
		gameObject = obtainModule(clazz).autoInstance(Id);
	}

	return gameObject;
  }

  protected GameObject remove(String Id) {
	  return obtainCache(obtainModule(Id)).remove(Id);
  }

  protected GameObject remove(Class<? extends GameObject> clazz, Integer Id) {
	return obtainCache(obtainModule(clazz)).remove(Id);
  }
	
  protected GameObject newInstance(Class<? extends GameObject> clazz, String Id) {
	  return newInstance(obtainModule(clazz), Id);
  }
	
  protected GameObject newInstance(String Id) {
	return newInstance(obtainModule(Id), Id);
  }
  
  private GameObject newInstance(GameObject module, String Id) {
	Cache<String, GameObject> gameObjectCache = obtainCache(module);
	synchronized (module) {
	  GameObject gameObject = gameObjectCache.get(Id);
	  if (Objects.nonNull(gameObject)) {
		return gameObject;
	  }			
				
	  gameObject = module.clone(Id);
//	  data.initialize0(); TODO init data
	  gameObjectCache.put(gameObject.getId(), gameObject);
	  return gameObject;
	}
  }
	
  private GameObject loadGameObject(Class<? extends GameObject> clazz, String Id) {
	return loadGameObject(obtainModule(clazz), Id);
  }
	
  private GameObject loadGameObject(String Id) {
    return loadGameObject(obtainModule(Id), Id);
  }
  
  private GameObject loadGameObject(GameObject module, String Id) {
	Cache<String, GameObject> gameObjectCache = obtainCache(module);
	GameObject gameObject = gameObjectCache.get(Id);
	if (Objects.nonNull(gameObject)) {
	  return gameObject; 
	}
	
	synchronized (Id.intern()) {
	  gameObject = dbSync.loadData(module, Id);
		if (Objects.isNull(gameObject)) {
			return null;
		}
			
		gameObjectCache.putIfAbsent(gameObject.getId(), gameObject);
		return gameObject;
	}
  }

  private GameObject obtainModule(String Id) {
	if (Id == null || Id.length() < 3) {
	  return null;
	}
		
	String prifex = Id.substring(0, 3);
	if (prifex.charAt(2) != '_') {
	  throw new IllegalArgumentException("PRIFEX must endwith [_]");
	}
		
	GameObject module = prefixModules.get(prifex);
	if (Objects.isNull(module)) {
	  throw new ItasException("illness Id:" + Id);
	}
	
	return module;
  }
  
  private GameObject obtainModule(Class<? extends GameObject> clazz) {
	GameObject module = classModules.get(clazz);
	
	if (Objects.isNull(module)) {
	  throw new IllegalAccessError("unkown class type:" + clazz.getName());
	}
	
	return module;
  }
  
  private Cache<String, GameObject> obtainCache(GameObject module) {
	Cache<String, GameObject> gameObjectCache = dataCaches.get(module.PRIFEX());
	if (Objects.isNull(gameObjectCache)) {
	  throw new NullPointerException("class cache:" + module.getClass());
	}
	
	return gameObjectCache;
  }
  
}
