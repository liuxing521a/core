package org.itas.core.code;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;

import org.itas.util.ItasException;


public abstract class CodeType {

	/**
	 * boolean包装类型
	 */
	public final static CtClass booleanWrapType;

	/**
	 * char包装类型
	 */
    public final static CtClass charWrapType;

    /**
	 * byte包装类型
	 */
    public final static CtClass byteWrapType;

    /**
	 * short包装类型
	 */
    public final static CtClass shortWrapType;

    /**
   	 * int包装类型
   	 */
    public final static CtClass intWrapType;

    /**
   	 * long包装类型
   	 */
    public final static CtClass longWrapType;

    /**
   	 * float包装类型
   	 */
    public final static CtClass floatWrapType;

    /**
     * double包装类型
     */
    public final static CtClass doubleWrapType;
    
    /**
     * String类型
     */
    public final static CtClass stringType;

    /**
     * Simple类型
     */
    public final static CtClass simpleType;

    /**
     * resource子类类型
     */
    public final static CtClass resourceChirldType;

    /**
     * resource子类类型
     */
    public final static CtClass enumByteChirldType;
   
    /**
     * resource子类类型
     */
    public final static CtClass enumIntChirldType;
    
    /**
     * resource子类类型
     */
    public final static CtClass enumStringChirldType;
    
    /**
	 * java.util.List子类类型
	 */
	public final static CtClass listChirldType;
	 
	/**
	 * java.util.Set子类类型
	 */
	public final static CtClass setChirldType;
	
	/**
	 * java.util.Map子类类型
	 */
	public final static CtClass mapChirldType;

	/**
	 * java.util.Map子类类型
	 */
	public final static CtClass ccrtmapChirldType;
	
	/**
     * java.util.Map子类类型
     */
    public final static CtClass sortedMapChirldType;

    static {
    	ClassPool pool = ClassPool.getDefault();
    			
    	try {
			booleanWrapType = pool.get("java.lang.Boolean");

			charWrapType = pool.get("java.lang.Character");

			byteWrapType = pool.get("java.lang.Byte");

			shortWrapType = pool.get("java.lang.Short");

			intWrapType = pool.get("java.lang.Integer");

			longWrapType = pool.get("java.lang.Long");

			floatWrapType = pool.get("java.lang.Float");

			doubleWrapType = pool.get("java.lang.Double");

			stringType = pool.get("java.lang.String");

			simpleType = pool.get("org.itas.core.Simple");

			resourceChirldType = pool.get("net.itas.core.resource");

			enumByteChirldType = pool.get("org.itas.core.enums.EByte");

			enumIntChirldType = pool.get("org.itas.core.enums.EShort");
			
			enumStringChirldType = pool.get("org.itas.core.enums.EString");
			
			listChirldType = pool.get("java.util.List");

    		setChirldType = pool.get("java.util.Set");

    		mapChirldType = pool.get("java.util.Map.");

    		ccrtmapChirldType = pool.get("java.util.concurrent.ConcurrentMap");

    		sortedMapChirldType = pool.get("java.util.SortedMap");
		} catch (NotFoundException e) {
			throw new ItasException(e);
		}
    }
	
	public CodeType(Modify modify) {
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
