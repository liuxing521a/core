package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;

import org.itas.core.bytecode.StatementByteProvider;
import org.itas.core.bytecode.Modify;
import org.junit.Before;
import org.junit.Test;

public class TestStatementByteProvider {

	private StatementByteProvider codeType;
	
	@Before
	public void setUP() {
		codeType = new StatementByteProvider(new Modify() {
			@Override
			protected String toModify() {
				return null;
			}
		});
	}
	
	@Test
	public void testSetStatement() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get("org.itas.core.bytecode.TestStatementByteProvider$Model");
		CtField field = clazz.getDeclaredField("bs");
		
		String content = codeType.setStatement(field);
		Assert.assertEquals("\t\tstate.setByte(1, getBs());", content);
		
		content = codeType.getResultSet(field);
		Assert.assertEquals("\t\tsetBs(result.getByte(\"bs\"));", content);
	}
	
	class Model {
		
		private byte bs;

		public byte getBs() {
			return bs;
		}

		public void setBs(byte bs) {
			this.bs = bs;
		}
		
	}
	
}
