package org.itas.core;

import org.itas.core.cache.CacheAble;

/**
 *<p>简单对象</p>
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014年3月24日
 */
public final class Simple<T extends CacheAble> {
	
  private final String Id;
	
  private final Class<T> clazz;
  
  @SuppressWarnings("unchecked")
	Simple(String Id) {
		this.Id = Id;
		this.clazz = (Class<T>) Pool.getModule(Id).getClass();
  }
	
  public String getId() {
  	return Id;
  }
	
  public T enty() {
  	return Pool.get(clazz, Id);
  }
	
  public Class<T> getClazz() {
  	return clazz;
  }

  @Override
  public boolean equals(Object data) {
		if (data == this) {
		  return true;
		}
			
		if (!(data instanceof Simple)) {
		  return false;
		}
	
		return Id.equals(((Simple<?>)data).Id) &&
				clazz == (((Simple<?>)data).clazz);
  }
	
  @Override
  public int hashCode() {
  	return 31 + Id.hashCode();
  }
	
  @Override
  public String toString() {
  	return String.format("[clazz=%s, Id=%s]", clazz.getName(), Id);
  }
	
}
