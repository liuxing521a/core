package org.itas.core.bytecode;

import java.sql.ResultSet;
import java.sql.SQLException;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;

import org.itas.core.EnumByte;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.PreparedStatement;

public class TestFieldEnumByteProvider {

	private FieldEnumByteProvider codeType;
	
	@Before
	public void setUP() {
		codeType = new FieldEnumByteProvider(new Modify() {
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
	
	class Model {
		
		private EnumByte bs;

		public EnumByte getBs() {
			return bs;
		}

		public void setBs(EnumByte bs) {
			this.bs = bs;
		}
		
		public void setState(PreparedStatement state) throws SQLException {
			byte ebyte_bs = 0;
			if (getBs() != null) {
				ebyte_bs = getBs().key();
			}
			
			state.setByte(1, ebyte_bs);
		}
		
		public void getResult(ResultSet result) throws SQLException {
			setBs(org.itas.core.util.Utils.EnumUtils.parse(EnumByte.class, result.getByte("bs")));
		}
	}
	
}
