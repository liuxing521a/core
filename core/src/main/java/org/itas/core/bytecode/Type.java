package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

import org.itas.core.CallBack;



/**
 * 支持数据类型
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月27日上午10:02:48
 */
public enum Type {
	
	booleanType {

		@Override
		protected TypeProvider provider() {
			return TypeBooleanProvider.PROVIDER;
		}
		
	},
	byteType {

		@Override
		protected TypeProvider provider() {
			return TypeByteProvider.PROVIDER;
		}
		
	},
	charType {

		@Override
		protected TypeProvider provider() {
			return TypeCharProvider.PROVIDER;
		}
		
	},
	shortType {

		@Override
		protected TypeProvider provider() {
			return TypeShortProvider.PROVIDER;
		}
		
	},
	intType {

		@Override
		protected TypeProvider provider() {
			return TypeIntProvider.PROVIDER;
		}
		
	},
	longType {

		@Override
		protected TypeProvider provider() {
			return TypeLongProvider.PROVIDER;
		}
		
	},
	floatType {

		@Override
		protected TypeProvider provider() {
			return TypeFloatProvider.PROVIDER;
		}
		
	},
	doubleType {

		@Override
		protected TypeProvider provider() {
			return TypeDoubleProvider.PROVIDER;
		}
		
	},
	stringType {

		@Override
		protected TypeProvider provider() {
			return TypeStringProvider.PROVIDER;
		}
		
	},
	simpleType {

		@Override
		protected TypeProvider provider() {
			return TypeSimpleProvider.PROVIDER;
		}
		
	},
	resourceType {

		@Override
		protected TypeProvider provider() {
			return TypeResourceProvider.PROVIDER;
		}
		
	},
	enumByteType {

		@Override
		protected TypeProvider provider() {
			return TypeEnumByteProvider.PROVIDER;
		}
		
	},
	enumIntType {

		@Override
		protected TypeProvider provider() {
			return TypeEnumIntProvider.PROVIDER;
		}
	},
	enumStringType {

		@Override
		protected TypeProvider provider() {
			return TypeEnumStringProvider.PROVIDER;
		}
		
	},
	listType {

		@Override
		protected TypeProvider provider() {
			return TypeListProvider.PROVIDER;
		}
		
	},
	setType {

		@Override
		protected TypeProvider provider() {
			return TypeSetProvider.PROVIDER;
		}
	
	},
	mapType {

		@Override
		protected TypeProvider provider() {
			return TypeMapProvider.PROVIDER;
		}
		
	},
	timeStampType {

		@Override
		protected TypeProvider provider() {
			return TypeTimestampProvider.PROVIDER;
		}
		
	},
	gameObjectType {

		@Override
		protected TypeProvider provider() {
			return TypeGameObjectProvider.PROVIDER;
		}
	
	},
	gameObjectAutoIdType {

		@Override
		protected TypeProvider provider() {
			return TypeGameObjectAutoIdProvider.PROVIDER;
		}
		
	},
	
	;
	
	private Type() {
		
	}
	
	protected abstract TypeProvider provider();
	
	boolean is(Class<?> clazz) {
		return provider().isType(clazz);
	}

	boolean is(CtClass clazz) throws Exception {
		return provider().isCtType(clazz);
	}
	
	String columnSQL(CtField field) throws Exception {
		return provider().columnSQL(field);
	}
	
	void fieldProcessing(MethodProvider provider, CallBack<FieldProvider> call) throws Exception {
		provider().fieldProcessing(provider, call);
	}
}
