package org.itas.core.resource;

import static org.itas.core.bytecode.Type.booleanType;
import static org.itas.core.bytecode.Type.byteType;
import static org.itas.core.bytecode.Type.charType;
import static org.itas.core.bytecode.Type.doubleType;
import static org.itas.core.bytecode.Type.enumByteType;
import static org.itas.core.bytecode.Type.enumIntType;
import static org.itas.core.bytecode.Type.enumStringType;
import static org.itas.core.bytecode.Type.floatType;
import static org.itas.core.bytecode.Type.intType;
import static org.itas.core.bytecode.Type.listType;
import static org.itas.core.bytecode.Type.longType;
import static org.itas.core.bytecode.Type.mapType;
import static org.itas.core.bytecode.Type.resourceType;
import static org.itas.core.bytecode.Type.setType;
import static org.itas.core.bytecode.Type.shortType;
import static org.itas.core.bytecode.Type.stringType;

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

import org.itas.core.EnumByte;
import org.itas.core.EnumString;
import org.itas.core.Enums;
import org.itas.core.IllegaException;
import org.itas.core.Pool;
import org.itas.core.annotation.Clazz;
import org.itas.util.ItasException;
import org.itas.util.Utils.ClassUtils;
import org.itas.util.Utils.Objects;

abstract class AbstractXml implements Enums {
	
  @SuppressWarnings("unchecked")
  void fill(Field field, String text) throws Exception {
	if (booleanType.is(field.getType())) {
	  field.setBoolean(this, parseBoolean(text));
	} else if (byteType.is(field.getType())) {
	  field.setByte(this, parseByte(text));
	} else if (charType.is(field.getType())) {
	  field.setChar(this, parseChar(text));
	} else if (shortType.is(field.getType())) {
	  field.setShort(this, parseShort(text));
	} else if (intType.is(field.getType())) {
	  field.setInt(this, parseInt(text));
	} else if (longType.is(field.getType())) {
	  field.setLong(this, parseLong(text));
	} else if (floatType.is(field.getType())) {
	  field.setFloat(this, parseFloat(text));
	} else if (doubleType.is(field.getType())) {
	  field.setDouble(this, parseDouble(text));
	} else if (stringType.is(field.getType())) {
	  field.set(this, text);
	} else if (resourceType.is(field.getType())) {
	  field.set(this, Pool.getResource(text));
	} else if (enumByteType.is(field.getType())) {
	  field.set(this, parse(field.getType(), parseByte(text)));
	} else if (enumIntType.is(field.getType())) {
	  field.set(this, parse(field.getType(), parseInt(text)));
	} else if (enumStringType.is(field.getType())) {
      field.set(this, parse(field.getType(), text));
	} else if (setType.is(field.getType())) {
	  field.set(this, processCollection(field, text));
	} else if (listType.is(field.getType())) {
	  field.set(this, processCollection(field, text));
	} else if (mapType.is(field.getType())) {
	  Map<Object, Object> map = processorMap(field, text);
	  field.set(this, Collections.unmodifiableMap(map));
	} else {
	  throw new ItasException("unSupported:[type:" + field.getType() + "]");
	}
  }
	
  private boolean parseBoolean(String text) {
    if (text == null || text.length() == 0) {
	  return false;
    }
  
    byte value = Byte.parseByte(text);
    if (value == 0) {
	  return false;
    }
  
    if (value == 1) {
	  return true;
    }
  
    throw new IllegaException("boolean type must 0|1");
  }
  
  private byte parseByte(String text) {
	if (text == null || text.length() == 0) {
	  return 0;
	}
	
	return Byte.parseByte(text);
  }
  
  private char parseChar(String text) {
	if (text == null || text.length() == 0) {
	  return ' ';
	}
	
	return text.charAt(0);
  }
  
  private short parseShort(String text) {
	if (text == null || text.length() == 0) {
	  return 0;
	}
	
	return Short.parseShort(text);
  }
  
  private int parseInt(String text) {
	if (text == null || text.length() == 0) {
	  return 0;
	}
		
	return Integer.parseInt(text);
  }
	
  private long parseLong(String text) {
    if (text == null || text.length() == 0) {
	  return 0L;
	}
			  
    return Long.parseLong(text);
  }
  
  private float parseFloat(String text) {
    if (text == null || text.length() == 0) {
	  return 0.0F;
	}
			  
    return Float.parseFloat(text);
  }
  
  private double parseDouble(String text) {
    if (text == null || text.length() == 0) {
	  return 0.0D;
	}
			  
    return Double.parseDouble(text); 
  }
  
  
  
  
  
  
  @SuppressWarnings("unchecked")
	public Map<Object, Object> processorMap(Field field, String text) throws Exception {
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
	public Collection<Object> processCollection(Field field, String text)
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
	private Object convertValue(Type genericType, String value) {
		
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
		} else if (EnumByte.class.isAssignableFrom((Class<?>)genericType)) {
			return parse((Class<?>)genericType, Byte.valueOf(value));
		} else if (EnumString.class.isAssignableFrom((Class<?>)genericType)) {
			return parse((Class<? extends EnumString>)genericType, value);
		} else {
			throw new RuntimeException("reflect unSupported type:["	+ genericType + "]");
		}
	}
	
	private Type getGenericType(Field field) {
		try {
			return getGenericTypes(field)[0];
		} catch (ClassCastException e) {
			throw new RuntimeException(field.getName()	+ " is not generic type:", e);
		}
	}

	private Type[] getGenericTypes(Field field) {
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
	
	private void splitCollection(Collection<Object> c, Type genericType, String strs) {
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
	
	private Map<Object, Object> fillMap(Map<Object, Object> map, Field field, String text) {
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
  
}
