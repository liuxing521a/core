package org.itas.core.util;

import java.lang.reflect.Constructor;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.itas.core.GameBaseAotuID;
import org.itas.core.GameObject;
import org.itas.core.Simple;
import org.itas.core.enums.EByte;
import org.itas.core.enums.EString;
import org.itas.util.ItasException;
import org.itas.util.Pair;
import org.itas.util.Utils.Objects;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import net.itas.core.annotation.SQLEntity;
import net.itas.core.resource.Resource;

public final class Utils {

	public static final class CoreUtils {
		
		@SuppressWarnings("unchecked")
		public static <T> T newInstance(Class<?> cls) {
			try {
				final Constructor<?> cons = cls.getDeclaredConstructor();
				final boolean isAccess = cons.isAccessible();
				
				cons.setAccessible(true);
				T t = (T)cons.newInstance();
				cons.setAccessible(isAccess);
				
				return t;
			} catch (Exception e) {
				throw new ItasException(e);
			}
		}
		
		@SuppressWarnings("unchecked")
		public static <T> T newInstanceInt(Class<?> cls, int paramValue) {
			try {
				final Constructor<?> cons = cls.getDeclaredConstructor(int.class);
				final boolean isAccess = cons.isAccessible();
				
				cons.setAccessible(true);
				T t = (T)cons.newInstance(paramValue);
				cons.setAccessible(isAccess);
				
				return t;
			} catch (Exception e) {
				throw new ItasException(e);
			}
		}
		
		@SuppressWarnings("unchecked")
		public static <T> T newInstanceLong(Class<?> cls, long paramValue) {
			try {
				final Constructor<?> cons = cls.getDeclaredConstructor(long.class);
				final boolean isAccess = cons.isAccessible();
				
				cons.setAccessible(true);
				T t = (T)cons.newInstance(paramValue);
				cons.setAccessible(isAccess);
				
				return t;
			} catch (Exception e) {
				throw new ItasException(e);
			}
		}
		
		@SuppressWarnings("unchecked")
		public static <T> T newInstanceString(Class<?> cls, String paramValue) {
			try {
			    final Constructor<?> cons = cls.getDeclaredConstructor(String.class);
			    final boolean isAccess = cons.isAccessible();
	
			    cons.setAccessible(true);
			    T t = (T)cons.newInstance(paramValue);
			    cons.setAccessible(isAccess);
	
				return t;
			} catch (Exception e) {
				throw new ItasException(e);
			}
	    }
	}
	
	public static final class CtClassUtils {
		
		public static boolean isBasicType(CtField field, Class<?> clazz) throws Exception {
			if (clazz == byte.class) {
				return field.getType() == CtClass.byteType;
			} else if (clazz == char.class) {
				return field.getType() == CtClass.charType;
			} else if (clazz == boolean.class) {
				return field.getType() == CtClass.booleanType;
			} else if (clazz == short.class) {
				return field.getType() == CtClass.shortType;
			} else if (clazz == int.class) {
				return field.getType() == CtClass.intType;
			} else if (clazz == long.class) {
				return field.getType() == CtClass.longType;
			} else if (clazz == float.class) {
				return field.getType() == CtClass.floatType;
			} else if (clazz == double.class) {
				return field.getType() == CtClass.doubleType;
			} else {
				throw new ItasException("type:" + clazz.getName() + "is not java basic type");
			}
		}
		
		public static boolean isBasicType(CtClass ctClass, Class<?> clazz) throws Exception {
			if (clazz == byte.class) {
				return ctClass == CtClass.byteType;
			} else if (clazz == char.class) {
				return ctClass == CtClass.charType;
			} else if (clazz == boolean.class) {
				return ctClass == CtClass.booleanType;
			} else if (clazz == short.class) {
				return ctClass == CtClass.shortType;
			} else if (clazz == int.class) {
				return ctClass == CtClass.intType;
			} else if (clazz == long.class) {
				return ctClass == CtClass.longType;
			} else if (clazz == float.class) {
				return ctClass == CtClass.floatType;
			} else if (clazz == double.class) {
				return ctClass == CtClass.doubleType;
			} else {
				throw new ItasException("type:" + clazz.getName() + "is not java basic type");
			}
		}

		public static boolean isBasicWarpType(CtField field, Class<?> clazz) throws Exception {
			return isBasicWarpType(field.getType(), clazz);
		}

