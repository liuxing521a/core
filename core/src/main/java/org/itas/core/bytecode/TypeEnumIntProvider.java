package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeEnumIntProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeEnumIntProvider();

	private TypeEnumIntProvider() {
		super(new FieldEnumIntProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.enumInt_.isAssignableFrom(clazz);
	}
	
	@Override
	public boolean isCtType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.enumInt_);
	}

	@Override
	public String columnSQL(CtField field) {
		return String.format("`%s` INT(11) NOT NULL DEFAULT '0'", field.getName());
	}

}
