package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeBooleanProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeBooleanProvider();
	
	private TypeBooleanProvider() {
		super(new FieldBooleanProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.boolean_ == clazz || javaType.booleanWrap == clazz;
	}

	@Override
	public boolean isCtType(CtClass clazz) {
		return javassistType.boolean_ == clazz || javassistType.booleanWrap == clazz;
	}

	@Override
	public String columnSQL(CtField field) {
		return String.format("`%s` TINYINT(1) ZEROFILL NOT NULL DEFAULT '0'", field.getName());
	}

}
