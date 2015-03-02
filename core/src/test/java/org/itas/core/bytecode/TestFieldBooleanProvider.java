package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestFieldBooleanProvider {

	private FieldProvider codeType;
	
	@Before
	public void setUP() {
		codeType = new FieldBooleanProvider();
		codeType.setMethodProvider(new TestMethod());
	}
	
	@Test
	public void testSetStatement() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get(Model.class.getName());
		CtField field = clazz.getDeclaredField("bs");
		
		String content = codeType.setStatement(field);
		Assert.assertEquals("\t\tstate.setBoolean(1, getBs());", content);
		
		content = codeType.getResultSet(field);
		Assert.assertEquals("\t\tsetBs(result.getBoolean(\"bs\"));", content);
	}
	
	class Model {
		
		private boolean bs;

		public boolean getBs() {
			return bs;
		}

		public void setBs(boolean bs) {
			this.bs = bs;
		}
		
	}
	
}
