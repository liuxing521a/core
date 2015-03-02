package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeEnumStringProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeEnumStringProvider();

	private TypeEnumStringProvider() {
		super(new FieldEnumStringProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.enumString_.isAssignableFrom(clazz);
	}
	
	@Override
	public boolean isCtType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.enumString_);
	}

	@Override
	public String columnSQL(CtField field) {
		return String.format("`%s` VARCHAR(24) NOT NULL DEFAULT ''", field.getName());
	}

}
