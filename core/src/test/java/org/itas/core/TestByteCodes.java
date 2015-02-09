package org.itas.core;

import javassist.ClassPool;
import javassist.CtClass;

import org.itas.core.code.ByteCodes;
import org.junit.Before;
import org.junit.Test;

public class TestByteCodes {

	ByteCodes code;
	
	@Before
	public void setUp() {
		code = new  ByteCodes();
	}
	
	@Test
	public void testGenerate() throws Exception {
//		ClassPool pool = ClassPool.getDefault();
//		CtClass clazz = pool.get("net.itas.core.mode.TestUser");
//		code.generate(clazz);
	}
	
	@Test
	public void aaa() {
		
	}
	
	@Test
	public void bbb() {
		
	}
	
	public static void main(String[] args) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get("net.itas.core.mode.TestUser");
		new  ByteCodes().generate(clazz);
	}
	
}
