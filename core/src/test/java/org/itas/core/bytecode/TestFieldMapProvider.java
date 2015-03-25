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

import org.itas.core.EnumByte;
import org.itas.core.GameObject;
import org.itas.core.Resource;
import org.itas.core.Simple;
import org.itas.core.annotation.Clazz;
import org.itas.core.annotation.Size;
import org.junit.Before;
import org.junit.Test;

public class TestFieldMapProvider {

	private CtClass clazz;
	
	private FieldProvider codeType;
	
	@Before
	public void setUP() throws NotFoundException {
		codeType = new MapProvider();
		codeType.setMethodProvider(new TestMethod());
		ClassPool pool = ClassPool.getDefault();
		clazz = pool.get(Model.class.getName());
	}
	
	@Test
	public void testSimple() throws Exception {
		CtField field = clazz.getDeclaredField("bs");
		
		String expected = 
				"\t\t"
						+ "state.setString(1, toString(getBs()));";
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = 
				"\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String datas = result.getString(\"bs\");"
				+ "\n\t\t\t"
				+ "org.itas.util.Pair[] dataArray = parsePair(datas);"
				+ "\n\t\t\t"
				+ "java.util.Map dataMap = new java.util.LinkedHashMap(8);"
				+ "\n\t\t\t"
				+ "org.itas.util.Pair pair;"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < dataArray.length; i ++) {"
				+ "\n\t\t\t\t"
				+ "pair = dataArray[i];"
				+ "\n\t\t\t\t"
				+ "dataMap.put(new org.itas.core.Simple((String)pair.getKey()), (String)pair.getValue());"
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
						+ "state.setString(1, toString(getRs()));";
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = 
				"\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String datas = result.getString(\"rs\");"
				+ "\n\t\t\t"
				+ "org.itas.util.Pair[] dataArray = parsePair(datas);"
				+ "\n\t\t\t"
				+ "java.util.Map dataMap = new java.util.HashMap(16);"
				+ "\n\t\t\t"
				+ "org.itas.util.Pair pair;"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < dataArray.length; i ++) {"
				+ "\n\t\t\t\t"
				+ "pair = dataArray[i];"
				+ "\n\t\t\t\t"
				+ "dataMap.put(org.itas.core.Pool.getResource((String)pair.getKey()), java.lang.Integer.valueOf((String)pair.getValue()));"
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
						+ "state.setString(1, toString(getAs()));";
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = 
				"\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String datas = result.getString(\"as\");"
				+ "\n\t\t\t"
				+ "org.itas.util.Pair[] dataArray = parsePair(datas);"
				+ "\n\t\t\t"
				+ "java.util.Map dataMap = new java.util.HashMap(8);"
				+ "\n\t\t\t"
				+ "org.itas.util.Pair pair;"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < dataArray.length; i ++) {"
				+ "\n\t\t\t\t"
				+ "pair = dataArray[i];"
				+ "\n\t\t\t\t"
				+ "dataMap.put(java.lang.Integer.valueOf((String)pair.getKey()), new org.itas.core.Simple((String)pair.getValue()));"
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
						+ "state.setString(1, toString(getEs()));";
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = 
				"\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String datas = result.getString(\"es\");"
				+ "\n\t\t\t"
				+ "org.itas.util.Pair[] dataArray = parsePair(datas);"
				+ "\n\t\t\t"
				+ "java.util.Map dataMap = new java.util.HashMap(8);"
				+ "\n\t\t\t"
				+ "org.itas.util.Pair pair;"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < dataArray.length; i ++) {"
				+ "\n\t\t\t\t"
				+ "pair = dataArray[i];"
				+ "\n\t\t\t\t"
				+ "dataMap.put(org.itas.core.util.Utils.EnumUtils.parse(org.itas.core.EnumByte.class, java.lang.Byte.valueOf((String)pair.getKey())), java.lang.Float.valueOf((String)pair.getValue()));"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setEs(dataMap);"
				+ "\n\t\t"
				+ "}";

		content = codeType.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
}
