package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeListProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeListProvider();

	private TypeListProvider() {
		super(new FieldListProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.list_.isAssignableFrom(clazz);
	}
	
	@Override
	public boolean isCtType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.list_);
	}

	@Override
	public String columnSQL(CtField field) throws Exception {
		return String.format("`%s` TEXT", field.getName());
	}

}
