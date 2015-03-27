package org.itas.core.util;

import static org.itas.core.bytecode.Type.booleanType;
import static org.itas.core.bytecode.Type.byteType;
import static org.itas.core.bytecode.Type.charType;
import static org.itas.core.bytecode.Type.doubleType;
import static org.itas.core.bytecode.Type.enumByteType;
import static org.itas.core.bytecode.Type.enumIntType;
import static org.itas.core.bytecode.Type.enumStringType;
import static org.itas.core.bytecode.Type.enumType;
import static org.itas.core.bytecode.Type.floatType;
import static org.itas.core.bytecode.Type.intType;
import static org.itas.core.bytecode.Type.longType;
import static org.itas.core.bytecode.Type.resourceType;
import static org.itas.core.bytecode.Type.shortType;
import static org.itas.core.bytecode.Type.stringType;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.itas.core.IllnessException;
import org.itas.core.Pool;
import org.itas.core.Resource;
import org.itas.core.annotation.Clazz;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * 容器支持处理
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年3月10日上午11:02:13
 */
public interface XmlContainers extends Enums {

  default Boolean parseBoolean(String text) {
    if (text == null || text.length() == 0) {
	  return Boolean.FALSE;
    }
	  
    byte value = Byte.parseByte(text);
    if (value == 0) {
    	return Boolean.FALSE;
    }
	  
    if (value == 1) {
    	return Boolean.TRUE;
    }
	  
    throw new IllnessException("boolean type must 0|1");
  }
	  
  default Byte parseByte(String text) {
		if (text == null || text.length() == 0) {
		  return 0;
		}
			
		return Byte.valueOf(text);
  }
	  
  default Character parseChar(String text) {
		if (text == null || text.length() == 0) {
	  	  return ' ';
		}
			
		return text.charAt(0);
  }
	  
  default Short parseShort(String text) {
		if (text == null || text.length() == 0) {
		  return 0;
		}
			
		return Short.valueOf(text);
  }
	  
  default Integer parseInt(String text) {
		if (text == null || text.length() == 0) {
		  return 0;
		}
				
		return Integer.valueOf(text);
  }
		
  default Long parseLong(String text) {
    if (text == null || text.length() == 0) {
    	return 0L;
    }
				  
    return Long.valueOf(text);
  }
	  
  default Float parseFloat(String text) {
    if (text == null || text.length() == 0) {
    	return 0.0F;
    }
				  
    return Float.valueOf(text);
  }
	  
  default Double parseDouble(String text) {
    if (text == null || text.length() == 0) {
    	return 0.0D;
    }
				  
    return Double.valueOf(text); 
  }
  
  default Resource parseResource(String text) {
  	return Pool.getResource(text);
  }
  
  default Timestamp parseTimestamp(String text) {
  	return Timestamp.valueOf(text);
  }
  
  default Map<Object, Object> parseMap(Field field, String text) throws Exception {
  	return ContainerImpl.instance.parseMap(field, text);
  }
  
  default List<Object> parseList(Field field, String text) throws Exception {
  	return ContainerImpl.instance.parseList(field, text);
  }
  
  default Set<Object> parseSet(Field field, String text) throws Exception {
    return ContainerImpl.instance.parseSet(field, text);
  }
	
  @SuppressWarnings("unchecked")
  static class ContainerImpl implements XmlContainers {
    
		private static final ContainerImpl instance = new ContainerImpl();
		  
		public Map<Object, Object> parseMap(Field field, String text) throws Exception {
	      text = checkNull(text);
		
		  final Clazz clazz = field.getAnnotation(Clazz.class);
		  
		  Map<Object, Object> map;
		  if (clazz != null) {
		  	map = (Map<Object, Object>)clazz.value().newInstance();
		  } else {
		  	map = Maps.newHashMap(); 
		  }
		      
		  fill(map, field, text);
		  return Collections.unmodifiableMap(map);
	  }
	
		public List<Object> parseList(Field field, String text) throws Exception {
		  text = checkNull(text);
		  
		  final Clazz clazz = field.getAnnotation(Clazz.class);
		  
		  List<Object> list;
		  if (clazz != null) {
		  	list = (List<Object>)clazz.value().newInstance();
		  } else {
		  	list = Lists.newArrayList(); 
		  }
		      
		  fill(list, field, text);
		  return Collections.unmodifiableList(list);
	  }
	    
