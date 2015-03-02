package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeSimpleProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeSimpleProvider();

	private TypeSimpleProvider() {
		super(new FieldSimpleProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.simple_ == clazz;
	}
	
	@Override
	public boolean isCtType(CtClass clazz) {
		return javassistType.simple_ == clazz;
	}

	@Override
	public String columnSQL(CtField field) throws Exception {
		return String.format("`%s` VARCHAR(36) NOT NULL DEFAULT ''", field.getName());
	}

}
