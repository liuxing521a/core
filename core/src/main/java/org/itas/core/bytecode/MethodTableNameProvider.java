package org.itas.core.bytecode;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

class MethodTableNameProvider extends AbstractMethodProvider {

	@Override
	public void begin(CtClass clazz) throws Exception {
		super.begin(clazz);
	}
	
	@Override
	public void append(CtField field) throws Exception {
		
	}
	
	@Override
	public void end() throws Exception {
		buffer.append("protected String tableName() {");
		buffer.append("return \"").append(tableName(ctClass)).append("\";");
		buffer.append("}");
	}

	@Override
	public CtMethod toMethod() throws CannotCompileException, ClassNotFoundException {
		return CtMethod.make(buffer.toString(), ctClass);
	}
}
