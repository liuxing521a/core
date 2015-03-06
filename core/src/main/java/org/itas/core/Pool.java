package org.itas.core;

import java.sql.Connection;
import java.sql.SQLException;

import org.itas.core.resource.Resource;


public interface Pool {
	
	/** 资源池*/
//	private DataPool resPool;

//	/** 数据池*/
//	private static DataPool dataPool;
//	
//	static {
//		resPool = Factory.getInstance(ResPool.class);
//		dataPool = Factory.getInstance(DataPool.class);
//	}
//	
	public static <T extends GameObject> T get(String Id) {
		return  null;
	}
//	@SuppressWarnings("unchecked")
//	public static <T extends GameObject> T get(Class<T> clazz, Integer Id) {
//		return (T) dataPool.get(clazz, Id);
//	}
//
//	@SuppressWarnings("unchecked")
//	public static <T extends GameBaseAotuID> T get(Class<T> clazz, String Id) {
//		return (T) dataPool.get(clazz, Id);
//	}
//
//	@SuppressWarnings("unchecked")
//	public static <T extends GameBaseAotuID> T get(String Id) {
//		return (T) dataPool.get(Id);
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static <T extends GameObject> T remove(Class<T> clazz, Integer Id) {
//		return (T) dataPool.remove(clazz, Id);
//	}
//
//	@SuppressWarnings("unchecked")
//	public static <T extends GameBaseAotuID> T remove(Class<T> clazz, String Id) {
//		return (T) dataPool.remove(clazz, Id);
//	}
//	
//	@SuppressWarnings("unchecked")
//	public static <T extends GameObject> T newInstance(Class<T> clazz, Integer Id) {
//		return (T) dataPool.newInstance(clazz, Id);
//	}
//
//	public static <T extends GameBaseAotuID> T newInstanceString(Class<T> clazz) {
//		return newInstanceString(clazz, null);
//	}
//
//	@SuppressWarnings("unchecked")
//	public static <T extends GameBaseAotuID> T newInstanceString(Class<T> clazz, String Id) {
//		return (T) dataPool.newInstance(clazz, Id);
//	}
//	
	@SuppressWarnings("unchecked")
	public static <T extends Resource> T getResource(String Rid) {
//		Resource resource = resPool.get(Rid);
//		if (Objects.isNull(resource)) {
//			return null;
//		}
//		
//		return (T) resource;
		return null;
	}
//
//	@SuppressWarnings("unchecked")
//	public static <T extends Resource> List<T> getResource(Class<T> clazz) {
//		return (List<T>) resPool.get(clazz);
//	}
	
  public interface DBPool {

	abstract Connection getConnection() throws SQLException;
		
	abstract void shutdown();
		
  }
	
  public interface DataPool extends OnService {
	  
	abstract void put(GameObject data);
	  
	abstract GameObject get(String Id);
	  
	abstract GameObject get(Class<? extends GameObject> clazz, String Id);

	abstract GameObject remove(String Id);

	abstract GameObject remove(Class<? extends GameObject> clazz, Integer Id);
	  
	abstract GameObject newInstance(Class<? extends GameObject> clazz, String Id);
		
	abstract GameObject newInstance(String Id);
	  
  }
}