		public Set<Object> parseSet(Field field, String text) throws Exception  {
		  text = checkNull(text);
				
		  final Clazz clazz = field.getAnnotation(Clazz.class);
				
		  Set<Object> set;
		  if (clazz != null) {
		    set = (Set<Object>)clazz.value().newInstance();
		  } else {
		  	set = Sets.newHashSet();
		  }
	
		  fill(set, field, text);
		  return Collections.unmodifiableSet(set);
		}
		
	  private void fill(Collection<Object> c, Field field, String content) {
		  final char[] chs = content.toCharArray();
		  final StringBuffer v = new StringBuffer();
				
		  Class<?> genericClazz = getGenericClassArray(field)[0];
		  for (char ch : chs) {
		  	if (ch == '|') {
				  c.add(toValue(genericClazz, v.toString()));
				  v.setLength(0);
				  continue;
		  	}
			
		  	v.append(ch);
		  }
				
		  if (v.length() > 0) {
		  	c.add(toValue(genericClazz, v.toString()));
		  }
		}
			
		private void fill(Map<Object, Object> map, Field field, String text) {
		  final char[] chs = text.toCharArray();
		  final StringBuffer k = new StringBuffer();
		  final StringBuffer v = new StringBuffer();
		  
		  final Class<?>[] genericClazz = getGenericClassArray(field);
				
		  StringBuffer c = k;
		  for (char ch : chs) {
				if (ch == ',') {
				  c = v;
				  continue;
				} 
						
				if (ch == '|') {
				  map.put(toValue(genericClazz[0], k.toString()), 
				      toValue(genericClazz[1], v.toString()));
				  k.setLength(0);
				  v.setLength(0);
				  c = k;
				  continue;
				}
				
				c.append(ch);
		  }
				
		  if (k.length() > 0) {
		  	map.put(toValue(genericClazz[0], k.toString()), 
		        toValue(genericClazz[1], v.toString()));
		  }
		}
	
		@SuppressWarnings("rawtypes")
		private Object toValue(Class<?> clazz, String value) {
		  if (booleanType.isType(clazz)) {
	    	return parseBoolean(value);
		  } else if (byteType.isType(clazz)) {
		  	return parseByte(value);
		  } else if (charType.isType(clazz)) {
		  	return parseChar(value);
		  } else if (shortType.isType(clazz)) {
		  	return parseShort(value);
		  } else if (intType.isType(clazz)) {
		  	return parseInt(value);
		  } else if (longType.isType(clazz)) {
		  	return parseLong(value);
		  } else if (floatType.isType(clazz)) {
		  	return parseFloat(value);
		  } else if (doubleType.isType(clazz)) {
		  	return parseDouble(value);
		  } else if (stringType.isType(clazz))	{
		  	return value;
		  } else if (enumByteType.isType(clazz)) {
		  	return parse((Class<Enum>)clazz, parseByte(value));
		  } else if (enumIntType.isType(clazz)) {
		  	return parse((Class<Enum>)clazz, parseInt(value));
		  } else if (enumStringType.isType(clazz)) {
		  	return parse((Class<Enum>)clazz, value);
		  } else if (enumType.isType(clazz)) {
		  	return parse((Class<Enum>)clazz, value);
		  } else if (resourceType.isType(clazz)) {
		  	return parseResource(value);
		  } else {
		  	throw new RuntimeException("reflect unSupported type:["	+ clazz + "]");
		  }
	  }
			
	  private Class<?>[] getGenericClassArray(Field field) {
		  final ParameterizedType type = (ParameterizedType)field.getGenericType();
		  final Type[] types = type.getActualTypeArguments();
		  final Class<?>[] classArray = new Class<?>[types.length];
					
		  int index = 0;
		  for (Type t : types) {
			classArray[index ++] = (t instanceof ParameterizedType) ?
			    (Class<?>)(((ParameterizedType)t).getRawType()) : (Class<?>)t;
		  }
			
		  return classArray;
		}
	    
	  private String checkNull(String content) {
	    return content == null ? "" : content;
	  }
		
		private ContainerImpl() {
		}
		
	}
	
}
