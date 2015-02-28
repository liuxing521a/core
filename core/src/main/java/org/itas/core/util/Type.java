package org.itas.core.util;

import java.sql.Timestamp;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import net.itas.core.annotation.Size;

import org.itas.core.GameBaseAotuID;
import org.itas.core.GameObject;
import org.itas.util.ItasException;



/**
 * 支持数据类型
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月27日上午10:02:48
 */
public enum Type {
	
	booleanType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.boolean_ == clazz || javaType.booleanWrap == clazz;
		}

		@Override
		public boolean is(CtClass clazz) {
			return javassistType.boolean_ == clazz || javassistType.booleanWrap == clazz;
		}

		@Override
		public String columnSQL(CtField field) {
			return String.format("`%s` TINYINT(1) ZEROFILL NOT NULL DEFAULT '0'", field.getName());
		}
	},
	byteType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.byte_ == clazz || javaType.byteWrap == clazz;
		}

		@Override
		public boolean is(CtClass clazz) {
			return javassistType.byte_ == clazz || javassistType.byteWrap == clazz;
		}

		@Override
		public String columnSQL(CtField field) {
			return String.format("`%s` TINYINT(4) NOT NULL DEFAULT '0'", field.getName());
		}
	},
	charType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.char_ == clazz || javaType.charWrap == clazz;
		}

		@Override
		public boolean is(CtClass clazz) {
			return javassistType.char_ == clazz || javassistType.charWrap == clazz;
		}

		@Override
		public String columnSQL(CtField field) {
			return String.format("`%s` CHAR(1) NOT NULL DEFAULT ' '", field.getName());
		}
	},
	shortType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.short_ == clazz || javaType.shortWrap == clazz;
		}
		
		@Override
		public boolean is(CtClass clazz) {
			return javassistType.short_ == clazz || javassistType.shortWrap == clazz;
		}

		@Override
		public String columnSQL(CtField field) {
			return String.format("`%s` SMALLINT(6) NOT NULL DEFAULT '0'", field.getName());
		}
	},
	intType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.int_ == clazz || javaType.intWrap == clazz;
		}
		
		@Override
		public boolean is(CtClass clazz) {
			return javassistType.int_ == clazz || javassistType.intWrap == clazz;
		}

		@Override
		public String columnSQL(CtField field) {
			return String.format("`%s` INT(1) NOT NULL DEFAULT '0'", field.getName());
		}
	},
	longType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.long_ == clazz || javaType.longWrap == clazz;
		}

		@Override
		public boolean is(CtClass clazz) {
			return javassistType.long_ == clazz || javassistType.longWrap == clazz;
		}

		@Override
		public String columnSQL(CtField field) {
			return String.format("`%s` BIGINT(20) NOT NULL DEFAULT '0'", field.getName());
		}
	},
	floatType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.float_ == clazz || javaType.floatWrap == clazz;
		}
		
		@Override
		public boolean is(CtClass clazz) {
			return javassistType.float_ == clazz || javassistType.floatWrap == clazz;
		}

		@Override
		public String columnSQL(CtField field) {
			return String.format("`%s` FLOAT(8, 2) NOT NULL DEFAULT '0.0'", field.getName());
		}
	},
	doubleType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.double_ == clazz || javaType.doubleWrap == clazz;
		}
		
		@Override
		public boolean is(CtClass clazz) {
			return javassistType.double_ == clazz || javassistType.doubleWrap == clazz;
		}

		@Override
		public String columnSQL(CtField field) {
			return String.format("`%s` DOUBLE(14, 4) NOT NULL DEFAULT '0.0'", field.getName());
		}
	},
	stringType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.string_ == clazz;
		}
		
		@Override
		public boolean is(CtClass clazz) {
			return javassistType.string_ == clazz;
		}

		@Override
		public String columnSQL(CtField field) throws Exception {
			Object aSize = field.getAnnotation(Size.class);
			int size = (aSize == null) ? 36 : ((Size)aSize).value();
			return String.format("`%s` VARCHAR(%s) NOT NULL DEFAULT ''", field.getName(), size);
		}
	},
	simpleType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.simple_ == clazz;
		}
		
		@Override
		public boolean is(CtClass clazz) {
			return javassistType.simple_ == clazz;
		}

		@Override
		public String columnSQL(CtField field) throws Exception {
			return String.format("`%s` VARCHAR(36) NOT NULL DEFAULT ''", field.getName());
		}
	},
	resourceType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.resource_.isAssignableFrom(clazz);
		}
		
		@Override
		public boolean is(CtClass clazz)  throws Exception {
			return clazz.subtypeOf(javassistType.resource_);
		}

		@Override
		public String columnSQL(CtField field) throws Exception {
			return String.format("`%s` VARCHAR(24) NOT NULL DEFAULT ''", field.getName());
		}
	},
	enumByteType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.enumByte_.isAssignableFrom(clazz);
		}
		
		@Override
		public boolean is(CtClass clazz)  throws Exception {
			return clazz.subtypeOf(javassistType.enumByte_);
		}

		@Override
		public String columnSQL(CtField field) throws Exception {
			return String.format("`%s` TINYINT(4) NOT NULL DEFAULT '0'", field.getName());
		}
	},
	enumIntType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.enumInt_.isAssignableFrom(clazz);
		}
		
		@Override
		public boolean is(CtClass clazz)  throws Exception {
			return clazz.subtypeOf(javassistType.enumInt_);
		}

		@Override
		public String columnSQL(CtField field) throws Exception {
			return String.format("`%s` INT(11) NOT NULL DEFAULT '0'", field.getName());
		}
	},
	enumStringType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.enumString_.isAssignableFrom(clazz);
		}
		
		@Override
		public boolean is(CtClass clazz)  throws Exception {
			return clazz.subtypeOf(javassistType.enumString_);
		}

		@Override
		public String columnSQL(CtField field) throws Exception {
			return String.format("`%s` VARCHAR(24) NOT NULL DEFAULT ''", field.getName());
		}
	},
	listType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.list_.isAssignableFrom(clazz);
		}
		
		@Override
		public boolean is(CtClass clazz)  throws Exception {
			return clazz.subtypeOf(javassistType.list_);
		}

		@Override
		public String columnSQL(CtField field) throws Exception {
			return String.format("`%s` TEXT", field.getName());
		}
	},
	setType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.set_.isAssignableFrom(clazz);
		}
		
		@Override
		public boolean is(CtClass clazz)  throws Exception {
			return clazz.subtypeOf(javassistType.set_);
		}

		@Override
		public String columnSQL(CtField field) throws Exception {
			return String.format("`%s` TEXT", field.getName());
		}
	},
	mapType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.map_.isAssignableFrom(clazz);
		}
		
		@Override
		public boolean is(CtClass clazz)  throws Exception {
			return clazz.subtypeOf(javassistType.map_);
		}

		@Override
		public String columnSQL(CtField field) throws Exception {
			return String.format("`%s` TEXT", field.getName());
		}
	},
	timeStampType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.timeStamp == clazz;
		}
		
		@Override
		public boolean is(CtClass clazz)  throws Exception {
			return clazz.subtypeOf(javassistType.timeStamp);
		}

		@Override
		public String columnSQL(CtField field) throws Exception {
			return String.format("`%s` TIMESTAMP NOT NULL", field.getName());
		}
	},
	gameObjectType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.gameObject.isAssignableFrom(clazz);
		}
		
		@Override
		public boolean is(CtClass clazz)  throws Exception {
			return clazz.subtypeOf(javassistType.gameObject);
		}

		@Override
		public String columnSQL(CtField field) throws Exception {
			throw new ItasException("unsuppotred GameObject type Persistence");
		}
	},
	gameObjectAutoIdType {
		@Override
		public boolean is(Class<?> clazz) {
			return javaType.gameBaseAotuID.isAssignableFrom(clazz);
		}

		@Override
		public boolean is(CtClass clazz)  throws Exception {
			return clazz.subtypeOf(javassistType.gameBaseAotuID);
		}

		@Override
		public String columnSQL(CtField field) throws Exception {
			throw new ItasException("unsuppotred GameObject type Persistence");
		}
	},
	
	;
	
	public abstract boolean is(Class<?> clazz);

	public abstract boolean is(CtClass clazz) throws Exception;
	
	public abstract String columnSQL(CtField field) throws Exception;
	
	public static class javaType {
		
		public final static Class<?> boolean_ = boolean.class;
		public final static Class<?> booleanWrap = java.lang.Boolean.class;
		
		public final static Class<?> char_ = char.class;
		public final static Class<?> charWrap = java.lang.Character.class;
		
		public final static Class<?> byte_ = byte.class;
		public final static Class<?> byteWrap = java.lang.Byte.class;
		
		public final static Class<?> short_ = short.class;
		public final static Class<?> shortWrap = java.lang.Short.class;
		
		public final static Class<?> int_ = int.class;
		public final static Class<?> intWrap = java.lang.Integer.class;
		
		public final static Class<?> long_ = long.class;
		public final static Class<?> longWrap = java.lang.Long.class;
		
		public final static Class<?> float_ = float.class;
		public final static Class<?> floatWrap = java.lang.Float.class;
		
		public final static Class<?> double_ = double.class;
		public final static Class<?> doubleWrap = java.lang.Double.class;
		
		public final static Class<?> string_ = java.lang.String.class;
		
		public final static Class<?> simple_ = org.itas.core.Simple.class;
		
		public final static Class<?> resource_ = org.itas.core.resource.Resource.class;
		
		public final static Class<?> enumByte_ = org.itas.core.EnumByte.class;
		
		public final static Class<?> enumInt_ = org.itas.core.EnumInt.class;
		
		public final static Class<?> enumString_ = org.itas.core.EnumString.class;
		
		public final static Class<?> list_ = java.util.List.class;
		
		public final static Class<?> set_ = java.util.Set.class;;
		
		public final static Class<?> concurrentMap_ = java.util.concurrent.ConcurrentMap.class;
		
		public final static Class<?> sortedMap_ = java.util.SortedMap.class;
		
		public final static Class<?> map_ = java.util.Map.class;
		
		public final static Class<?> timeStamp = Timestamp.class;

		public final static Class<?> gameObject = GameObject.class;

		public final static Class<?> gameBaseAotuID = GameBaseAotuID.class;
		
	}
	
	public static class javassistType {
		
		public final static CtClass boolean_ = CtClass.booleanType;
		public final static CtClass booleanWrap;

		public final static CtClass char_ = CtClass.charType;
		public final static CtClass charWrap;

		public final static CtClass byte_ = CtClass.byteType;
		public final static CtClass byteWrap;

		public final static CtClass short_ = CtClass.shortType;
		public final static CtClass shortWrap;

		public final static CtClass int_ = CtClass.intType;
		public final static CtClass intWrap;

		public final static CtClass long_ = CtClass.longType;
		public final static CtClass longWrap;

		public final static CtClass float_ = CtClass.floatType;
		public final static CtClass floatWrap;

		public final static CtClass double_ = CtClass.doubleType;
		public final static CtClass doubleWrap;

		public final static CtClass string_;

		public final static CtClass simple_;

		public final static CtClass resource_;

		public final static CtClass enumByte_;

		public final static CtClass enumInt_;

		public final static CtClass enumString_;

		public final static CtClass list_;

		public final static CtClass set_;

		public final static CtClass map_;
		
		public final static CtClass timeStamp;
		
		public final static CtClass gameObject;

		public final static CtClass gameBaseAotuID;
		

	    static {
	    	ClassPool pool = ClassPool.getDefault();
	    	try {
	    		booleanWrap = pool.get(javaType.booleanWrap.getName());
	    		charWrap = pool.get(javaType.charWrap.getName());
	    		byteWrap = pool.get(javaType.byteWrap.getName());
	    		shortWrap = pool.get(javaType.shortWrap.getName());
	    		intWrap = pool.get(javaType.intWrap.getName());
	    		longWrap = pool.get(javaType.longWrap.getName());
	    		floatWrap = pool.get(javaType.floatWrap.getName());
				doubleWrap = pool.get(javaType.doubleWrap.getName());
				string_ = pool.get(javaType.string_.getName());
				simple_ = pool.get(javaType.simple_.getName());
				resource_ = pool.get(javaType.resource_.getName());
				enumByte_ = pool.get(javaType.enumByte_.getName());
				enumInt_ = pool.get(javaType.enumInt_.getName());
				enumString_ = pool.get(javaType.enumString_.getName());
				list_ = pool.get(javaType.list_.getName());
				set_ = pool.get(javaType.set_.getName());
				map_ = pool.get(javaType.map_.getName());
				timeStamp = pool.get(javaType.timeStamp.getName());
				gameObject = pool.get(javaType.gameObject.getName());
				gameBaseAotuID = pool.get(javaType.gameBaseAotuID.getName());
			} catch (Exception e) {
				throw new ItasException(e);
			}
	    }
	}
}
