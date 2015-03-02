package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeDoubleProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeDoubleProvider();

	private TypeDoubleProvider() {
		super(new FieldDoubleProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.double_ == clazz || javaType.doubleWrap == clazz;
	}
	
	@Override
	public boolean isCtType(CtClass clazz) {
		return javassistType.double_ == clazz || javassistType.doubleWrap == clazz;
	}

	@Override
	public String columnSQL(CtField field) {
		return String.format("`%s` DOUBLE(14, 4) NOT NULL DEFAULT '0.0'", field.getName());
	}

}
