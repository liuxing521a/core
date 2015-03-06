package org.itas.core;

import java.util.HashMap;
import java.util.Map;

import org.itas.core.Pool.DataPool;
import org.itas.core.util.Constructors;
import org.itas.util.ItasException;
import org.itas.util.Utils.Objects;
import org.itas.util.Utils.TimeUtil;
import org.itas.util.cache.Cache;
import org.itas.util.cache.LocalCache;

import com.google.common.collect.Maps;
import com.typesafe.config.Config;

/**
 * <p>对象池 </p>
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014-3-19
 */
abstract class DataPoolImpl implements Constructors, DataPool {

  /** 数据库操作工具*/
  private final DBSync dbSync;
	
  /** 模型数据集 */
  private final Map<Class<?>, GameObject> classModules;
	
  /** 前缀和class映射*/
  private final Map<String, GameObject> prefixModules;

  /** 对象缓存的缓存 */
  private final Map<String, Cache<String, GameObject>> dataCaches;
  
  /** 在缓存中最大存放数量*/
  private final long capacity;

  /** 缓存生命周期*/
  private final long lifeTime;
	
  private DataPoolImpl(DBSync dbSync, long capacity, long lifeTime) {
	this.classModules = Maps.newHashMap();
	this.prefixModules = new HashMap<>();
	this.dataCaches = new HashMap<>();
	this.dbSync = dbSync;
	this.capacity = capacity;
	this.lifeTime = lifeTime * TimeUtil.MILLIS_PER_HOUR;
  }
  
  @Override
  public void setUP(Called...back) {
	final GameObject module = back[0].callBack();
	Cache<String, GameObject> cache = new LocalCache<>(
		module.getClass().getSimpleName(), module.getCachedSize()*this.capacity, this.lifeTime);
	classModules.put(module.getClass(), module);
	prefixModules.put(module.PRIFEX(), module);
	dataCaches.put(module.PRIFEX(), cache);
  }

  @Override
  public void destoried() {
	prefixModules.clear();
    classModules.clear();
    dataCaches.clear();
  }
  
  @Override
  public void put(GameObject data) {
	obtainCache(data).put(data.getId(), data);
  }

  @Override
  public GameObject get(String Id) {
	  GameObject gameObject = loadGameObject(Id);
	  if (Objects.nonNull(gameObject)) {
		  gameObject.setUpdateTime(TimeUtil.systemTime());
	  } else {
		  gameObject = obtainModule(Id).autoInstance(Id);
	  }
	  
	  return gameObject;
  }
  
  @Override
  public GameObject get(Class<? extends GameObject> clazz, String Id) {
	GameObject gameObject = loadGameObject(clazz, Id);
	if (Objects.nonNull(gameObject)) {
	  gameObject.setUpdateTime(TimeUtil.systemTime());
	} else {
		gameObject = obtainModule(clazz).autoInstance(Id);
	}

	return gameObject;
  }

  @Override
  public GameObject remove(String Id) {
	  return obtainCache(obtainModule(Id)).remove(Id);
  }

  @Override
  public GameObject remove(Class<? extends GameObject> clazz, Integer Id) {
	return obtainCache(obtainModule(clazz)).remove(Id);
  }
	
  @Override
  public GameObject newInstance(Class<? extends GameObject> clazz, String Id) {
	  return newInstance(obtainModule(clazz), Id);
  }
	
  @Override
  public GameObject newInstance(String Id) {
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
	  gameObject.initialize();
	  ((AbstractDBSync)dbSync).addInsert(gameObject);
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
  
  public static DataPoolBuilder makeBuilder() {
	return new DataPoolBuilder();
  }
  
  public static class DataPoolBuilder implements Builder, Constructors {

	private Config share;
	
	private DBSync dbSync;
	
	private DataPoolBuilder() {
	}
	
	public DataPoolBuilder setShared(Config share) {
		this.share = share;
		return this;
	}
	
	public DataPoolBuilder setDbSync(DBSync dbSync) {
	  this.dbSync = dbSync;
	  return this;
	}

	@Override
	public DataPool builder() {
	  return new DataPoolImpl(dbSync,
	      share.getLong("capicity"), share.getLong("aliveTime")){ };
	}
  }
  
}
