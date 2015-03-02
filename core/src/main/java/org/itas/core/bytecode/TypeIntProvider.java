package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeIntProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeIntProvider();

	private TypeIntProvider() {
		super(new FieldIntProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.int_ == clazz || javaType.intWrap == clazz;
	}
	
	@Override
	public boolean isCtType(CtClass clazz) {
		return javassistType.int_ == clazz || javassistType.intWrap == clazz;
	}

	@Override
	public String columnSQL(CtField field) {
		return String.format("`%s` INT(11) NOT NULL DEFAULT '0'", field.getName());
	}

}
