package org.itas.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.itas.core.cache.CacheAble;
import org.itas.core.resources.Element;
import org.itas.util.ItasException;

public class Pool {
	
  /** 数据池*/
  private static final DataPool dataPool;

  /** 数据池*/
  private static final ResPool resPool;
	
  static {
  	resPool = Ioc.getInstance(ResPool.class);
  	dataPool = Ioc.getInstance(DataPool.class);
  }
  
  public static <T extends GameBase> T getModule(String Id) {
  	return dataPool.getModule(Id);
  }

  public static <T extends GameBase> T getModule(Class<T> clazz) {
  	return dataPool.getModule(clazz);
  }
  
  public static <T extends CacheAble> T get(String Id) {
  	return dataPool.get(Id);
  }

  public static <T extends CacheAble> T get(Class<T> clazz, String Id) {
  	return dataPool.get(clazz, Id);
  }

  public static boolean isCached(String Id) {
  	return dataPool.isCached(Id);  
  }
  
  public static <T extends CacheAble> boolean isCached(Class<T> clazz, String Id) {
  	return dataPool.isCached(clazz, Id); 
  }

  public static <T extends CacheAble> T remove(String Id) {
  	return dataPool.remove(Id); 
  }

  public static <T extends CacheAble> T remove(Class<T> clazz, String Id) {
  	return dataPool.remove(clazz, Id); 
  }
	  
  public static <T extends CacheAble> T newInstance(String Id) {
	  return dataPool.newInstance(Id);
  }
  
  public static <T extends CacheAble> T newInstance(Class<T> clazz, String Id) {
  	return dataPool.newInstance(clazz, Id);
  }
		
  public static <T extends Resource> T getResource(String Rid) {
    return resPool.get(Rid);
	}
	
  public static <T extends Resource> List<T> getResource(Class<T> clazz) {
		return resPool.get(clazz);
  }
	
  public interface DBPool {

  	Connection getConnection() throws SQLException;
		
  }
	
  public interface DataPool {
	  
		void put(CacheAble data);
		
	  <T extends GameBase> T getModule(String Id);

	  <T extends GameBase> T getModule(Class<T> clazz);
		  
		<T extends CacheAble> T get(String Id);
		  
		<T extends CacheAble> T get(Class<T> clazz, String Id);
	
		boolean isCached(String Id);
	
		<T extends CacheAble> boolean isCached(Class<T> clazz, String Id);
		
		<T extends CacheAble> T remove(String Id);
	
		<T extends CacheAble> T remove(Class<T> clazz, String Id);
		  
		<T extends CacheAble> T newInstance(String Id);

		<T extends CacheAble> T newInstance(Class<T> clazz, String Id);
	  
  }
  
  public interface ResPool {

  	<T extends Resource> T get(String rid);
  	
  	<T extends Resource> List<T> get(Class<T> clazz);
  	
  }

  public interface ConfigPool {
	  
  }
  
  private Pool() {
  	throw new ItasException("can't create instance...");
  }
}
