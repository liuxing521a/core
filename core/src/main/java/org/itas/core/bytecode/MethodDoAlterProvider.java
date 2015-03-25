//package org.itas.core.bytecode;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Map.Entry;
//
//import javassist.CannotCompileException;
//import javassist.CtClass;
//import javassist.CtField;
//import javassist.CtMethod;
//import javassist.NotFoundException;
//
//import org.itas.util.Logger;
//
//class MethodDoAlterProvider extends AbstractMethodProvider {
//	
//  private Map<String, String> alters;
//	
//  MethodDoAlterProvider() {
//	super();
//  }
//
//  @Override
//  public void begin(CtClass clazz) throws Exception {
//	super.begin(clazz);
//	this.alters = new HashMap<>();
//  }
//
//  @Override
//  public void append(CtField field) throws NotFoundException, Exception {
//	alters.put(field.getName(), String.format("ALTER TABLE `%s` ADD %s;", 
//	    tableName(ctClass), getType(field).sqlType(field)));
//  }
//
//  @Override
//  public void end() {
//	  
//  }
//	
//  @Override
//  public CtMethod toMethod() throws CannotCompileException {
//	StringBuffer methodBuf = new StringBuffer();
//		
//	methodBuf.append("protected void doAlter(java.sql.Statement stmt, java.util.Set columns) throws java.sql.SQLException {");
//	methodBuf.append("\n\t\t");
//	methodBuf.append("java.util.Map alterMap = new java.util.HashMap();");
//	for (Entry<String, String> alter : alters.entrySet()) {
//	  methodBuf.append("\n\t\t");
//	  methodBuf.append("alterMap.put(");
//	  methodBuf.append("\"").append(alter.getKey()).append("\"");
//	  methodBuf.append(", \"").append(alter.getValue()).append("\");");
//	}
//	
//	methodBuf.append("\n\n\t\t");
//	methodBuf.append("java.util.Map.Entry entry;");
//	methodBuf.append("\n\t\t");
//	methodBuf.append("java.util.Iterator it = alterMap.entrySet().iterator();");
//	methodBuf.append("\n\t\t");
//	methodBuf.append("while (it.hasNext()) {");
//	methodBuf.append("\n\t\t\t");
//	methodBuf.append("entry = it.next();");
//	methodBuf.append("\n\t\t\t");
//	methodBuf.append("if (!columns.contains((String)entry.getKey()))");
//	methodBuf.append("\n\t\t\t");
//	methodBuf.append("stmt.addBatch((String)entry.getValue());");
//	methodBuf.append("\n\t\t");
//	methodBuf.append("}");
//	methodBuf.append("\n\t");
//	methodBuf.append("}");
//	Logger.trace("{}", methodBuf);
//		
//	return CtMethod.make(methodBuf.toString(), ctClass);
//  }
//	
//  @Override
//  public String toString() {
//	StringBuffer buffer = new StringBuffer();
//	alters.forEach((name, sql)->buffer.append(sql).append("\n"));
//		
//	Logger.trace("\n{}", buffer.toString());
//	return buffer.toString();
//  }
//
//}
