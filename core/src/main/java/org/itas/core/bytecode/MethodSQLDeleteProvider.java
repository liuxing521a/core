package org.itas.core.bytecode;

import static org.itas.core.util.ByteCodeUtils.tableName;
import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import org.itas.util.Logger;

class MethodSQLDeleteProvider extends AbstractMethodProvider {
	
	MethodSQLDeleteProvider() {
		super();
	}
	
	@Override
	public void begin(CtClass clazz) throws Exception {
		super.begin(clazz);
	}

	@Override
	public void append(CtField field) {
		
	}

	@Override
	public void end() throws Exception {
		buffer.append("DELETE FROM `");
		buffer.append(tableName(ctClass));
		buffer.append("` WHERE Id = ?;");
	}
	
	@Override
	public CtMethod toMethod() throws CannotCompileException {
		StringBuffer methodBuf = new StringBuffer();
		
		methodBuf.append("protected String deleteSQL() {");
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
