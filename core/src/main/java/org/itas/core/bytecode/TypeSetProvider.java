package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeSetProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeSetProvider();

	private TypeSetProvider() {
		super(new FieldSetProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.set_.isAssignableFrom(clazz);
	}
	
	@Override
	public boolean isCtType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.set_);
	}

	@Override
	public String columnSQL(CtField field) throws Exception {
		return String.format("`%s` TEXT", field.getName());
	}

}
