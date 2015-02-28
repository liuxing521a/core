package org.itas.core.bytecode;

import java.sql.ResultSet;
import java.sql.SQLException;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;

import org.itas.core.EnumInt;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.PreparedStatement;

public class TestFieldEnumIntProvider {

	private FieldEnumIntProvider codeType;
	
	@Before
	public void setUP() {
		codeType = new FieldEnumIntProvider(new Modify() {
			@Override
			protected String toModify() {
				return null;
			}
		});
	}
	
	@Test
	public void testEnum() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get(Model.class.getName());
		CtField field = clazz.getDeclaredField("bs");
		
		String result = 
				"\t\t"
				+ "setBs(org.itas.core.util.Utils.EnumUtils.parse(org.itas.core.EnumInt.class, result.getInt(\"bs\")));";
		
		String content = codeType.getResultSet(field);
		Assert.assertEquals(result, content);
		
		result = 
			"\t\t"
			+ "int eint_bs = 0;"
			+ "\n\t\t"
			+ "if (getBs() != null) {"
			+ "\n\t\t\t"
			+ "eint_bs = getBs().key();"
			+ "\n\t\t"
			+ "}"
			+ "\n\t\t"
			+ "state.setInt(1, eint_bs);";
		
		content = codeType.setStatement(field);
		Assert.assertEquals(result, content);
	}
	
	class Model {
		
		private EnumInt bs;

		public EnumInt getBs() {
			return bs;
		}

		public void setBs(EnumInt bs) {
			this.bs = bs;
		}
		
		public void setState(PreparedStatement state) throws SQLException {
			byte ebyte_bs = 0;
			if (getBs() != null) {
				ebyte_bs = getBs().key();
			}
			
			state.setInt(1, ebyte_bs);
		}
		
		public void getResult(ResultSet result) throws SQLException {
//			setBs(org.itas.core.util.Utils.EnumUtils.parse(EnumInt.class, result.getInt("bs")));
		}
	}
	
}
