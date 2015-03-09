package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;

import org.itas.core.EnumString;
import org.junit.Before;
import org.junit.Test;

public class TestFieldEnumStringProvider {

	private FieldProvider codeType;
	
	@Before
	public void setUP() {
		codeType = new FieldEnumStringProvider();
		codeType.setMethodProvider(new TestMethod());
	}
	
	@Test
	public void testEnum() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get(Model.class.getName());
		CtField field = clazz.getDeclaredField("bs");
		
		String result = 
				"\t\t"
				+ "setBs(org.itas.core.util.Utils.EnumUtils.parse(org.itas.core.EnumString.class, result.getString(\"bs\")));";
		
		String content = codeType.getResultSet(field);
		Assert.assertEquals(result, content);
		
		result = 
			"\t\t"
			+ "String estr_bs = \"\";"
			+ "\n\t\t"
			+ "if (getBs() != null) {"
			+ "\n\t\t\t"
			+ "estr_bs = getBs().key();"
			+ "\n\t\t"
			+ "}"
			+ "\n\t\t"
			+ "state.setString(1, estr_bs);";
		
		content = codeType.setStatement(field);
		Assert.assertEquals(result, content);
	}
	
	class Model {
		
		private EnumString bs;

		public EnumString getBs() {
			return bs;
		}

		public void setBs(EnumString bs) {
			this.bs = bs;
		}
	}
	
}
