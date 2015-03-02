package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

class TypeTimestampProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeTimestampProvider();

	private TypeTimestampProvider() {
		super(new FieldTimestampProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.timeStamp == clazz;
	}
	
	@Override
	public boolean isCtType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.timeStamp);
	}

	@Override
	public String columnSQL(CtField field) throws Exception {
		return String.format("`%s` TIMESTAMP NOT NULL", field.getName());
	}

}
