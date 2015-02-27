package org.itas.core.bytecode;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;

import org.itas.core.GameBaseAotuID;
import org.itas.core.Simple;
import org.junit.Before;
import org.junit.Test;

public class TestCodeSimpleProvider {

	private CodeSimpleProvider codeType;
	
	@Before
	public void setUP() {
		codeType = new CodeSimpleProvider(new Modify() {
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
		
		String expected = 
						"\t\t"
						+ "String id_bs = \"\";" 
						+ "\n\t\t"
						+ "if (getBs() != null) {" 
						+ "\n\t\t\t"
						+ "id_bs = getBs().getId();" 
						+ "\n\t\t"
						+ "}" 
						+ "\n\t\t"
						+ "state.setString(1, id_bs);";
		
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = 
				"\t\t"
				+ "String id_bs = result.getString(\"bs\");" 
				+ "\n\t\t"
				+ "if (id_bs != null && id_bs.length() > 0) {" 
				+ "\n\t\t\t"
				+ "setBs(new org.itas.core.Simple(id_bs));" 
				+ "\n\t\t"
				+ "}";
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
