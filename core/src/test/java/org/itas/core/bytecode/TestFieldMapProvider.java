package org.itas.core.bytecode;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import junit.framework.Assert;
import net.itas.core.annotation.Clazz;
import net.itas.core.annotation.Size;

import org.itas.core.EnumByte;
import org.itas.core.GameObject;
import org.itas.core.Simple;
import org.itas.core.resource.Resource;
import org.junit.Before;
import org.junit.Test;

public class TestFieldMapProvider {

	private CtClass clazz;
	
	private FieldProvider codeType;
	
	@Before
	public void setUP() throws NotFoundException {
		codeType = new FieldMapProvider();
		codeType.setMethodProvider(new TestMethod());
		ClassPool pool = ClassPool.getDefault();
		clazz = pool.get(Model.class.getName());
	}
	
	@Test
	public void testSimple() throws Exception {
		CtField field = clazz.getDeclaredField("bs");
		
		String expected = 
				"\t\t"
						+ "state.setString(1, org.itas.core.util.GameObjects.toString(getBs()));";
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = 
				"\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String datas = result.getString(\"bs\");"
				+ "\n\t\t\t"
				+ "java.util.Map dataStrMap = org.itas.core.util.GameObjects.parseMap(datas);"
				+ "\n\t\t\t"
				+ "java.util.Map dataMap = new java.util.LinkedHashMap(8);"
				+ "\n\t\t\t"
				+ "java.util.Iterator it = dataStrMap.entrySet().iterator();"
				+ "\n\t\t\t"
				+ "java.util.Map.Entry entry;"
				+ "\n\t\t\t"
				+ "while (it.hasNext()) {"
				+ "\n\t\t\t\t"
				+ "entry = (java.util.Map.Entry)it.next();"
				+ "\n\t\t\t\t"
				+ "dataMap.put(new org.itas.core.Simple((String)entry.getKey()), (String)entry.getValue());"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setBs(dataMap);"
				+ "\n\t\t"
				+ "}";

		content = codeType.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	@Test
	public void testResource() throws Exception {
		CtField field = clazz.getDeclaredField("rs");
		
		String expected = 
				"\t\t"
						+ "state.setString(1, org.itas.core.util.GameObjects.toString(getRs()));";
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = 
				"\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String datas = result.getString(\"rs\");"
				+ "\n\t\t\t"
				+ "java.util.Map dataStrMap = org.itas.core.util.GameObjects.parseMap(datas);"
				+ "\n\t\t\t"
				+ "java.util.Map dataMap = new java.util.HashMap(16);"
				+ "\n\t\t\t"
				+ "java.util.Iterator it = dataStrMap.entrySet().iterator();"
				+ "\n\t\t\t"
				+ "java.util.Map.Entry entry;"
				+ "\n\t\t\t"
				+ "while (it.hasNext()) {"
				+ "\n\t\t\t\t"
				+ "entry = (java.util.Map.Entry)it.next();"
				+ "\n\t\t\t\t"
				+ "dataMap.put(org.itas.core.Pool.getResource((String)entry.getKey()), java.lang.Integer.valueOf((String)entry.getValue()));"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setRs(dataMap);"
				+ "\n\t\t"
				+ "}";

		content = codeType.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	@Test
	public void testJavaBase() throws Exception {
		CtField field = clazz.getDeclaredField("as");
		
		String expected = 
				"\t\t"
						+ "state.setString(1, org.itas.core.util.GameObjects.toString(getAs()));";
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = 
				"\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String datas = result.getString(\"as\");"
				+ "\n\t\t\t"
				+ "java.util.Map dataStrMap = org.itas.core.util.GameObjects.parseMap(datas);"
				+ "\n\t\t\t"
				+ "java.util.Map dataMap = new java.util.HashMap(8);"
				+ "\n\t\t\t"
				+ "java.util.Iterator it = dataStrMap.entrySet().iterator();"
				+ "\n\t\t\t"
				+ "java.util.Map.Entry entry;"
				+ "\n\t\t\t"
				+ "while (it.hasNext()) {"
				+ "\n\t\t\t\t"
				+ "entry = (java.util.Map.Entry)it.next();"
				+ "\n\t\t\t\t"
				+ "dataMap.put(java.lang.Integer.valueOf((String)entry.getKey()), new org.itas.core.Simple((String)entry.getValue()));"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setAs(dataMap);"
				+ "\n\t\t"
				+ "}";

		content = codeType.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	@Test
	public void testEnum() throws Exception {
		CtField field = clazz.getDeclaredField("es");
		
		String expected = 
						"\t\t"
						+ "state.setString(1, org.itas.core.util.GameObjects.toString(getEs()));";
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = 
				"\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String datas = result.getString(\"es\");"
				+ "\n\t\t\t"
				+ "java.util.Map dataStrMap = org.itas.core.util.GameObjects.parseMap(datas);"
				+ "\n\t\t\t"
				+ "java.util.Map dataMap = new java.util.HashMap(8);"
				+ "\n\t\t\t"
				+ "java.util.Iterator it = dataStrMap.entrySet().iterator();"
				+ "\n\t\t\t"
				+ "java.util.Map.Entry entry;"
				+ "\n\t\t\t"
				+ "while (it.hasNext()) {"
				+ "\n\t\t\t\t"
				+ "entry = (java.util.Map.Entry)it.next();"
				+ "\n\t\t\t\t"
				+ "dataMap.put(org.itas.core.util.Utils.EnumUtils.parse(org.itas.core.EnumByte.class, java.lang.Byte.valueOf((String)entry.getKey())), java.lang.Float.valueOf((String)entry.getValue()));"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setEs(dataMap);"
				+ "\n\t\t"
				+ "}";

		content = codeType.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	
	
	class Model {
		
		@Clazz(LinkedHashMap.class)
		private Map<Simple<TestMode>, String> bs;

		@Size(16)
		private Map<TestRes, Integer> rs;

		private Map<Integer, Simple<TestMode>> as;
		
		private Map<EnumByte, Float> es;

		public Map<Simple<TestMode>, String> getBs() {
			return bs;
		}

		public void setBs(Map<Simple<TestMode>, String> bs) {
			this.bs = bs;
		}

		public Map<TestRes, Integer> getRs() {
			return rs;
		}

		public void setRs(Map<TestRes, Integer> rs) {
			this.rs = rs;
		}

		public Map<Integer, Simple<TestMode>> getAs() {
			return as;
		}

		public void setAs(Map<Integer, Simple<TestMode>> as) {
			this.as = as;
		}

		public Map<EnumByte, Float> getEs() {
			return es;
		}

		public void setEs(Map<EnumByte, Float> es) {
			this.es = es;
		}
		
		public void setResultSet(ResultSet result) throws SQLException {
			
		}
	}
	
	public class TestRes extends Resource {

		protected TestRes(String Id) {
			super(Id);
		}
		
	}
	
	class TestMode extends GameObject {

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

		@Override
		protected <T extends GameObject> T autoInstance(String Id) {
			// TODO Auto-generated method stub
			return null;
		}

	}
	
}
