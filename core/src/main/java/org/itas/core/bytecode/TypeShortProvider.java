package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeShortProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeShortProvider();

	private TypeShortProvider() {
		super(new FieldShortProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.short_ == clazz || javaType.shortWrap == clazz;
	}
	
	@Override
	public boolean isCtType(CtClass clazz) {
		return javassistType.short_ == clazz || javassistType.shortWrap == clazz;
	}

	@Override
	public String columnSQL(CtField field) {
		return String.format("`%s` SMALLINT(6) NOT NULL DEFAULT '0'", field.getName());
	}

}
