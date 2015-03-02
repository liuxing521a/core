package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeMapProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeMapProvider();

	private TypeMapProvider() {
		super(new FieldMapProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.map_.isAssignableFrom(clazz);
	}
	
	@Override
	public boolean isCtType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.map_);
	}

	@Override
	public String columnSQL(CtField field) throws Exception {
		return String.format("`%s` TEXT", field.getName());
	}

}
