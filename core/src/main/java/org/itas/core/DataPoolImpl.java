package org.itas.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.itas.core.Pool.DataPool;
import org.itas.core.util.Constructors;
import org.itas.util.ItasException;
import org.itas.util.Utils.Objects;
import org.itas.util.Utils.TimeUtil;
import org.itas.util.cache.Cache;
import org.itas.util.cache.LocalCache;

import com.typesafe.config.Config;

/**
 * <p>对象池 </p>
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014-3-19
 */
@SuppressWarnings("unchecked")
final class DataPoolImpl implements Constructors, DataPool, Binding {

  /** 数据库操作工具*/
  private final DBSync dbSync;
  
  /** 在缓存中最大存放数量*/
  private final long capacity;

  /** 缓存生命周期*/
  private final long lifeTime;
	
  /** 模型数据集 */
  private static Map<Class<?>, GameObject> classModules;
	
  /** 前缀和class映射*/
  private static Map<String, GameObject> prefixModules;

  /** 对象缓存的缓存 */
  private static Map<String, Cache<String, GameObject>> dataCaches;
  
	
  private DataPoolImpl(DBSync dbSync, long capacity, long lifeTime) {
	this.dbSync = dbSync;
	this.capacity = capacity;
	this.lifeTime = lifeTime * TimeUtil.MILLIS_PER_HOUR;
  }
  
  @Override
  public void bind(Called back) {
	checkInitialized();
	final List<GameObject> gameObjects = back.callBack();
	
	final int size = gameObjects.size();
	final Map<Class<?>, GameObject> clsMap = new HashMap<>(size);
	final Map<String, GameObject> prefixMap = new HashMap<>(size);
	final Map<String, Cache<String, GameObject>> cacheMap = new HashMap<>(size);
	for (final GameObject gameObject : gameObjects) {
	  final Cache<String, GameObject> cache = new LocalCache<>(
	      gameObject.getClass().getSimpleName(), 
		  gameObject.getCachedSize()*this.capacity, this.lifeTime);
	  
	  clsMap.put(gameObject.getClass(), gameObject);
	  prefixMap.put(gameObject.PRIFEX(), gameObject);
	  cacheMap.put(gameObject.PRIFEX(), cache);
	}
	
	classModules = Collections.unmodifiableMap(clsMap);
	prefixModules = Collections.unmodifiableMap(prefixMap);
	dataCaches = Collections.unmodifiableMap(cacheMap);
  }

  @Override
  public void unBind() {
	prefixModules = null;
    classModules = null;
    dataCaches = null;
  }
  
  @Override
  public void put(GameObject data) {
	obtainCache(data).put(data.getId(), data);
  }

  
  @Override
  public <T extends GameObject> T get(String Id) {
	  GameObject gameObject = loadGameObject(Id);
	  if (Objects.nonNull(gameObject)) {
		  gameObject.setUpdateTime(TimeUtil.systemTime());
	  } else {
		  gameObject = obtainModule(Id).autoInstance(Id);
	  }
	  
	  return (T) gameObject;
  }
  
  @Override
  public <T extends GameObject> T get(Class<? extends GameObject> clazz, String Id) {
	GameObject gameObject = loadGameObject(clazz, Id);
	if (Objects.nonNull(gameObject)) {
	  gameObject.setUpdateTime(TimeUtil.systemTime());
	} else {
		gameObject = obtainModule(clazz).autoInstance(Id);
	}

	return (T) gameObject;
  }

  @Override
  public boolean isCached(String Id) {
	final GameObject module = obtainModule(Id);
	return obtainCache(module).containsKey(Id);
  }

  @Override
  public boolean isCached(Class<? extends GameObject> clazz, String Id) {
	final GameObject module = obtainModule(clazz);
	return obtainCache(module).containsKey(Id);
  }
	
  @Override
  public <T extends GameObject> T remove(String Id) {
	  return (T) obtainCache(obtainModule(Id)).remove(Id);
  }

  @Override
  public <T extends GameObject> T remove(Class<? extends GameObject> clazz, String Id) {
	return (T) obtainCache(obtainModule(clazz)).remove(Id);
  }
	
  @Override
  public <T extends GameObject> T newInstance(Class<? extends GameObject> clazz, String Id) {
	  return (T) newInstance(obtainModule(clazz), Id);
  }
	
  @Override
  public <T extends GameObject> T newInstance(String Id) {
	return (T) newInstance(obtainModule(Id), Id);
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
  
  private void checkInitialized() {
	if (classModules != null || prefixModules != null || dataCaches != null) {
	  throw new DoubleException("can't initialized agin");
	}
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
	      share.getLong("capicity"), share.getLong("aliveTime"));
	}
  }
  
}
