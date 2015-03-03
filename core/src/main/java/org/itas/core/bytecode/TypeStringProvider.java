package org.itas.core.bytecode;

import org.itas.core.annotation.Size;

import javassist.CtClass;
import javassist.CtField;

class TypeStringProvider extends AbstractTypeProvider {
	
	public static final TypeProvider PROVIDER = new TypeStringProvider();

	private TypeStringProvider() {
		super(new FieldStringProvider());
	}

	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.string_ == clazz;
	}
	
	@Override
	public boolean isCtType(CtClass clazz) {
		return javassistType.string_ == clazz;
	}

	@Override
	public String columnSQL(CtField field) throws Exception {
		Object aSize = field.getAnnotation(Size.class);
		int size = (aSize == null) ? 36 : ((Size)aSize).value();
		return String.format("`%s` VARCHAR(%s) NOT NULL DEFAULT ''", field.getName(), size);
	}

}
