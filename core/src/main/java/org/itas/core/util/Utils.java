package org.itas.core.util;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;

import org.itas.core.EnumByte;
import org.itas.core.EnumString;
import org.itas.core.annotation.SQLEntity;
import org.itas.util.ItasException;
import org.itas.util.Utils.Objects;

public final class Utils {

	public static final class CtClassUtils {
		
		public static List<CtClass> getAllClass(CtClass clazz) throws Exception {
	        List<CtClass> clazzList = new ArrayList<CtClass>();
	        
	        CtClass objectClass = ClassPool.getDefault().get("java.lang.Object");
	        getSupperClass(clazzList, clazz, objectClass);
	        
	        return clazzList;
	    }

	    private static void getSupperClass(List<CtClass> clazzList, CtClass clazz, CtClass parentClass) throws Exception {
	        if (clazz.equals(parentClass)) {
	            return;
	        }
	        
	        clazzList.add(clazz);
	        getSupperClass(clazzList, clazz.getSuperclass(), parentClass);
	    }
	    
	    public static List<CtField> getAllField(CtClass childClass) throws Exception {
	        List<CtField> fieldList = new ArrayList<CtField>();
	        
	        List<CtClass> supperClazzs = getAllClass(childClass);
	        for (CtClass cls : supperClazzs) {
	        	Objects.addAll(fieldList, cls.getDeclaredFields());
			}

	        return fieldList;
	    }
	}
	
	public static final class EnumUtils {
		/** 保存数据为INT类型*/
		private static final Map<Class<?>, Map<Byte, Enum<?>>> ENUM_MAP_INTEGER = new HashMap<>();
		/** 保存数据为SHORT类型*/
		private static final Map<Class<?>, Map<String, Enum<?>>> ENUM_MAP_STRING = new HashMap<>();
		
		@SuppressWarnings("unchecked")
		public static <T extends Enum<T>> T parse(Class<? extends EnumString> clazz, String key) {
			Map<String, Enum<?>> enumMap = ENUM_MAP_STRING.get(clazz);
			if (Objects.nonNull(enumMap)) {
				return (T) enumMap.get(key);
			}
			
			Map<String, Enum<?>> enumMap1 = new HashMap<>();
			EnumSet<? extends Enum<?>> enums = EnumSet.allOf((Class<T>)clazz);
			for (Enum<?> e : enums) {
				if (e instanceof EnumString) {
					enumMap1.put(((EnumString)e).key(), e);
				} else {
					throw new ItasException("enum auto complate must be implements net.itas.core.EnumString");
				}
			}
			
			ENUM_MAP_STRING.putIfAbsent(clazz, enumMap1);
			return (T) enumMap1.get(key);
		}
		
		@SuppressWarnings("unchecked")
		public static <T extends Enum<T>> T parse(Class<? extends EnumByte> clazz, byte key) {
			Map<Byte, Enum<?>> enumMap1 = ENUM_MAP_INTEGER.get(clazz);
			if (Objects.nonNull(enumMap1)) {
				return (T) enumMap1.get(key);
			}
			
			Map<Byte, Enum<?>> enumMap = new HashMap<>();
			
			EnumSet<? extends Enum<?>> enums = EnumSet.allOf((Class<T>)clazz);
			for (Enum<?> e : enums) {
				if (e instanceof EnumByte) {
					enumMap.put(((EnumByte)e).key(), e);
				} else {
					throw new ItasException("enum auto complate must be implements net.itas.core.EnumByte");
				}
			}
			 
			ENUM_MAP_INTEGER.putIfAbsent(clazz, enumMap);
			return (T) enumMap.get(key);
		}
		
		private EnumUtils() { }
	}
	
	public static final class ByteCodeUtils {
		
		public static String getTableName(CtClass clazz) throws ClassNotFoundException {
			Object sqlEntity = clazz.getAnnotation(SQLEntity.class);
			if (sqlEntity == null) {
				throw new ItasException(String.join("", clazz.getName(), " has not annotation:SQLEntity"));
			}

			return ((SQLEntity)sqlEntity).value();
		}
		
		public static String firstCharUpCase(String text) {
			if (Objects.isEmpty(text)) {
				return text;
			}
			
			String fistKey = text.substring(0, 1).toUpperCase();
			String suffix = text.substring(1, text.length());

			return String.format("%s%s", fistKey, suffix);
		}
		
		public static String uname(CtField field){
			return firstCharUpCase(field.getName());
		}
		
		public static String unameBool(CtField field) {
			String name = field.getName();
			
			if (name.startsWith("is")) {
				return firstCharUpCase(name.substring(2, name.length()));
			}

			return firstCharUpCase(name);
		}
		
		public static String name(CtField field) {
			return field.getName();
		}
		
		private ByteCodeUtils() { }
	}
	
}
