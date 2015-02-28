package org.itas.core.bytecode;

import javassist.CtClass;

import org.itas.core.util.Type;
import org.itas.util.ItasException;

/**
 * 容器类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月27日下午4:38:05
 */
abstract class FieldContainerProvider extends AbstractFieldProvider {

	public FieldContainerProvider(Modify modify) {
		super(modify);
	}
	
	/**
	 * 字符串转成泛型具体类型
	 * @param genericType
	 * @param value
	 * @return
	 * @throws Exception
	 */
	protected String toObjectCode(CtClass genericType, String value) throws Exception {
		if (Type.booleanType.is(genericType)) {
			return String.format("java.lang.Boolean.valueOf(%s)", value);
		} 

		if (Type.byteType.is(genericType)) {
			return String.format("java.lang.Byte.valueOf(%s)", value);
		} 

		if (Type.charType.is(genericType)) {
			return String.format("%s.charAt(0)", value);
		} 

		if (Type.shortType.is(genericType)) {
			return String.format("java.lang.Short.valueOf(%s)", value);
		} 

		if (Type.intType.is(genericType)) {
			return String.format("java.lang.Integer.valueOf(%s)", value);
		} 

		if (Type.longType.is(genericType)) {
			return String.format("java.lang.Long.valueOf(%s)", value);
		} 

		if (Type.floatType.is(genericType)) {
			return String.format("java.lang.Float.valueOf(%s)", value);
		} 

		if (Type.doubleType.is(genericType)) {
			return String.format("java.lang.Double.valueOf(%s)", value);
		} 

		if (Type.stringType.is(genericType)) {
			return value;
		} 

		if (Type.simpleType.is(genericType)) {
			return String.format("new org.itas.core.Simple(%s)", value);
		} 

		if (Type.resourceType.is(genericType)) {
			return String.format("org.itas.core.Pool.getResource(%s)", value);
		} 

		if (Type.enumByteType.is(genericType)) {
			return String.format("org.itas.core.util.Utils.EnumUtils.parse(%s.class, java.lang.Byte.valueOf(%s))", 
					genericType.getName(), value);
		} 

		if (Type.enumIntType.is(genericType)) {
			return String.format("org.itas.core.util.Utils.EnumUtils.parse(%s.class, java.lang.Integer.valueOf(%s))", 
					genericType.getName(), value);
		} 

		if (Type.enumStringType.is(genericType)) {
			return String.format("org.itas.core.util.Utils.EnumUtils.parse(%s.class, %s)", 
					genericType.getName(), value);
		} 

		throw new ItasException("[" + FieldContainerProvider.class.getName() + "] unsuppoted type: " + genericType.getName());
	}
}
