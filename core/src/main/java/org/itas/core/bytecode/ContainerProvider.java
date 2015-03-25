package org.itas.core.bytecode;

import javassist.CtClass;

import org.itas.util.ItasException;

/**
 * 容器数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月27日下午4:38:05
 */
abstract class ContainerProvider extends AbstractFieldProvider {

	/**
	 * 字符串转成泛型具体类型
	 * @param genericType
	 * @param value
	 * @return
	 * @throws Exception
	 */
	protected String toObjectCode(CtClass genericType, String value) throws Exception {
		if (Type.booleanType.isType(genericType)) {
			return String.format("java.lang.Boolean.valueOf(%s)", value);
		} 

		if (Type.byteType.isType(genericType)) {
			return String.format("java.lang.Byte.valueOf(%s)", value);
		} 

		if (Type.charType.isType(genericType)) {
			return String.format("%s.charAt(0)", value);
		} 

		if (Type.shortType.isType(genericType)) {
			return String.format("java.lang.Short.valueOf(%s)", value);
		} 

		if (Type.intType.isType(genericType)) {
			return String.format("java.lang.Integer.valueOf(%s)", value);
		} 

		if (Type.longType.isType(genericType)) {
			return String.format("java.lang.Long.valueOf(%s)", value);
		} 

		if (Type.floatType.isType(genericType)) {
			return String.format("java.lang.Float.valueOf(%s)", value);
		} 

		if (Type.doubleType.isType(genericType)) {
			return String.format("java.lang.Double.valueOf(%s)", value);
		} 

		if (Type.stringType.isType(genericType)) {
			return value;
		} 

		if (Type.simpleType.isType(genericType)) {
			return String.format("new org.itas.core.Simple(%s)", value);
		} 

		if (Type.resourceType.isType(genericType)) {
			return String.format("org.itas.core.Pool.getResource(%s)", value);
		} 

		if (Type.enumByteType.isType(genericType)) {
			return String.format("org.itas.core.util.Utils.EnumUtils.parse(%s.class, java.lang.Byte.valueOf(%s))", 
					genericType.getName(), value);
		} 

		if (Type.enumIntType.isType(genericType)) {
			return String.format("org.itas.core.util.Utils.EnumUtils.parse(%s.class, java.lang.Integer.valueOf(%s))", 
					genericType.getName(), value);
		} 

		if (Type.enumStringType.isType(genericType)) {
			return String.format("org.itas.core.util.Utils.EnumUtils.parse(%s.class, %s)", 
					genericType.getName(), value);
		} 

		throw new ItasException("[" + ContainerProvider.class.getName() + "] unsuppoted type: " + genericType.getName());
	}
}
