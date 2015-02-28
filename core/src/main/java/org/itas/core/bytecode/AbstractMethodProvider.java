package org.itas.core.bytecode;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.itas.core.util.Type;
import org.itas.core.util.Type.javassistType;
import org.itas.util.ItasException;

/**
 * 方法[method]字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月28日上午10:14:10
 */
abstract class AbstractMethodProvider implements Provider {
	
	private static final Map<CtClass, Type> SUPPORT_TYPE;
	
	static {
		Map<CtClass, Type> tmpmap = new HashMap<>();
		tmpmap.put(javassistType.boolean_, Type.booleanType);
		tmpmap.put(javassistType.booleanWrap, Type.booleanType);
		tmpmap.put(javassistType.byte_, Type.byteType);
		tmpmap.put(javassistType.byteWrap, Type.byteType);
		tmpmap.put(javassistType.char_, Type.charType);
		tmpmap.put(javassistType.charWrap, Type.charType);
		tmpmap.put(javassistType.short_, Type.shortType);
		tmpmap.put(javassistType.shortWrap, Type.shortType);
		tmpmap.put(javassistType.int_, Type.intType);
		tmpmap.put(javassistType.intWrap, Type.intType);
		tmpmap.put(javassistType.long_, Type.longType);
		tmpmap.put(javassistType.longWrap, Type.longType);
		tmpmap.put(javassistType.float_, Type.floatType);
		tmpmap.put(javassistType.floatWrap, Type.floatType);
		tmpmap.put(javassistType.double_, Type.doubleType);
		tmpmap.put(javassistType.doubleWrap, Type.doubleType);
		tmpmap.put(javassistType.string_, Type.stringType);
		tmpmap.put(javassistType.simple_, Type.simpleType);
		tmpmap.put(javassistType.resource_, Type.resourceType);
		tmpmap.put(javassistType.enumByte_, Type.enumByteType);
		tmpmap.put(javassistType.enumInt_, Type.enumIntType);
		tmpmap.put(javassistType.enumString_, Type.enumStringType);
		tmpmap.put(javassistType.list_, Type.listType);
		tmpmap.put(javassistType.set_, Type.setType);
		tmpmap.put(javassistType.map_, Type.mapType);
		tmpmap.put(javassistType.timeStamp, Type.timeStampType);
		
		SUPPORT_TYPE = Collections.unmodifiableMap(tmpmap);
	}
	
	protected CtClass ctClass;
	
	protected StringBuffer buffer;

	/**
	 * 方法增加field之前预处理
	 */
	public abstract void begin(CtClass clazz) throws Exception;
	
	/**
	 * 增加方法相关field信息
	 * @param field
	 */
	public abstract void append(CtField field) throws Exception;

	/**
	 * 结束添加field后续处理
	 * @throws Exception
	 */
	public void end() throws Exception {
		
	}
	
	/**
	 * 转成ctMethod
	 * @return
	 * @throws CannotCompileException
	 */
	public abstract CtMethod toMethod() throws Exception;
	
	protected Type getType(CtField field) throws NotFoundException {
		Type type = SUPPORT_TYPE.get(field.getType());
		if (type == null) {
			throw new ItasException("field type unsupported:" + field.getType());
		}
		
		return type;
	}
	
	
}
