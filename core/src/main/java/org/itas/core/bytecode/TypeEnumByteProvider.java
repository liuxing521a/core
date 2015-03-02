package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeEnumByteProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeEnumByteProvider();

	private TypeEnumByteProvider() {
		super(new FieldEnumByteProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.enumByte_.isAssignableFrom(clazz);
	}
	
	@Override
	public boolean isCtType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.enumByte_);
	}

	@Override
	public String columnSQL(CtField field) {
		return String.format("`%s` TINYINT(4) NOT NULL DEFAULT '0'", field.getName());
	}

}
