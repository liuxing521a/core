package org.itas.core.bytecode;

import java.sql.ResultSet;
import java.sql.SQLException;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;

import org.itas.core.EnumString;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.PreparedStatement;

public class TestCodeEnumStringProvider {

	private CodeEnumStringProvider codeType;
	
	@Before
	public void setUP() {
		codeType = new CodeEnumStringProvider(new Modify() {
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
		
		public void setState(PreparedStatement state) throws SQLException {
			String ebyte_bs = "";
			if (getBs() != null) {
				ebyte_bs = getBs().key();
			}
			
			state.setString(1, ebyte_bs);
		}
		
		public void getResult(ResultSet result) throws SQLException {
			setBs(org.itas.core.util.Utils.EnumUtils.parse(EnumString.class, result.getString("bs")));
		}
	}
	
}
