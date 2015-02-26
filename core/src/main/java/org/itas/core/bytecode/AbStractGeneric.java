package org.itas.core.code.type;

import static org.itas.core.util.Utils.CtClassUtils.isExtends;
import javassist.CtClass;

import org.itas.core.code.CodeType;
import org.itas.core.code.Modify;
import org.itas.core.enums.EString;
import org.itas.util.ItasException;

public abstract class AbStractGeneric extends CodeType {

	public AbStractGeneric(Modify modify) {
		super(modify);
	}
	
	/**
	 * 字符串转成泛型具体类型
	 * @param genericType
	 * @param value
	 * @return
	 * @throws Exception
	 */
	protected String string2Generic(CtClass genericType, String value) throws Exception {
		StringBuffer buffer = new StringBuffer();
		
		if (genericType == booleanWrapType) {
			buffer.append("java.lang.Boolean.valueOf(");
			buffer.append(value);
			buffer.append(")");
			
			return buffer.toString();
		} else if (genericType == byteWrapType) {
			buffer.append("java.lang.Byte.valueOf(");
			buffer.append(value);
			buffer.append(")");
			
			return buffer.toString();
		} else if (genericType == charWrapType) {
			buffer.append(value);
			buffer.append(".charAt(0)");
			
			return buffer.toString();
		} else if (genericType == shortWrapType) {
			buffer.append("java.lang.Short.valueOf(");
			buffer.append(value);
			buffer.append(")");
			
			return buffer.toString();
		} else if (genericType == intWrapType) {
			buffer.append("java.lang.Integer.valueOf(");
			buffer.append(value);
			buffer.append(")");
			
			return buffer.toString();
		} else if (genericType == longWrapType) {
			buffer.append("java.lang.Long.valueOf(");
			buffer.append(value);
			buffer.append(")");
			
			return buffer.toString();
		} else if (genericType == floatWrapType) {
			buffer.append("java.lang.Float.valueOf(");
			buffer.append(value);
			buffer.append(")");
			
			return buffer.toString();
		} else if (genericType == doubleWrapType) {
			buffer.append("java.lang.Double.valueOf(");
			buffer.append(value);
			buffer.append(")");
			
			return buffer.toString();
		} else if (genericType == stringType) {
			buffer.append(value);
			
			return buffer.toString();
		} else if (genericType == simpleType) {
			buffer.append("new org.itas.core.Simple(");
			buffer.append(value);
			buffer.append(")");
			
			return buffer.toString();
		} else if (genericType.subtypeOf(resourceChirldType)) {
			buffer.append("(");
			buffer.append(genericType.getName());
			buffer.append(")org.itas.core.Pool.getResource(");
			buffer.append(value);
			buffer.append(")");
			
			return buffer.toString();
		} else if (genericType.subtypeOf(enumByteChirldType)) {
			buffer.append("org.itas.core.util.Utils.EnumUtils.parse(");
			buffer.append(genericType.getName());
			buffer.append(", java.lang.Byte.valueOf(");
			buffer.append(value);
			buffer.append("))");
			
			return buffer.toString();
		} else if (genericType.subtypeOf(enumIntChirldType)) {
			buffer.append("org.itas.core.util.Utils.EnumUtils.parse(");
			buffer.append(genericType.getName());
			buffer.append(", java.lang.Integer.valueOf(");
			buffer.append(value);
			buffer.append("))");
			
			return buffer.toString();
		} else if (isExtends(genericType, EString.class)) {
			buffer.append("org.itas.core.util.Utils.EnumUtils.parse(");
			buffer.append(genericType.getName());
			buffer.append(", ");
			buffer.append(value);
			buffer.append("))");
			
			return buffer.toString();
		} else {
			throw new ItasException("unsuppoted type:" + genericType.getName());
		}
	}

//	System.out.println(field.getGenericSignature());
//	
//	ClassType f = (ClassType)SignatureAttribute.toFieldSignature(field.getGenericSignature());
//	
//	CtClass clss1 = pool.get(f.getName());
//	CtClass clss2 = pool.get("java.util.HashMap");
//	System.out.println(clss2.subtypeOf(clss1));
//	
//	ClassType chirldType;
//	for (TypeArgument argument : f.getTypeArguments()) {
//		chirldType = (ClassType)argument.getType();
//		System.out.println(chirldType.getName());
//	}
}
