package org.itas.core.code.type;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;

import org.itas.core.GameBaseAotuID;
import org.itas.core.Simple;
import org.itas.core.code.Modify;
import org.junit.Before;
import org.junit.Test;

public class TestSimpleCode {

	private SimpleCode codeType;
	
	@Before
	public void setUP() {
		codeType = new SimpleCode(new Modify() {
			@Override
			protected String toModify() {
				return null;
			}
		});
	}
	
	@Test
	public void testSetStatement() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get("org.itas.core.code.type.TestSimpleCode$Model");
		CtField field = clazz.getDeclaredField("bs");
		
		String expected = "\t\tString id_bs = \"\";" + 
						"\n\t\tif (getBs() != null) {" +
						"\n\t\t\tid_bs = getBs().getId();" + 
						"\n\t\t}" + 
						"\n\t\tstate.setString(1, id_bs);";
		
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = "\t\tString id_bs = result.getString(\"bs\");" +
					"\n\t\tif (org.itas.util.Utils.Objects.nonEmpty(id_bs)) {" +
						"\n\t\t\tsetBs(new org.itas.core.Simple(id_bs));" +
					"\n\t\t}";
		content = codeType.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	
	
	class Model {
		
		private Simple<TestMode> bs;

		public Simple<TestMode> getBs() {
			return bs;
		}

		public void setBs(Simple<TestMode> bs) {
			this.bs = bs;
		}
		
	}
	
	private class TestMode extends GameBaseAotuID {

		protected TestMode(String Id) {
			super(Id);
		}

		@Override
		public void writeExternal(ObjectOutput out) throws IOException {
			
		}

		@Override
		public void readExternal(ObjectInput in) throws IOException,
				ClassNotFoundException {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected String PRIFEX() {
			// TODO Auto-generated method stub
			return null;
		}

	}
	
}
