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

import org.itas.core.EnumByte;
import org.itas.core.GameObject;
import org.itas.core.Resource;
import org.itas.core.Simple;
import org.itas.core.annotation.Clazz;
import org.itas.core.annotation.Size;
import org.junit.Before;
import org.junit.Test;

public class TestFieldListProvider {

	private CtClass clazz;
	
	private FieldProvider codeType;
	
	@Before
	public void setUP() throws NotFoundException {
		codeType = new ListProvider();
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
				+ "String data = result.getString(\"bs\");"
				+ "\n\t\t\t"
				+ "java.lang.String[] dataStrList = parseArray(data);"
				+ "\n\t\t\t"
				+ "java.util.List dataArray = new java.util.LinkedList();"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < dataStrList.length; i ++) {"
				+ "\n\t\t\t\t"
				+ "dataArray.add(new org.itas.core.Simple(dataStrList[i]));"
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
				+ "state.setString(1, toString(getRs()));";
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = 
				"\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String data = result.getString(\"rs\");"
				+ "\n\t\t\t"
				+ "java.lang.String[] dataStrList = parseArray(data);"
				+ "\n\t\t\t"
				+ "java.util.List dataArray = new java.util.ArrayList(16);"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < dataStrList.length; i ++) {"
				+ "\n\t\t\t\t"
				+ "dataArray.add(org.itas.core.Pool.getResource(dataStrList[i]));"
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
						+ "state.setString(1, toString(getAs()));";
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = 
				"\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String data = result.getString(\"as\");"
				+ "\n\t\t\t"
				+ "java.lang.String[] dataStrList = parseArray(data);"
				+ "\n\t\t\t"
				+ "java.util.List dataArray = new java.util.ArrayList(8);"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < dataStrList.length; i ++) {"
				+ "\n\t\t\t\t"
				+ "dataArray.add(java.lang.Integer.valueOf(dataStrList[i]));"
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
						+ "state.setString(1, toString(getEs()));";
		String content = codeType.setStatement(field);
		Assert.assertEquals(expected, content);
		
		expected = 
				"\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String data = result.getString(\"es\");"
				+ "\n\t\t\t"
				+ "java.lang.String[] dataStrList = parseArray(data);"
				+ "\n\t\t\t"
				+ "java.util.List dataArray = new java.util.ArrayList(8);"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < dataStrList.length; i ++) {"
				+ "\n\t\t\t\t"
				+ "dataArray.add(org.itas.core.util.Utils.EnumUtils.parse(org.itas.core.EnumByte.class, java.lang.Byte.valueOf(dataStrList[i])));"
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
	
	
}
