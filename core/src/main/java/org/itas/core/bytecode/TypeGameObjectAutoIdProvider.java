package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

import org.itas.core.CallBack;
import org.itas.util.ItasException;

class TypeGameObjectAutoIdProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeGameObjectAutoIdProvider();

	private TypeGameObjectAutoIdProvider() {
		super(null);
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.gameBaseAotuID.isAssignableFrom(clazz);
	}

	@Override
	public boolean isCtType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.gameBaseAotuID);
	}

	@Override
	public String columnSQL(CtField field) throws Exception {
		throw new ItasException("unsuppotred GameObject type Persistence");
	}
	
	@Override
	public synchronized void fieldProcessing(MethodProvider provider, CallBack<FieldProvider> call) {
		throw new ItasException("unsuppotred GameObject type Persistence");
	}

}
