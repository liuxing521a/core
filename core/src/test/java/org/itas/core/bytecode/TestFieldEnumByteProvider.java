package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;

import org.itas.core.EnumByte;
import org.itas.core.util.Enums;
import org.junit.Before;
import org.junit.Test;

public class TestFieldEnumByteProvider implements Enums {

	private FieldProvider codeType;
	
	@Before
	public void setUP() {
		codeType = new EnumByteProvider();
		codeType.setMethodProvider(new TestMethod());
	}
	
	@Test
	public void testEnum() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get(Model.class.getName());
		CtField field = clazz.getDeclaredField("bs");
		
		String result = 
				"\t\t"
				+ "setBs(org.itas.core.util.Utils.EnumUtils.parse(org.itas.core.EnumByte.class, result.getByte(\"bs\")));";
		
		String content = codeType.getResultSet(field);
		Assert.assertEquals(result, content);
		
		result = 
			"\t\t"
			+ "byte ebyte_bs = 0;"
			+ "\n\t\t"
			+ "if (getBs() != null) {"
			+ "\n\t\t\t"
			+ "ebyte_bs = getBs().key();"
			+ "\n\t\t"
			+ "}"
			+ "\n\t\t"
			+ "state.setByte(1, ebyte_bs);";
		
		content = codeType.setStatement(field);
		Assert.assertEquals(result, content);
	}
	
}
