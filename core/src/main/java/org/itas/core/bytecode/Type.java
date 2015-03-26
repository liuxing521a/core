package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;



/**
 * 支持数据类型
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月27日上午10:02:48
 */
public enum Type implements TypeProvider {
	
	booleanType {

		@Override
		protected TypeProvider provider() {
			return BooleanProvider.PROVIDER;
		}
		
	},
	byteType {

		@Override
		protected TypeProvider provider() {
			return ByteProvider.PROVIDER;
		}
		
	},
	charType {

		@Override
		protected TypeProvider provider() {
			return CharProvider.PROVIDER;
		}
		
	},
	shortType {

		@Override
		protected TypeProvider provider() {
			return ShortProvider.PROVIDER;
		}
		
	},
	intType {

		@Override
		protected TypeProvider provider() {
			return IntProvider.PROVIDER;
		}
		
	},
	longType {

		@Override
		protected TypeProvider provider() {
			return LongProvider.PROVIDER;
		}
		
	},
	floatType {

		@Override
		protected TypeProvider provider() {
			return FloatProvider.PROVIDER;
		}
		
	},
	doubleType {

		@Override
		protected TypeProvider provider() {
			return DoubleProvider.PROVIDER;
		}
		
	},
	stringType {

		@Override
		protected TypeProvider provider() {
			return StringProvider.PROVIDER;
		}
		
	},
	simpleType {

		@Override
		protected TypeProvider provider() {
			return SimpleProvider.PROVIDER;
		}
		
	},
	resourceType {

		@Override
		protected TypeProvider provider() {
			return ResourceProvider.PROVIDER;
		}
		
	},
	enumType {

		@Override
		TypeProvider provider() {
			return EnumProvider.PROVIDER;
		}
		
	},
	enumByteType {

		@Override
		protected TypeProvider provider() {
			return EnumByteProvider.PROVIDER;
		}
		
	},
	enumIntType {

		@Override
		protected TypeProvider provider() {
			return EnumIntProvider.PROVIDER;
		}
	},
	enumStringType {

		@Override
		protected TypeProvider provider() {
			return EnumStringProvider.PROVIDER;
		}
		
	},
	listType {

		@Override
		protected TypeProvider provider() {
			return ListProvider.PROVIDER;
		}
		
	},
	setType {

		@Override
		protected TypeProvider provider() {
			return SetProvider.PROVIDER;
		}
	
	},
	mapType {

		@Override
		protected TypeProvider provider() {
			return MapProvider.PROVIDER;
		}
		
	},
	timeStampType {

		@Override
		protected TypeProvider provider() {
			return TimestampProvider.PROVIDER;
		}
		
	},
	gameObjectType {

		@Override
		protected TypeProvider provider() {
			return GameObjectProvider.PROVIDER;
		}
	
	},
	gameObjectAutoIdType {

		@Override
		protected TypeProvider provider() {
			return GameObjectAutoProvider.PROVIDER;
		}
		
	},
	;
	
	private Type() {
	}
	
	abstract TypeProvider provider();
	
	@Override
	public boolean isType(Class<?> clazz) {
		return provider().isType(clazz);
	}

	@Override
	public boolean isType(CtClass clazz) throws Exception {
		return provider().isType(clazz);
	}
	
	@Override
	public String sqlType(CtField field) throws Exception {
		return provider().sqlType(field);
	}
	
//	@Override
//	public void process(MethodProvider provider, 
//			CallBack<FieldProvider> call) throws Exception {
////		provider().process(provider, call);
//	}
	
	
}
