package org.itas.core.bytecode;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javassist.CtField;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.itas.core.EnumByte;
import org.itas.core.EnumInt;
import org.itas.core.EnumString;
import org.itas.core.GameObject;
import org.itas.core.GameObjectAotuID;
import org.itas.core.Resource;
import org.itas.core.Simple;
import org.junit.Before;
import org.junit.Test;

public class ListProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = ListProvider.PROVIDER;
	}
	
	@Test
	public void typeTest() {
		Assert.assertEquals(false, provider.isType(Boolean.class));
		Assert.assertEquals(false, provider.isType(boolean.class));
		
		Assert.assertEquals(false, provider.isType(byte.class));
		Assert.assertEquals(false, provider.isType(Byte.class));
		
		Assert.assertEquals(false, provider.isType(char.class));
		Assert.assertEquals(false, provider.isType(Character.class));
		
		Assert.assertEquals(false, provider.isType(short.class));
		Assert.assertEquals(false, provider.isType(Short.class));

		Assert.assertEquals(false, provider.isType(int.class));
		Assert.assertEquals(false, provider.isType(Integer.class));
		
		Assert.assertEquals(false, provider.isType(long.class));
		Assert.assertEquals(false, provider.isType(Long.class));
	
		Assert.assertEquals(false, provider.isType(float.class));
		Assert.assertEquals(false, provider.isType(Float.class));
		
		Assert.assertEquals(false, provider.isType(double.class));
		Assert.assertEquals(false, provider.isType(Double.class));

		Assert.assertEquals(false, provider.isType(String.class));
		Assert.assertEquals(false, provider.isType(Simple.class));
		Assert.assertEquals(false, provider.isType(GameObject.class));
		Assert.assertEquals(false, provider.isType(GameObjectAotuID.class));
		Assert.assertEquals(false, provider.isType(EnumByte.class));
		Assert.assertEquals(false, provider.isType(EnumInt.class));
		Assert.assertEquals(false, provider.isType(EnumString.class));
		Assert.assertEquals(false, provider.isType(Resource.class));
		Assert.assertEquals(true, provider.isType(ArrayList.class));
		Assert.assertEquals(false, provider.isType(HashSet.class));
		Assert.assertEquals(false, provider.isType(HashMap.class));
		Assert.assertEquals(false, provider.isType(Timestamp.class));
	}

	@Override
	public void setStatementTest() throws Exception {
		simpleStatementTest();
		javaBaseStatementTest();
		resourceStatementTest();
		enumStatementTest();
		enumbyteStatementTest();
		enumIntStatementTest();
		enumStringStatementTest();
	}

	@Override
	public void getResultSetTest() throws Exception {
		javaBaseResultTest();
		simpleResultTest();
		resourceResultTest();
		enumResultTest();
		enumbyteResultTest();
		enumIntResultTest();
		enumStringResultTest();
	}
	
	public void javaBaseResultTest() throws Exception {
		field = clazz.getDeclaredField("points");
		
		String expected = "\n\t\t"
				+ "state.setString(1, toString(getPoints()));";
		
		String actual = provider.setStatement(1, field);
		Assert.assertEquals(expected, actual);
	}
	
	public void javaBaseStatementTest() throws Exception {
		field = clazz.getDeclaredField("points");
		
		String expected = 
				"\n\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String value_ = result.getString(\"points\");"
				+ "\n\t\t\t"
				+ "String[] valueArray_ = parseArray(value_);"
				+ "\n\t\t\t"
				+ "java.util.List valueList_ = new java.util.LinkedList();"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"
				+ "\n\t\t\t\t"
				+ "valueList_.add(java.lang.Integer.valueOf(valueArray_[i]));"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setPoints(valueList_);"
				+ "\n\t\t"
				+ "}";
		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
	public void simpleResultTest() throws Exception {
		field = clazz.getDeclaredField("depotS");
		
		String expected =	"\n\t\t"
				+ "state.setString(1, toString(getDepotS()));";
		
		String actual = provider.setStatement(1, field);
		Assert.assertEquals(expected, actual);
	}
	
	public void simpleStatementTest() throws Exception {
		field = clazz.getDeclaredField("depotS");
		
		String expected = "\n\t\t"
				+ "{" + "\n\t\t\t"
				+ "String value_ = result.getString(\"depotS\");" + "\n\t\t\t"
				+ "String[] valueArray_ = parseArray(value_);" + "\n\t\t\t"
				+ "java.util.List valueList_ = new java.util.ArrayList(8);" + "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {" + "\n\t\t\t\t"
				+ "valueList_.add(new org.itas.core.Simple(valueArray_[i]));" + "\n\t\t\t"
				+ "}" + "\n\t\t\t"
				+ "setDepotS(valueList_);" + "\n\t\t"
				+ "}";

		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
	public void resourceStatementTest() throws Exception {
		field = clazz.getDeclaredField("heroResList");
		
		String expected = 
				"\n\t\t"
				+ "state.setString(1, toString(getHeroResList()));";
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}

	public void resourceResultTest() throws Exception {
		field = clazz.getDeclaredField("heroResList");
		
		String expected = 
				"\n\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String value_ = result.getString(\"heroResList\");"
				+ "\n\t\t\t"
				+ "String[] valueArray_ = parseArray(value_);"
				+ "\n\t\t\t"
				+ "java.util.List valueList_ = new java.util.ArrayList(16);"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"
				+ "\n\t\t\t\t"
				+ "valueList_.add(org.itas.core.Pool.getResource(valueArray_[i]));"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setHeroResList(valueList_);"
				+ "\n\t\t"
				+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroTypeList");
		
		String expected = 
				"\n\t\t"
						+ "state.setString(1, toString(getHeroTypeList()));";
		
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroTypeList");
		
		String expected = 
				"\n\t\t"
						+ "{"
						+ "\n\t\t\t"
						+ "String value_ = result.getString(\"heroTypeList\");"
						+ "\n\t\t\t"
						+ "String[] valueArray_ = parseArray(value_);"
						+ "\n\t\t\t"
						+ "java.util.List valueList_ = new java.util.ArrayList(8);"
						+ "\n\t\t\t"
						+ "for (int i = 0; i < valueArray_.length; i ++) {"
						+ "\n\t\t\t\t"
						+ "valueList_.add(parse(org.itas.core.bytecode.Model.HeroType.class, valueArray_[i]));"
						+ "\n\t\t\t"
						+ "}"
						+ "\n\t\t\t"
						+ "setHeroTypeList(valueList_);"
						+ "\n\t\t"
						+ "}";
		//						 bsArray.add(%s);
		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	
	public void enumbyteStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("sexTypeList");
		
		String expected = 
				"\n\t\t"
						+ "state.setString(1, toString(getSexTypeList()));";
		
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumbyteResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("sexTypeList");
		
		String expected = 
				"\n\t\t"
						+ "{"
						+ "\n\t\t\t"
						+ "String value_ = result.getString(\"sexTypeList\");"
						+ "\n\t\t\t"
						+ "String[] valueArray_ = parseArray(value_);"
						+ "\n\t\t\t"
						+ "java.util.List valueList_ = new java.util.ArrayList(8);"
						+ "\n\t\t\t"
						+ "for (int i = 0; i < valueArray_.length; i ++) {"
						+ "\n\t\t\t\t"
						+ "valueList_.add(parse(org.itas.core.bytecode.Model.SexType.class, java.lang.Byte.valueOf(valueArray_[i])));"
						+ "\n\t\t\t"
						+ "}"
						+ "\n\t\t\t"
						+ "setSexTypeList(valueList_);"
						+ "\n\t\t"
						+ "}";
		//						 bsArray.add(%s);
		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumIntStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("effectTypeList");
		
		String expected = 
				"\n\t\t"
				+ "state.setString(1, toString(getEffectTypeList()));";
		
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumIntResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("effectTypeList");
		
		String expected = 
				"\n\t\t"
				+ "{"
				+ "\n\t\t\t"
				+ "String value_ = result.getString(\"effectTypeList\");"
				+ "\n\t\t\t"
				+ "String[] valueArray_ = parseArray(value_);"
				+ "\n\t\t\t"
				+ "java.util.List valueList_ = new java.util.ArrayList(8);"
				+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"
				+ "\n\t\t\t\t"
				+ "valueList_.add(parse(org.itas.core.bytecode.Model.Effect.class, java.lang.Integer.valueOf(valueArray_[i])));"
				+ "\n\t\t\t"
				+ "}"
				+ "\n\t\t\t"
				+ "setEffectTypeList(valueList_);"
				+ "\n\t\t"
				+ "}";
				//						 bsArray.add(%s);
		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumStringStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("skillTypeList");
		
		String expected = 
				"\n\t\t"
						+ "state.setString(1, toString(getSkillTypeList()));";
		
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumStringResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("skillTypeList");
		
		String expected = 
				"\n\t\t"
						+ "{"
						+ "\n\t\t\t"
						+ "String value_ = result.getString(\"skillTypeList\");"
						+ "\n\t\t\t"
						+ "String[] valueArray_ = parseArray(value_);"
						+ "\n\t\t\t"
						+ "java.util.List valueList_ = new java.util.ArrayList(8);"
						+ "\n\t\t\t"
						+ "for (int i = 0; i < valueArray_.length; i ++) {"
						+ "\n\t\t\t\t"
						+ "valueList_.add(parse(org.itas.core.bytecode.Model.SkillType.class, valueArray_[i]));"
						+ "\n\t\t\t"
						+ "}"
						+ "\n\t\t\t"
						+ "setSkillTypeList(valueList_);"
						+ "\n\t\t"
						+ "}";
		//						 bsArray.add(%s);
		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
}
