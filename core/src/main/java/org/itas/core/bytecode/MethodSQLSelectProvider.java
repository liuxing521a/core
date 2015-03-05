package org.itas.core.bytecode;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import org.itas.util.Logger;

class MethodSQLSelectProvider extends AbstractMethodProvider {
	
	MethodSQLSelectProvider() {
		super();
	}
	
	@Override
	public void begin(CtClass clazz) throws Exception {
		super.begin(clazz);
		
		buffer.append("SELECT ");
	}

	@Override
	public void append(CtField field) {
		buffer.append("`");
		buffer.append(field.getName());
		buffer.append("`");
		buffer.append(", ");
	}
	
	@Override
	public void end() throws ClassNotFoundException {
		buffer.delete(buffer.length() - 2, buffer.length());
		buffer.append(" FROM `");
		buffer.append(tableName(ctClass));
		buffer.append("` WHERE Id = ?;");
	}

	@Override
	public CtMethod toMethod() throws CannotCompileException {
		StringBuffer methodBuf = new StringBuffer();
		
		methodBuf.append("protected String selectSQL() {");
		methodBuf.append("return \"").append(buffer.toString()).append("\";");
		methodBuf.append("}");
		
		return CtMethod.make(methodBuf.toString(), ctClass);
	}
	
	@Override
	public String toString() {
		Logger.trace("\n{}", buffer.toString());
		return buffer.toString();
	}
	
}
