package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeResourceProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeResourceProvider();

	private TypeResourceProvider() {
		super(new FieldResourcesProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.resource_.isAssignableFrom(clazz);
	}
	
	@Override
	public boolean isCtType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.resource_);
	}

	@Override
	public String columnSQL(CtField field) throws Exception {
		return String.format("`%s` VARCHAR(24) NOT NULL DEFAULT ''", field.getName());
	}

}
