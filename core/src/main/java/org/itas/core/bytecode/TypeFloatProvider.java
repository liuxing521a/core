package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeFloatProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeFloatProvider();

	private TypeFloatProvider() {
		super(new FieldFloatProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.float_ == clazz || javaType.floatWrap == clazz;
	}
	
	@Override
	public boolean isCtType(CtClass clazz) {
		return javassistType.float_ == clazz || javassistType.floatWrap == clazz;
	}

	@Override
	public String columnSQL(CtField field) {
		return String.format("`%s` FLOAT(8, 2) NOT NULL DEFAULT '0.0'", field.getName());
	}

}
