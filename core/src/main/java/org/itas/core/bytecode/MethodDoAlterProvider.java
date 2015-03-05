package org.itas.core.bytecode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.itas.util.Logger;

class MethodDoAlterProvider extends AbstractMethodProvider {
	
	private Set<String> alters;
	
	private Set<String> exitsColumns;
	
	MethodDoAlterProvider() {
		super();
		exitsColumns = Collections.emptySet();
	}
	
	public Set<String> getExitsColumns() {
		return exitsColumns;
	}

	public void setExitsColumns(Set<String> exitsColumns) {
		this.exitsColumns = exitsColumns;
	}

	@Override
	public void begin(CtClass clazz) throws Exception {
		super.begin(clazz);
		this.alters = new HashSet<>();
	}

	@Override
	public void append(CtField field) throws NotFoundException, Exception {
		if (!exitsColumns.contains(field.getName())) {
			alters.add(String.format("ALTER TABLE `%s` ADD %s;", tableName(ctClass), getType(field).columnSQL(field)));
		}
	}

	@Override
	public void end() {

	}
	
	@Override
	public CtMethod toMethod() throws CannotCompileException {
		StringBuffer methodBuf = new StringBuffer();
		
		methodBuf.append("protected void doAlter(java.sql.Statement stmt) {");
		for (String alter : alters) {
			methodBuf.append("stmt.addBatch(\"").append(alter).append("\");");
		}
		methodBuf.append("}");
		
		return CtMethod.make(methodBuf.toString(), ctClass);
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		alters.forEach(sql->buffer.append(sql).append("\n"));
		
		Logger.trace("\n{}", buffer.toString());
		return buffer.toString();
	}

}