		public static boolean isBasicWarpType(CtClass ctClazz, Class<?> clazz) throws Exception {
			ClassPool clazzPool = ClassPool.getDefault();
			
			if (clazz == byte.class) {
				return ctClazz == clazzPool.get("java.lang.Byte");
			} else if (clazz == char.class) {
				return ctClazz == clazzPool.get("java.lang.Character");
			} else if (clazz == boolean.class) {
				return ctClazz == clazzPool.get("java.lang.Boolean");
			} else if (clazz == short.class) {
				return ctClazz == clazzPool.get("java.lang.Short");
			} else if (clazz == int.class) {
				return ctClazz == clazzPool.get("java.lang.Integer");
			} else if (clazz == long.class) {
				return ctClazz == clazzPool.get("java.lang.Long");
			} else if (clazz == float.class) {
				return ctClazz == clazzPool.get("java.lang.Float");
			} else if (clazz == double.class) {
				return ctClazz == clazzPool.get("java.lang.Double");
			} else {
				throw new ItasException("type:" + clazz.getName() + "is not java basic type");
			}
		}
		
		public static boolean isExtends(CtField field, Class<?> parent) throws Exception {
			return isExtends(field.getType(), parent);
		}
		
		public static boolean isExtends(CtClass clazz, Class<?> parent) throws Exception {
			ClassPool clazzPool = ClassPool.getDefault();
			
			if (parent == String.class) {
				return clazz == clazzPool.get("java.lang.String");
			} else if (parent == Simple.class) {
				return clazz == clazzPool.get("net.itas.core.Simple");
			} else if (parent == Pair.class) {
				return clazz == clazzPool.get("net.itas.util.Pair");
			} else if (parent == Timestamp.class) {
				return clazz == clazzPool.get("java.sql.Timestamp");
			} else if (parent == EByte.class) {
				return clazz.subtypeOf(clazzPool.get("net.itas.core.EnumByte"));
			} else if (parent == EString.class) {
				return clazz.subtypeOf(clazzPool.get("net.itas.core.EnumString"));
			} else if (parent == Resource.class) {
				return clazz.subtypeOf(clazzPool.get("net.itas.core.resource.Resource"));
			} else if (parent == Collection.class) {
				return clazz.subtypeOf(clazzPool.get("java.util.Collection"));
			} else if (parent == List.class) {
				return clazz.subtypeOf(clazzPool.get("java.util.List"));
			} else if (parent == Set.class) {
				return clazz.subtypeOf(clazzPool.get("java.util.Set"));
			} else if (parent == Map.class) {
				return clazz.subtypeOf(clazzPool.get("java.util.Map"));
			} else if (parent == GameObject.class) {
				return clazz.subtypeOf(clazzPool.get("net.itas.core.GameBase"));
			} else if (parent == GameBaseAotuID.class) {
				return clazz.subtypeOf(clazzPool.get("net.itas.core.GameBaseString"));
			} else {
				throw new ItasException("unsupported type:" + clazz);
			}
		}
		
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
		public static <T extends Enum<T>> T parse(Class<? extends EString> clazz, String key) {
			Map<String, Enum<?>> enumMap = ENUM_MAP_STRING.get(clazz);
			if (Objects.nonNull(enumMap)) {
				return (T) enumMap.get(key);
			}
			
			Map<String, Enum<?>> enumMap1 = new HashMap<>();
			EnumSet<? extends Enum<?>> enums = EnumSet.allOf((Class<T>)clazz);
			for (Enum<?> e : enums) {
				if (e instanceof EString) {
					enumMap1.put(((EString)e).key(), e);
				} else {
					throw new ItasException("enum auto complate must be implements net.itas.core.EnumString");
				}
			}
			
			ENUM_MAP_STRING.putIfAbsent(clazz, enumMap1);
			return (T) enumMap1.get(key);
		}
		
		@SuppressWarnings("unchecked")
		public static <T extends Enum<T>> T parse(Class<? extends EByte> clazz, byte key) {
			Map<Byte, Enum<?>> enumMap1 = ENUM_MAP_INTEGER.get(clazz);
			if (Objects.nonNull(enumMap1)) {
				return (T) enumMap1.get(key);
			}
			
			Map<Byte, Enum<?>> enumMap = new HashMap<>();
			
			EnumSet<? extends Enum<?>> enums = EnumSet.allOf((Class<T>)clazz);
			for (Enum<?> e : enums) {
				if (e instanceof EByte) {
					enumMap.put(((EByte)e).key(), e);
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