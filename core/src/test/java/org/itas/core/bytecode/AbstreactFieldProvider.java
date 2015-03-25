package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import org.junit.Before;

public class AbstreactFieldProvider {

	protected CtClass clazz;

	protected FieldProvider provider;
	
	@Before
	public void setUP() throws NotFoundException {
		clazz = ClassPool.getDefault().get(ModelTest.class.getName());
	}
	
}
