package org.itas.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.itas.core.Pool;
import org.itas.core.enums.EByte;
import org.itas.core.enums.EString;
import org.itas.core.resource.Resource;
import org.itas.core.util.Utils.EnumUtils;
import org.itas.util.Utils.ClassUtils;
import org.itas.util.Utils.Objects;

import net.itas.core.annotation.Clazz;

/**
 * <p>泛型处理</p>
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014-3-19
 */
public final class GenericUtil {
	
	
	@SuppressWarnings("unchecked")
	public static Map<Object, Object> processorMap(Field field, String text) throws Exception {
		if (text == null) {
			text = "";
		}
		
		Clazz clazz = field.getAnnotation(Clazz.class);

		Map<Object, Object> map;
		if (clazz == null) {
			map = new HashMap<Object, Object>();
		} else {
			map = (Map<Object, Object>) clazz.value().newInstance();
		}
		
		return fillMap(map, field, text);
	}


	@SuppressWarnings("unchecked")
	public static Collection<Object> processCollection(Field field, String text)
	throws Exception  {
		text = Objects.isNull(text) ? "" : text;
		
		Clazz column = field.getAnnotation(Clazz.class);
		
		Collection<Object> c;
		if (Objects.isNull(column)) {
			if (ClassUtils.isExtends(field.getType(), List.class)) {
				c = new ArrayList<>();
			} else {
				c = new HashSet<>();
			}
			;
		} else {
			c = (Collection<Object>)column.value().newInstance();
		}

		splitCollection(c, getGenericType(field), text);
		
		if (c instanceof List) {
			return Collections.unmodifiableList((List<?>)c);
		} else {
			return Collections.unmodifiableSet((Set<?>)c);
		}
	}

	@SuppressWarnings("unchecked")
	private static Object convertValue(Type genericType, String value) {
		
		if (genericType == Byte.class) {
			return Integer.valueOf(value).byteValue();
		} else if (genericType == Short.class) {
			return Short.valueOf(value);
		} else if (genericType == Integer.class) {
			return Integer.valueOf(value);
		} else if (genericType == Long.class) {
			return Long.valueOf(value);
		} else if (genericType == Float.class) {
			return Float.valueOf(value);
		} else if (genericType == Double.class) {
			return Double.valueOf(value);
		} else if (genericType == Character.class) {
			return value.charAt(0);
		} else if (genericType == Boolean.class) {
			return Boolean.valueOf(value);
		} else if (genericType == String.class)	{
			return value;
		} else if (Resource.class.isAssignableFrom((Class<?>)genericType)) {
			return Pool.getResource(value);
		} else if (EByte.class.isAssignableFrom((Class<?>)genericType)) {
			return EnumUtils.parse((Class<? extends EByte>)genericType, Byte.valueOf(value));
		} else if (EString.class.isAssignableFrom((Class<?>)genericType)) {
			return EnumUtils.parse((Class<? extends EString>)genericType, value);
		} else {
			throw new RuntimeException("reflect unSupported type:["	+ genericType + "]");
		}
	}
	
	private static Type getGenericType(Field field) {
		try {
			return getGenericTypes(field)[0];
		} catch (ClassCastException e) {
			throw new RuntimeException(field.getName()	+ " is not generic type:", e);
		}
	}

	private static Type[] getGenericTypes(Field field) {
		try {
			ParameterizedType type = (ParameterizedType) field.getGenericType();
			Type[] types = type.getActualTypeArguments();
			Type[] typeList = new Type[types.length];
			
			int index = 0;
			for (Type t : types) {
				if (t instanceof ParameterizedType) {
					typeList[index ++] = ((ParameterizedType)t).getRawType();
				} else {
					typeList[index ++] = t;
				}
			}
			
			return typeList;
		} catch (ClassCastException e) {
			throw new RuntimeException(field.getName()	+ " is not generic type:", e);
		}
	}
	
	private static void splitCollection(Collection<Object> c, Type genericType, String strs) {
		final char[] chs = strs.toCharArray();
		StringBuffer v = new StringBuffer();
		for (char ch : chs) {
			if (ch == '|') {
				c.add(convertValue(genericType, v.toString()));
				v.setLength(0);
				continue;
			}
			v.append(ch);
		}
		
		if (v.length() > 0) {
			c.add(convertValue(genericType, v.toString()));
		}
	}
	
	private static Map<Object, Object> fillMap(Map<Object, Object> map, Field field, String text) {
		final char[] chs = text.toCharArray();
		StringBuffer k = new StringBuffer();
		StringBuffer v = new StringBuffer();
		Type[] genericType = getGenericTypes(field);
		
		StringBuffer c = k;
		for (char ch : chs) {
			if (ch == ',') {
				c = v;
				continue;
			} 
			
			if (ch == '|') {
				map.put(convertValue(genericType[0], k.toString()), 
						convertValue(genericType[1], v.toString()));
				k.setLength(0);
				v.setLength(0);
				c = k;
				continue;
			}
			c.append(ch);
		}
		
		if (k.length() > 0) {
			map.put(convertValue(genericType[0], k.toString()), 
					convertValue(genericType[1], v.toString()));
		}
		
		return map;
	}

	private GenericUtil() {
	}
}
