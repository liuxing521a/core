package org.itas.core.bytecode;

import java.util.List;
import java.util.Map;

import javassist.CtField;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestCodeType {

	protected Provider codeType;
	
	@Before
	public void setUP() {
		codeType = new Provider(null) {
			@Override
			protected String setStatement(CtField field) {
				return null;
			}
			
			@Override
			protected String getResultSet(CtField field) {
				return null;
			}
		};
	}
	
	@Test
	public void testFirstKeyUPCase() {
		String result = codeType.firstKeyUpCase("hello");
		Assert.assertEquals("Hello", result);
		
		result = codeType.firstKeyUpCase("Hello");
		Assert.assertEquals("Hello", result);
	}

	@Test
	public void testFirstKeyLowerCase() {
		String result = codeType.firstKeyLowerCase("hello");
		Assert.assertEquals("hello", result);
		
		result = codeType.firstKeyLowerCase("Hello");
		Assert.assertEquals("hello", result);
	}
	
	public static void main(String[] args) throws Exception {
//		ClassPool pool = ClassPool.getDefault();
//		CtClass clazz = pool.get("org.itas.core.bytecode.TestCodeType");
//		CtField field = clazz.getDeclaredField("test");
//		
//		System.out.println(field.getGenericSignature());
//		
//		ClassType f = (ClassType)SignatureAttribute.toFieldSignature(field.getGenericSignature());
//		
//		CtClass clss1 = pool.get(f.getName());
//		CtClass clss2 = pool.get("java.util.HashMap");
//		System.out.println(clss2.subtypeOf(clss1) + ", " + f.getName());
//		
//		ClassType chirldType;
//		for (TypeArgument argument : f.getTypeArguments()) {
//			chirldType = (ClassType)argument.getType();
//			System.out.println(chirldType.getName());
//		}
//		
//		System.out.println(pool.get("byte") == CtClass.byteType);
//		
//		field = clazz.getDeclaredField("test1");
//		System.out.println(field.getType().isArray());
//
//		System.out.println(field.getType().getComponentType() == pool.get("java.util.Collection"));
	
		System.out.println(Math.sin((Math.PI/180D)*60D));
		System.out.println(Math.sin((Math.PI/180)*45));
	}
	
	Map<Integer, List<Integer>> test;
	List<Integer>[] test1;
}
