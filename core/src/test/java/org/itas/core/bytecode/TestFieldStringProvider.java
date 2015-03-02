package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestFieldStringProvider {

	private FieldProvider codeType;
	
	@Before
	public void setUP() throws NotFoundException {
		codeType = new FieldStringProvider();
		codeType.setMethodProvider(new TestMethod());
	}
	
	@Test
	public void testSetStatement() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get(Model.class.getName());
		CtField field = clazz.getDeclaredField("bs");
		
		String content = codeType.setStatement(field);
		Assert.assertEquals("\t\tstate.setString(1, getBs());", content);
		
		content = codeType.getResultSet(field);
		Assert.assertEquals("\t\tsetBs(result.getString(\"bs\"));", content);
	}
	
	class Model {
		
		private String bs;

		public String getBs() {
			return bs;
		}

		public void setBs(String bs) {
			this.bs = bs;
		}
		
	}
	
}
