package org.itas.core.util;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import org.itas.core.EnumByte;
import org.itas.core.EnumInt;
import org.itas.core.EnumString;
import org.itas.util.ItasException;

import com.google.common.collect.Maps;

/**
 * 支持枚举处理
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年3月10日上午11:01:53
 */
public interface Enums {

  default <T extends Enum<T>> T parse(Class<?> clazz, byte key) {
	return EnumParse.parse(clazz, key);
  }
  
  default <T extends Enum<T>> T parse(Class<?> clazz, int key) {
	return EnumParse.parse(clazz, key);
  }
  
  default <T extends Enum<T>> T parse(Class<?> clazz, String key) {
    return EnumParse.parse(clazz, key);
  }
	
  @SuppressWarnings("unchecked")
  static class EnumParse {
		
    private static final Map<Class<?>, Map<Byte, Enum<?>>> enumBytes;
		
	private static final Map<Class<?>, Map<Integer, Enum<?>>> enumInts;
		
	private static final Map<Class<?>, Map<String, Enum<?>>> enumStrings;
		
	static {
	  enumBytes = Maps.newHashMap();
	  enumInts = Maps.newHashMap();
	  enumStrings = Maps.newHashMap();
	}
	
	static <T extends Enum<T>> T parse(Class<?> clazz, byte key) {
	  Map<Byte, Enum<?>> enumMap = enumBytes.get(clazz);
	  if (enumMap == null) {
		  enumMap = cachedEnumByte((Class<T>)clazz);
	  }
	  
	  return (T)enumMap.get(key);
	}

	static <T extends Enum<T>> T parse(Class<?> clazz, int key) {
	  Map<Integer, Enum<?>> enumMap = enumInts.get(clazz);
	  if (enumMap == null) {
	    enumMap = cachedEnumInt((Class<T>)clazz);
	  }
		  
	  return (T)enumMap.get(key);
	}
	
	static <T extends Enum<T>> T parse(Class<?> clazz, String key) {
	  Map<String, Enum<?>> enumMap = enumStrings.get(clazz);
	  if (enumMap == null) {
		enumMap = cachedEnumString((Class<T>)clazz);
	  }
			  
	  return (T)enumMap.get(key);
	}
	
    private static <T extends Enum<T>> Map<Byte, Enum<?>> cachedEnumByte(Class<T> clazz) {
	  final EnumSet<? extends Enum<?>> enumSet = EnumSet.allOf((Class<T>)clazz);
		
	  final Map<Byte, Enum<?>> enums = new HashMap<Byte, Enum<?>>(enumSet.size());
	  for (Enum<?> e : enumSet) {
	    if (e instanceof EnumByte) {
	      enums.put(((EnumByte)e).key(), e);
	      continue; 
	    } 

	    throw new ItasException("enum auto complate must be implements org.itas.core.EnumByte");
	  }
		
	  enumBytes.putIfAbsent(clazz, enums);
	  return enums;
    }
    
    private static <T extends Enum<T>>  Map<Integer, Enum<?>> cachedEnumInt(Class<T> clazz) {
  	  final EnumSet<? extends Enum<?>> enumSet = EnumSet.allOf((Class<T>)clazz);
  		
  	  final Map<Integer, Enum<?>> enums = new HashMap<Integer, Enum<?>>(enumSet.size());
  	  for (Enum<?> e : enumSet) {
  	    if (e instanceof EnumInt) {
  	      enums.put(((EnumInt)e).key(), e);
  	      continue; 
  	    } 

  	    throw new ItasException("enum auto complate must be implements org.itas.core.EnumInt");
  	  }
  		
  	  enumInts.putIfAbsent(clazz, enums);
  	  return enums;
    }
    
    private static <T extends Enum<T>> Map<String, Enum<?>> cachedEnumString(Class<T> clazz) {
	  final EnumSet<? extends Enum<?>> enumSet = EnumSet.allOf((Class<T>)clazz);
		
	  final Map<String, Enum<?>> enums = new HashMap<String, Enum<?>>(enumSet.size());
	  for (Enum<?> e : enumSet) {
	    if (e instanceof EnumInt) {
	      enums.put(((EnumString)e).key(), e);
	      continue; 
	    } 

	    throw new ItasException("enum auto complate must be implements org.itas.core.EnumString");
	  }
		
	  enumStrings.putIfAbsent(clazz, enums);
	  return enums;
    }
    
    private EnumParse() {
    }
  }
}
