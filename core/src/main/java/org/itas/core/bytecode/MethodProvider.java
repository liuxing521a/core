package org.itas.core.bytecode;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

public interface MethodProvider extends Provider {

	/**
	 * 序号增长, 1为底数
	 * @return
	 */
	abstract int getAndIncIndex();
	
	/**
	 * 方法增加field之前预处理
	 */
	abstract void begin(CtClass clazz) throws Exception;
	
	/**
	 * 增加方法相关field信息
	 * @param field
	 */
	abstract void append(CtField field) throws Exception;

	/**
	 * 结束添加field后续处理
	 * @throws Exception
	 */
	abstract void end() throws Exception;
	
	/**
	 * 转成ctMethod
	 * @return
	 * @throws CannotCompileException
	 */
	abstract CtMethod toMethod() throws Exception;
	
}
