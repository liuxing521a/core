package org.itas.core.code.type;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;

import org.itas.core.code.Modify;
import org.junit.Before;
import org.junit.Test;

public class TestDoubleCode {

	private DoubleCode codeType;
	
	@Before
	public void setUP() {
		codeType = new DoubleCode(new Modify() {
			@Override
			protected String toModify() {
				return null;
			}
		});
	}
	
	@Test
	public void testSetStatement() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get("org.itas.core.code.type.TestDoubleCode$Model");
		CtField field = clazz.getDeclaredField("bs");
		
		String content = codeType.setStatement(field);
		Assert.assertEquals("\t\tstate.setDouble(1, getBs());", content);
		
		content = codeType.getResultSet(field);
		Assert.assertEquals("\t\tsetBs(result.getDouble(\"bs\"));", content);
	}
	
	class Model {
		
		private double bs;

		public double getBs() {
			return bs;
		}

		public void setBs(double bs) {
			this.bs = bs;
		}
		
	}
	
}