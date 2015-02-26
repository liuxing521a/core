package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestStatementBooleanProvider {

	private StatementBooleanProvider codeType;
	
	@Before
	public void setUP() {
		codeType = new StatementBooleanProvider(new Modify() {
			@Override
			protected String toModify() {
				return null;
			}
		});
	}
	
	@Test
	public void testSetStatement() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get("org.itas.core.bytecode.TestStatementBooleanProvider$Model");
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
