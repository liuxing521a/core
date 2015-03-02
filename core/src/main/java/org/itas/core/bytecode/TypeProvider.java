package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

import org.itas.core.CallBack;

/**
 * 可操作类型支持
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年3月2日上午11:42:12
 */
public interface TypeProvider extends Provider {

	/**
	 * 是否是java类型
	 * @param clazz
	 * @return
	 */
	abstract boolean isType(Class<?> clazz);
	
	/**
	 * 是否是ctClass指定类型
	 * @param clazz
	 * @return
	 */
	abstract boolean isCtType(CtClass clazz) throws Exception ;

	/**
	 * 数据库列字段类型
	 * @param field
	 * @return
	 */
	abstract String columnSQL(CtField field) throws Exception;
	
	/**
	 * field支持类型处理
	 * @return
	 */
	abstract void fieldProcessing(MethodProvider provider, CallBack<FieldProvider> call) throws Exception;
	
}
