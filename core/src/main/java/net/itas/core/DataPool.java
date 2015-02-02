//package net.itas.core;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import net.itas.core.util.Utils.CoreUtils;
//import net.itas.util.ItasException;
//import net.itas.util.Utils.ClassUtils;
//import net.itas.util.Utils.Objects;
//import net.itas.util.Utils.TimeUtil;
//import net.itas.util.cache.Cache;
//import net.itas.util.cache.LocalCache;
//
//import com.typesafe.config.Config;
//
///**
// * <p>对象池 </p>
// * 
// * @author liuzhen<liuxing521a@163.com>
// * @date 2014-3-19
// */
//final class DataPool {
//
//	/** 字节码操作*/
//	private final ByteCodes operate;
//	
//	/** 数据库操作工具*/
//	private final SQLDirtys sQLTools;
//	
//	/** 模型数据集 */
//	private final Map<Class<?>, GameBase> dataModels;
//	
//	/** 前缀和class映射*/
//	private final Map<String, Class<? extends GameBaseAotuID>> dataStringPrifex;
//
//	/** 对象缓存的缓存 */
//	private final Map<Class<?>, Cache<Integer, GameObject>> dataIntCaches;
//	
//	/** 对象缓存的缓存 */
//	private final Map<Class<?>, Cache<String, GameBaseAotuID>> dataStringCaches;
//	
//
//	private DataPool() {
//		this.dataModels = new HashMap<>();
//		this.dataIntCaches = new HashMap<>();
//		this.dataStringCaches = new HashMap<>();
//		this.dataStringPrifex = new HashMap<>();
//		this.operate = Factory.getInstance(ByteCodes.class);
//		this.sQLTools = Factory.getInstance(SQLDirtys.class);
//	}
//
//	void bind(Config config, String pack) throws Exception {
//		int  capicity = config.getInt("capicity");
//		long lifeTime = config.getLong("aliveTime")*TimeUtil.MILLIS_PER_HOUR;
//		
//		List<Class<?>> classList = operate.loadDynamicClass(pack);
//		for (Class<?> clazz : classList) {
//			if (ClassUtils.isExtends(clazz, GameObject.class)) {
//				bindGameBaseInt(clazz, capicity, lifeTime);
//			} else if (ClassUtils.isExtends(clazz, GameBaseAotuID.class)) {
//				bindGameBaseString(clazz, capicity, lifeTime);
//			} else {
//				throw new ItasException("error:class must extends from Game[" + clazz.getName() + "]");
//			}
//		}
//	}
//	
//
//	Collection<GameBase> getModelList() {
//		return Collections.unmodifiableCollection(dataModels.values());
//	}
//
//	void destoryed() {
//		dataModels.clear();
//		dataIntCaches.clear();
//		dataStringCaches.clear();
//	}
//
//	protected void put(GameObject data) {
//		dataIntCaches.get(data.getClass()).put(data.getId(), data);
//	}
//	
//	protected void put(GameBaseAotuID data) {
//		dataStringCaches.get(data.getClass()).put(data.getId(), data);
//	}
//
//	protected GameObject get(Class<? extends GameObject> clazz, Integer Id) {
//		GameObject data = loadData(clazz, Id);
//		
//		if (Objects.nonNull(data)) {
//			data.setUpdateTime(TimeUtil.systemTime());
//		} else {
//			GameObject mould = (GameObject)dataModels.get(clazz);
//			data = mould.autoGen(Id);
//		}
//
//		return data;
//	}
//
//	protected GameBaseAotuID get(Class<? extends GameBaseAotuID> clazz, String Id) {
//		GameBaseAotuID data = loadData(clazz, Id);
//		if (Objects.nonNull(data)) {
//			data.setUpdateTime(TimeUtil.systemTime());
//		}
//		
//		return data;
//	}
//
//	protected GameBaseAotuID get(String Id) {
//		GameBaseAotuID data = loadData(parsePrifex(Id), Id);
//		if (Objects.nonNull(data)) {
//			data.setUpdateTime(TimeUtil.systemTime());
//		}
//	
//		return data;
//	}
//
//	
//	protected GameObject remove(Class<? extends GameObject> clazz, Integer Id) {
//		return dataIntCaches.get(clazz).remove(Id);
//	}
//	
//	protected GameBaseAotuID remove(Class<? extends GameBaseAotuID> clazz, String Id) {
//		return dataStringCaches.get(clazz).remove(Id);
//	}
//
//	
//	protected GameObject newInstance(Class<? extends GameObject> clazz, Integer Id) {
//		synchronized (clazz) {
//			Cache<Integer, GameObject> cache = dataIntCaches.get(clazz);
//			
//			GameObject data = cache.get(Id);
//			if (Objects.nonNull(data)) {
//				return data;
//			}
//			
//			data = (GameObject)dataModels.get(clazz).clone(Id);
//			data.initialize0();
//			cache.put(data.getId(), data);
//		 
//			return data;
//		}
//	}
//	
//	protected GameBaseAotuID newInstance(Class<? extends GameBaseAotuID> clazz, String Id) {
//		synchronized (clazz) {
//			Cache<String, GameBaseAotuID> cache = dataStringCaches.get(clazz);
//			
//			GameBaseAotuID data;
//			if (Objects.nonEmpty(Id) && Objects.nonNull(data = cache.get(Id))) {
//				return data;
//			} 
//			
//			data = (GameBaseAotuID)dataModels.get(clazz).clone(Id);
//			data.initialize0();
//			cache.put(data.getId(), data);
//			return data;
//		}
//	}
//	
//	// ===================================================
//	/**
//	 * <p>数据库加载数据 </P>
//	 * @param clazz 加载的类
//	 * @param Id   参数
//	 * @return 加载后数据，如果数据库不存在返回null
//	 */
//	private GameObject loadData(Class<? extends GameObject> clazz, Integer Id) {
//		if (Id == 0)  {
//			return null;
//		}
//		
//		Cache<Integer, GameObject> cache = dataIntCaches.get(clazz);
//		GameObject data = cache.get(Id);
//		if (Objects.nonNull(data)) {
//			return data;
//		}
//		 
//		return loadGameBaseFromDB(cache, clazz, Id);
//	}
//	
//	/**
//	 * <p>数据库加载数据 </P>
//	 * @param clazz 加载的类
//	 * @param Id   参数
//	 * @return 加载后数据，如果数据库不存在返回null
//	 */
//	private GameBaseAotuID loadData(Class<? extends GameBaseAotuID> clazz, String Id) {
//		if (Objects.isEmpty(Id) || Objects.isNull(clazz)) {
//			return null;
//		}
//		
//		Cache<String, GameBaseAotuID> cache = dataStringCaches.get(clazz);
//		GameBaseAotuID data = cache.get(Id);
//		if (Objects.nonNull(data)) {
//			return data;
//		}
//		 
//		return loadGameBaseStringFromDB(cache, clazz, Id);
//	}
//	
//	private GameObject loadGameBaseFromDB(Cache<Integer, GameObject> cache, Class<?> clazz, Integer Id) {
//		synchronized (Id) {
//			GameBase tmp = sQLTools.loadData(dataModels.get(clazz), Id);
//			if (Objects.isNull(tmp)) {
//				return null;
//			}
//			
//			GameObject data = (GameObject)tmp;
//			cache.putIfAbsent(data.getId(), data);
//			return data;
//		}
//	}
//	
//	private GameBaseAotuID loadGameBaseStringFromDB(Cache<String, GameBaseAotuID> cache, Class<?> clazz, String Id) {
//		synchronized (Id) {
//			GameBase tmp = sQLTools.loadData(dataModels.get(clazz), Id);
//			if (Objects.isNull(tmp)) {
//				return null;
//			}
//			
//			GameBaseAotuID data = (GameBaseAotuID)tmp;
//			cache.putIfAbsent(data.getId(), data);
//			return data;
//		}
//	}
//	
//	
//	private void bindGameBaseInt(Class<?> clazz, int capicity, long lifeTime) {
//		GameObject data = CoreUtils.newInstanceInt(clazz, 0);
//		Cache<Integer, GameObject> cache = new LocalCache<>(clazz.getSimpleName(), data.getCachedSize()*capicity, lifeTime);
//		dataModels.put(clazz, data);
//		dataIntCaches.put(clazz, cache);
//		sQLTools.initDataStruct(clazz);
//	}
//	
//	private void bindGameBaseString(Class<?> clazz, int capicity, long lifeTime) {
//		GameBaseAotuID data = CoreUtils.newInstanceString(clazz, "");
//		Cache<String, GameBaseAotuID> cache = new LocalCache<>(clazz.getSimpleName(), data.getCachedSize()*capicity, lifeTime + TimeUtil.MILLIS_PER_HOUR);
//		dataModels.put(clazz, data);
//		dataStringPrifex.put(data.PRIFEX(), data.getClass());
//		dataStringCaches.put(clazz, cache);
//		sQLTools.initDataStruct(clazz);
//	}
//
//	private Class<? extends GameBaseAotuID> parsePrifex(String Id) {
//		if (Objects.isEmpty(Id)) {
//			return null;
//		}
//		
//		int index = Id.indexOf('_');
//		if (index != 2) {
//			throw new ItasException("PRIFEX must like:[ps_]");
//		}
//		
//		return dataStringPrifex.get(Id.substring(0, index + 1));
//	}
//}
