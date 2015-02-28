package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class TestFieldLongProvider {

	private FieldLongProvider codeType;
	
	@Before
	public void setUP() {
		codeType = new FieldLongProvider(new Modify() {
			@Override
			protected String toModify() {
				return null;
			}
		});
	}
	
	@Test
	public void testSetStatement() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get(Model.class.getName());
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
