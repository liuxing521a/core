package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeByteProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeByteProvider();

	private TypeByteProvider() {
		super(new FieldByteProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.byte_ == clazz || javaType.byteWrap == clazz;
	}

	@Override
	public boolean isCtType(CtClass clazz) {
		return javassistType.byte_ == clazz || javassistType.byteWrap == clazz;
	}

	@Override
	public String columnSQL(CtField field) {
		return String.format("`%s` TINYINT(4) NOT NULL DEFAULT '0'", field.getName());
	}

	

}
