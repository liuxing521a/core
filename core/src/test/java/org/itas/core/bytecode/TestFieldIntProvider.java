package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestFieldIntProvider {

	private FieldProvider codeType;
	
	@Before
	public void setUP() {
		codeType = new FieldIntProvider();
		codeType.setMethodProvider(new TestMethod());
	}
	
	@Test
	public void testSetStatement() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get(Model.class.getName());
		CtField field = clazz.getDeclaredField("bs");
		
		String content = codeType.setStatement(field);
		Assert.assertEquals("\t\tstate.setInt(1, getBs());", content);
		
		content = codeType.getResultSet(field);
		Assert.assertEquals("\t\tsetBs(result.getInt(\"bs\"));", content);
	}
	
	class Model {
		
		private int bs;

		public int getBs() {
			return bs;
		}

		public void setBs(int bs) {
			this.bs = bs;
		}
		
	}
	
}
