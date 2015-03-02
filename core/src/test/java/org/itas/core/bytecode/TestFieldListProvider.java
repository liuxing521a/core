package org.itas.core.bytecode;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.LinkedList;
import java.util.List;

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

public class TestFieldListProvider {

	private CtClass clazz;
	
	private FieldProvider codeType;
	
	@Before
	public void setUP() throws NotFoundException {
		codeType = new FieldListProvider();
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
				+ "String data = result.getString(\"bs\");"
				+ "\n\t\t\t"
				+ "java.util.List dataStrList = org.itas.core.util.GameObjects.parseList(data);"
				+ "\n\t\t\t"
				+ "java.util.List dataArray = new java.util.LinkedList();"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < dataStrList.size(); i ++) {"
				+ "\n\t\t\t\t"
				+ "dataArray.add(new org.itas.core.Simple((String)dataStrList.get(i)));"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setBs(dataArray);"
				+ "\n\t\t"
				+ "}";
		//						 bsArray.add(%s);
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
				+ "String data = result.getString(\"rs\");"
				+ "\n\t\t\t"
				+ "java.util.List dataStrList = org.itas.core.util.GameObjects.parseList(data);"
				+ "\n\t\t\t"
				+ "java.util.List dataArray = new java.util.ArrayList(16);"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < dataStrList.size(); i ++) {"
				+ "\n\t\t\t\t"
				+ "dataArray.add(org.itas.core.Pool.getResource((String)dataStrList.get(i)));"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setRs(dataArray);"
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
				+ "String data = result.getString(\"as\");"
				+ "\n\t\t\t"
				+ "java.util.List dataStrList = org.itas.core.util.GameObjects.parseList(data);"
				+ "\n\t\t\t"
				+ "java.util.List dataArray = new java.util.ArrayList(8);"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < dataStrList.size(); i ++) {"
				+ "\n\t\t\t\t"
				+ "dataArray.add(java.lang.Integer.valueOf((String)dataStrList.get(i)));"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setAs(dataArray);"
				+ "\n\t\t"
				+ "}";
		//						 bsArray.add(%s);
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
				+ "String data = result.getString(\"es\");"
				+ "\n\t\t\t"
				+ "java.util.List dataStrList = org.itas.core.util.GameObjects.parseList(data);"
				+ "\n\t\t\t"
				+ "java.util.List dataArray = new java.util.ArrayList(8);"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < dataStrList.size(); i ++) {"
				+ "\n\t\t\t\t"
				+ "dataArray.add(org.itas.core.util.Utils.EnumUtils.parse(org.itas.core.EnumByte.class, java.lang.Byte.valueOf((String)dataStrList.get(i))));"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setEs(dataArray);"
				+ "\n\t\t"
				+ "}";
				//						 bsArray.add(%s);
		content = codeType.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	
	
	class Model {
		
		@Clazz(LinkedList.class) @Size(106)
		private List<Simple<TestMode>> bs;

		@Size(16)
		private List<TestRes> rs;

		private List<Integer> as;
		
		private List<EnumByte> es;

		public List<Simple<TestMode>> getBs() {
			return bs;
		}

		public void setBs(List<Simple<TestMode>> bs) {
			this.bs = bs;
		}

		public List<TestRes> getRs() {
			return rs;
		}

		public void setRs(List<TestRes> rs) {
			this.rs = rs;
		}

		public List<Integer> getAs() {
			return as;
		}

		public void setAs(List<Integer> as) {
			this.as = as;
		}

		public List<EnumByte> getEs() {
			return es;
		}

		public void setEs(List<EnumByte> es) {
			this.es = es;
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
