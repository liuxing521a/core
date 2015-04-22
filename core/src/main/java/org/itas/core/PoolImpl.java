package org.itas.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.itas.core.Pool.DataPool;
import org.itas.core.Pool.ResPool;
import org.itas.core.cache.Cache;
import org.itas.core.cache.CacheAble;
import org.itas.core.cache.LocalCache;
import org.itas.core.resources.XmlInfo;
import org.itas.core.util.Constructors;
import org.itas.util.ItasException;
import org.itas.util.Logger;
import org.itas.util.Utils.Objects;
import org.itas.util.Utils.TimeUtil;

import com.typesafe.config.Config;

class PoolImpl {
	
}

final class DataPoolImpl implements Constructors, DataPool {

  /** 数据库操作工具*/
  private final DBSync dbSync;
  
  /** 在缓存中最大存放数量*/
  private final long capacity;

  /** 缓存生命周期*/
  private final long lifeTime;
	
  /** 模型数据集 */
  private Map<Class<?>, GameBase> classModules;
	
  /** 前缀和class映射*/
  private Map<String, GameBase> prefixModules;

  /** 对象缓存的缓存 */
  private Map<String, Cache<String, CacheAble>> dataCaches;
  
	
  private DataPoolImpl(DBSync dbSync, long capacity, long lifeTime) {
		this.dbSync = dbSync;
		this.capacity = capacity;
		this.lifeTime = lifeTime * TimeUtil.MILLIS_PER_HOUR;
  }
  
  @Override
  public void bind(Called back) {
		checkInitialized();
		final List<GameBase> gameObjects = back.callBack();
		
		final int size = gameObjects.size();
		final Map<Class<?>, CacheAble> clsMap = new HashMap<>(size);
		final Map<String, CacheAble> prefixMap = new HashMap<>(size);
		final Map<String, Cache<String, CacheAble>> cacheMap = new HashMap<>(size);
		for (final GameBase gameObject : gameObjects) {
		  final Cache<String, CacheAble> cache = new LocalCache<>(
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
  public void put(CacheAble data) {
  	obtainCache(data).put(data.getId(), data);
  }

  
  @Override
  public <T extends CacheAble> T get(String Id) {
	  GameObject gameObject = loadGameObject(Id);
	  if (Objects.nonNull(gameObject)) {
		  gameObject.setUpdateTime(TimeUtil.systemTime());
	  } else {
		  gameObject = obtainModule(Id).autoInstance(Id);
	  }
	  
	  return (T) gameObject;
  }
  
  @Override
  public <T extends CacheAble> T get(Class<T> clazz, String Id) {
  	CacheAble gameObject = loadGameObject(clazz, Id);
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
		  ((DBSyner)dbSync).addInsert(gameObject);
		  gameObjectCache.put(gameObject.getId(), gameObject);
		  return gameObject;
		}
  }
	
  private <T extends CacheAble> loadGameObject(Class<T> clazz, String Id) {
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

  private <T extends GameBase> T obtainModule(String Id) {
		if (Id == null || Id.length() < 3) {
		  return null;
		}
		
		String prifex = Id.substring(0, 3);
		if (prifex.charAt(2) != '_') {
		  throw new IllegalArgumentException("PRIFEX must endwith [_]");
		}
			
		GameBase module = prefixModules.get(prifex);
		if (Objects.isNull(module)) {
		  throw new ItasException("illness Id:" + Id);
		}
		
		return (T) module;
  }
  
  private <T extends GameBase> T  obtainModule(Class<T> clazz) {
  	final GameBase module = classModules.get(clazz);
		
		if (Objects.isNull(module)) {
		  throw new IllegalAccessError("unkown class type:" + clazz.getName());
		}
		
		return (T) module;
	}
	  
	  private Cache<String, CacheAble> obtainCache(CacheAble module) {
		final Cache<String, CacheAble> gameObjectCache = dataCaches.get(module.PRIFEX());
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

class ResPoolImpl implements ResPool, Constructors {

  /** 所有对象集合*/
  private static Map<String, Resource> XML_MAP;
	
  /** 分类列表*/
  private static Map<Class<?>, List<Resource>> XML_ARRAY_MAP;
  
  private ResPoolImpl(Map<String, Resource> maps, Map<Class<?>, List<Resource>> arrayMaps) {
  	synchronized (ResPoolImpl.class) {
  		checkInitialized();
  		XML_MAP = Collections.unmodifiableMap(maps);
  		XML_ARRAY_MAP = Collections.unmodifiableMap(arrayMaps);
		}
  }

  @Override
  public void bind() throws Exception {
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
      	childresMap.put(childList.get(0).getClass(), 
      	    Collections.unmodifiableList(childList));
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
  	XML_MAP = null;
  	XML_ARRAY_MAP = null;
  }
  
  @Override
  public <T extends Resource> T get(String Id) {
		if (Id == null || Id.length() == 0) {
		  return null;
		}
			
		return (T)XML_MAP.get(Id);
  }
	
  @Override
  public <T extends Resource> List<T> get(Class<T> clazz) {
    return (List<T>)XML_ARRAY_MAP.get(clazz);
  }
	  
  private void checkInitialized() {
		if (XML_MAP != null || XML_ARRAY_MAP != null) {
		  throw new DoubleException("can't initialized agin");
		}
  }
  
  public static ResPoolImplBuilder makeBuilder() {
  	return new ResPoolImplBuilder();
  }
  
  public static class ResPoolImplBuilder implements Builder {
	
  	private Map<String, Resource> mapa;
  	
  	private Map<Class<?>, List<Resource>> maps;
  	
		private ResPoolImplBuilder() {
		}
	
		@Override
		public ResPool builder() {
		  return new ResPoolImpl(mapa, maps);
		}
  }
}
