package org.itas.core.bytecode;

import javassist.CtField;

/**
 * 属性[field]字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月28日上午10:13:48
 */
abstract class AbstractFieldProvider implements Provider {

	public AbstractFieldProvider(Modify modify) {
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
