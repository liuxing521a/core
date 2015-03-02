package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeCharProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeCharProvider();

	private TypeCharProvider() {
		super(new FieldCharProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.char_ == clazz || javaType.charWrap == clazz;
	}

	@Override
	public boolean isCtType(CtClass clazz) {
		return javassistType.char_ == clazz || javassistType.charWrap == clazz;
	}

	@Override
	public String columnSQL(CtField field) {
		return String.format("`%s` CHAR(1) NOT NULL DEFAULT ' '", field.getName());
	}

}
