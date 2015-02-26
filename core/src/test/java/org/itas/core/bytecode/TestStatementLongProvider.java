package org.itas.core.code.type;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;

import org.itas.core.code.Modify;
import org.junit.Before;
import org.junit.Test;

public class TestLongCode {

	private LongCode codeType;
	
	@Before
	public void setUP() {
		codeType = new LongCode(new Modify() {
			@Override
			protected String toModify() {
				return null;
			}
		});
	}
	
	@Test
	public void testSetStatement() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get("org.itas.core.code.type.TestLongCode$Model");
		CtField field = clazz.getDeclaredField("bs");
		
		String content = codeType.setStatement(field);
		Assert.assertEquals("\t\tstate.setLong(1, getBs());", content);
		
		content = codeType.getResultSet(field);
		Assert.assertEquals("\t\tsetBs(result.getLong(\"bs\"));", content);
	}
	
	class Model {
		
		private long bs;

		public long getBs() {
			return bs;
		}

		public void setBs(long bs) {
			this.bs = bs;
		}
		
	}
	
}
