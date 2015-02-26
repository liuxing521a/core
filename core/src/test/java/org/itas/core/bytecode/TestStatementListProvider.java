package org.itas.core.bytecode;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import junit.framework.Assert;
import net.itas.core.annotation.Clazz;

import org.itas.core.GameBaseAotuID;
import org.junit.Before;
import org.junit.Test;

import com.mysql.jdbc.PreparedStatement;

public class TestStatementListProvider {

	private StatementListProvider codeType;
	
	@Before
	public void setUP() {
		codeType = new StatementListProvider(new Modify() {
			@Override
			protected String toModify() {
				return null;
			}
		});
	}
	
	@Test
	public void testSetStatement() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass clazz = pool.get("org.itas.core.bytecode.TestStatementListProvider$Model");
		CtField field = clazz.getDeclaredField("bs");
		
		String expected = 
						"\t\t"
						+ "state.setString(1, org.itas.core.util.GameObjects.toString(getBs()));";
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = 
				"\t\t"
				+ "String bsData = result.getString(\"bs\");"
				+ "\n\t\t"
				+ "java.util.List bsList = org.itas.core.util.GameObjects.parseList(bsData);"
				+ "\n\t\t"
				+ "java.util.List bsArray = new LinkedList();"
				+ "\n\t\t"
				+ "for (Object value : bsList) {"
				+ "\n\t\t"
				+ "}"
				+ "\n\t\t"
				+ "setBs(bsArray);";
				//						 bsArray.add(%s);
		content = codeType.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	
	
	class Model {
		
		@Clazz(LinkedList.class)
		private List<TestMode> bs;

		public List<TestMode> getBs() {
			return bs;
		}

		public void setBs(List<TestMode> bs) {
			this.bs = bs;
		}
		
		public void load(ResultSet result) throws SQLException {
					 String bsData = result.getString("bs");
					 java.util.List bsList = org.itas.core.util.GameObjects.parseList(bsData);
					 java.util.List bsArray = new LinkedList();
					 for (Object value : bsList) {
//						 bsArray.add(%s);
					 }
					 setBs(bsArray);;
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
