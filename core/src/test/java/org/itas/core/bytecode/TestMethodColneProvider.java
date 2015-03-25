package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestMethodColneProvider {

	
	private CtClass ctClass;
	
	@Before
	public void setUP() throws NotFoundException, Exception {
		ctClass = ClassPool.getDefault().get(ModelTest.class.getName());
	}
	
	@Test
	public void testClone() throws Exception {
		CloneProvider clone = new CloneProvider();
		
		clone.begin(ctClass);
		clone.end();
		
		String expected = 
				"protected org.itas.core.GameObject clone(java.lang.String oid) {"
				+ "return new org.itas.core.bytecode.TestModel(oid);"
				+ "}";
		Assert.assertEquals(expected, clone.toString());
		
		clone.toMethod();
//		ctClass.addMethod(method);
	}
}
