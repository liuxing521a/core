//package org.itas.core.bytecode;
//
//import javassist.CannotCompileException;
//import javassist.CtClass;
//import javassist.CtField;
//import javassist.CtMethod;
//
//import org.itas.util.Logger;
//
//class MethodSQLInsertProvider extends AbstractMethodProvider {
//	
//	private int count;
//	
//	MethodSQLInsertProvider() {
//		super();
//	}
//	
//	@Override
//	public void begin(CtClass clazz) throws Exception {
//		super.begin(clazz);
//		
//		this.count = 0;
//		buffer.append("INSERT INTO `");
//		buffer.append(tableName(clazz));
//		buffer.append("` (");
//	}
//
//	@Override
//	public void append(CtField field) {
//		this.count ++;
//		
//		buffer.append("`");
//		buffer.append(field.getName());
//		buffer.append("`");
//		buffer.append(", ");
//	}
//	
//	@Override
//	public void end() throws Exception {
//		buffer.delete(buffer.length() - 2, buffer.length());
//		buffer.append(") VALUES (");
//		
//		for (int i = 0; i < count; i++) {
//			buffer.append("?, ");
//		}
//		buffer.delete(buffer.length() - 2, buffer.length());
//		buffer.append(");");
//	}
//
//	@Override
//	public CtMethod toMethod() throws CannotCompileException {
//		StringBuffer methodBuf = new StringBuffer();
//		
//		methodBuf.append("protected String insertSQL() {");
//		methodBuf.append("return \"").append(buffer.toString()).append("\";");
//		methodBuf.append("}");
//		
//		return CtMethod.make(methodBuf.toString(), ctClass);
//	}
//
//	@Override
//	public String toString() {
//		Logger.trace("\n{}", buffer.toString());
//		return buffer.toString();
//	}
//	
//}
