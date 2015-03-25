package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.itas.core.Resource;
import org.junit.Before;
import org.junit.Test;

public class TestFieldResourcesProvider {
	
	
	private FieldProvider codeType;
	
	@Before
	public void setUP() throws NotFoundException {
		codeType = new ResourceProvider();
		codeType.setMethodProvider(new TestMethod());
	}
	
	@Test
	public void testSetStatement() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get(Model.class.getName());
		CtField field = clazz.getDeclaredField("bs");
		
		String expected = 
						"\t\t"
						+ "String rid_bs = \"\";"  
						+ "\n\t\t"
						+ "if (getBs() != null) {" 
						+ "\n\t\t\t"
						+ "rid_bs = getBs().getId();" 
						+ "\n\t\t"
						+ "}" 
						+ "\n\t\t"
						+ "state.setString(1, rid_bs);";
		
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = 
				"\t\t"
				+ "String rid_bs = result.getString(\"bs\");" 
				+ "\n\t\t"
				+ "if (rid_bs != null && rid_bs.length() > 0) {" 
				+ "\n\t\t\t"
				+ "setBs(org.itas.core.Pool.getResource(rid_bs));" 
				+ "\n\t\t"
				+ "}";
		content = codeType.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
}
