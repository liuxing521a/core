package org.itas.core.bytecode;

import javassist.CtField;


public abstract class Provider {

	public Provider(Modify modify) {
		this.modify = modify;
	}
	
	protected final Modify modify;
	
	protected abstract String setStatement(CtField field) throws Exception;
	
	protected abstract String getResultSet(CtField field) throws Exception;
	
	protected String firstKeyUpCase(String str) {
		StringBuilder buffer = new StringBuilder(str.length());
		
		for (char ch : str.toCharArray()) {
			if (buffer.length() == 0 && (ch >= 'a' && ch <= 'z'))
				buffer.append((char)(ch - 32));
			else
				buffer.append(ch);
		}
		
		return buffer.toString();
	}

	protected String firstKeyLowerCase(String str) {
		StringBuilder buffer = new StringBuilder(str.length());
		
		for (char ch : str.toCharArray()) {
			if (buffer.length() == 0 && (ch >= 'A' && ch <= 'Z'))
				buffer.append((char)(ch + 32));
			else
				buffer.append(ch);
		}
		
		return buffer.toString();
	}
	
	protected String nextLine(int line) {
		StringBuffer buffer = new StringBuffer();
		
		for (int i = 0; i < line; i++) {
			buffer.append('\n');
		}
		
		return buffer.toString();
	}
	
	protected String nextTable(int table) {
		StringBuffer buffer = new StringBuffer();
		
		for (int i = 0; i < table; i++) {
			buffer.append('\t');
		}
		
		return buffer.toString();
	}
	
	protected String next(int line, int table) {
		StringBuffer buffer = new StringBuffer();
		
		for (int i = 0; i < line; i++) {
			buffer.append('\n');
		}
		
		for (int i = 0; i < table; i++) {
			buffer.append('\t');
		}
		
		return buffer.toString();
	}
	
//	addFieldSelectSQL(columns, selectSQLBuffer, clazz);
//
//	addFieldInsertSQL(columns, insertSQLBuffer, clazz);
//	
//	addFieldUpdateSQL(columns, updateSQLBuffer, clazz);
//
//	addFieldDeleteSQL(deleteSQLBuffer, clazz);
//	
//	CtMethod method = CtMethod.make(insertBuffer.toString(), clazz);
//	clazz.addMethod(method);
//	
//	method = CtMethod.make(updateBuffer.toString(), clazz);
//	clazz.addMethod(method);
//	
//	method = CtMethod.make(deleteBuffer.toString(), clazz);
//	clazz.addMethod(method);
//	
//	method = CtMethod.make(fillBuffer.toString(), clazz);
//	clazz.addMethod(method);
//	
//	addMethodGetSelectSQL(clazz);
//
//	addMethodGetInsertSQL(clazz);
//
//	addMethodGetUpdateSQL(clazz);
//
//	addMethodGetDeleteSQL(clazz);
//
//	addMethodClone(clazz);
//	
//	addMethodGetTableName(clazz);
}
