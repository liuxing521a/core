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
	
}

class EnumParse {
	
  private static final Map<Class<?>, EnumModul> enums;
	
  static {
  	enums = Maps.newHashMap();
  }

  static <E extends Enum<E>> E parse(Class<E> clazz, String key) {
  	EnumModul enumModul = enums.get(clazz);
  	if (enumModul == null) {
  		enumModul = EnumModul.loadEnums(clazz);
  		enums.putIfAbsent(enumModul.getEnumClass(), enumModul);
  	}
  	
  	return enumModul.getEnum(key);
  }

  private EnumParse() {
  	throw new RuntimeException("not supported new instance...");
  }
}

class EnumModul {
	
	private final Class<? extends Enum<?>> enumClass;
	
	private final Class<?> typeClass;
	
	private final Map<Object, Enum<?>> enums;
	
	private EnumModul(Class<? extends Enum<?>> enumClass, 
			Class<?> typeClass, Map<Object, Enum<?>> enums) {
		this.enumClass = enumClass;
		this.typeClass = typeClass;
		this.enums = Collections.unmodifiableMap(enums);
	}
	
	public Class<? extends Enum<?>> getEnumClass() {
		return enumClass;
	}

	public Object toKey(String value) {
		if (!EnumDef.class.isAssignableFrom(enumClass)) {
			return String.class;
		} else if (typeClass == Byte.class) {
			return Byte.valueOf(value);
		} else if (typeClass == Integer.class) {
			return Integer.valueOf(value);
		} else if (typeClass == String.class) {
			return value;
		}

		throw new UnsupportException(" type:" + typeClass);
	}

	@SuppressWarnings("unchecked")
	public <E extends Enum<E>> E getEnum(String key) {
		final Enum<?> e = enums.get(toKey(key));
		if (e == null) {
			return null;
		}
		
		return ((E) e);
	}

	static <E extends Enum<E>> EnumModul loadEnums(Class<E> clazz) {
  	final EnumSet<E> enumSet = EnumSet.allOf(clazz);
		
  	Class<?> typeClass = null;
  	final Map<Object, Enum<?>> enumMap = new HashMap<>(enumSet.size());
  	for (E e : enumSet) {
  		if (e instanceof EnumDef) {
  			enumMap.put(((EnumDef<?>)e).key(), e);
  			typeClass = ((EnumDef<?>) e).key().getClass();
			} else{
  			enumMap.put(e.name(), e);
  			typeClass = String.class;
  		}
  	}
		
		return new EnumModul(clazz, typeClass, enumMap);
	}
	
}
