package org.itas.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.itas.util.ItasException;

@SuppressWarnings("unchecked")
public final class Pool implements Ioc {
	
  /** 数据池*/
  private static final DataPool dataPool;

  /** 数据池*/
  private static final ResPool resPool;
	
  static {
	resPool = Ioc.getInstance(ResPool.class);
	dataPool = Ioc.getInstance(DataPool.class);
  }
	
  
  public static <T extends GameObject> T get(String Id) {
	return (T)dataPool.get(Id);
  }
	  
  public static <T extends GameObject> T get(
      Class<? extends GameObject> clazz, String Id) {
    return (T)dataPool.get(clazz, Id);
  }

  public static boolean isCached(String Id) {
	return dataPool.isCached(Id);  
  }

  public static boolean isCached(Class<? extends GameObject> clazz, String Id) {
	return dataPool.isCached(clazz, Id); 
  }
	
  public static <T extends GameObject> T remove(String Id) {
	return (T)dataPool.remove(Id); 
  }

  public static <T extends GameObject> T remove(
      Class<? extends GameObject> clazz, String Id) {
	return (T)dataPool.remove(clazz, Id); 
  }
	  
  public static <T extends GameObject> T newInstance(String Id) {
	  return (T)dataPool.newInstance(Id);
  }
  
  public static <T extends GameObject> T newInstance(
      Class<? extends GameObject> clazz, String Id) {
	return (T)dataPool.newInstance(clazz, Id);
  }
		
  public static <T extends Resource> T getResource(String Rid) {
    Resource res = resPool.get(Rid);
	if (res == null) {
		return null;
	}

	return (T)res;
  }

  public static <T extends Resource> List<T> getResource(Class<T> clazz) {
	return (List<T>)resPool.get(clazz);
  }
	
  public interface DBPool {

	abstract Connection getConnection() throws SQLException;
		
	abstract void shutdown();
		
  }
	
  public interface DataPool extends Binding {
	  
	abstract void put(GameObject data);
	  
	abstract <T extends GameObject> T get(String Id);
	  
	abstract <T extends GameObject> T get(Class<? extends GameObject> clazz, String Id);

	abstract boolean isCached(String Id);

	abstract boolean isCached(Class<? extends GameObject> clazz, String Id);
	
	abstract <T extends GameObject> T remove(String Id);

	abstract <T extends GameObject> T remove(Class<? extends GameObject> clazz, String Id);
	  
	abstract <T extends GameObject> T newInstance(Class<? extends GameObject> clazz, String Id);
		
	abstract <T extends GameObject> T newInstance(String Id);
	  
  }
  
  public interface ConfigPool {
	  
  }
  
  public interface ResPool extends Binding {
	  
	abstract Resource get(String rid);
	  
	abstract List<Resource> get(Class<? extends Resource> clazz);
	  
  }
  
  private Pool() {
	throw new ItasException("can't create instance...");
  }
}
