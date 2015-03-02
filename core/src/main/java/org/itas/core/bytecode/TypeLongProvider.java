package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeLongProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeLongProvider();

	private TypeLongProvider() {
		super(new FieldLongProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.long_ == clazz || javaType.longWrap == clazz;
	}

	@Override
	public boolean isCtType(CtClass clazz) {
		return javassistType.long_ == clazz || javassistType.longWrap == clazz;
	}

	@Override
	public String columnSQL(CtField field) {
		return String.format("`%s` BIGINT(20) NOT NULL DEFAULT '0'", field.getName());
	}

}
