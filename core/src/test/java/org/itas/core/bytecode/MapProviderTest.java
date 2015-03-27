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

public class MapProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = MapProvider.PROVIDER;
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
		Assert.assertEquals(false, provider.isType(ArrayList.class));
		Assert.assertEquals(false, provider.isType(HashSet.class));
		Assert.assertEquals(true, provider.isType(HashMap.class));
		Assert.assertEquals(false, provider.isType(Timestamp.class));
	}
	
	@Override
	public void setStatementTest() throws Exception {
		javaBaseStatementTest();
		resourceStatementTest();
		enumStatementTest();
		simpleStatmentTest();
	}

	@Override
	public void getResultSetTest() throws Exception {
		javaBaseResultTest();
		resourceResultTest();
		enumResultTest();
		simpleResultTest();
	}
	
	public void javaBaseStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroGroups");
		
		String expected = "\n\t\t"
				+ "state.setString(1, toString(getHeroGroups()));";
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void javaBaseResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroGroups");
		String expected =	"\n\t\t"
				+ "{"	+ "\n\t\t\t"
				+ "String value_ = result.getString(\"heroGroups\");"	+ "\n\t\t\t"
				+ "org.itas.util.Pair[] valueArray_ = parsePair(value_);"	+ "\n\t\t\t"
				+ "java.util.Map valueList_ = new java.util.HashMap(8);"	+ "\n\t\t\t"
				+ "org.itas.util.Pair pair;"	+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"	+ "\n\t\t\t\t"
				+ "pair = valueArray_[i];"	+ "\n\t\t\t\t"
				+ "valueList_.put(java.lang.Integer.valueOf((String)pair.getKey()), new org.itas.core.Simple((String)pair.getValue()));"	+ "\n\t\t\t"
				+ "}"	+ "\n\t\t\t"
				+ "setHeroGroups(valueList_);"	+ "\n\t\t"
				+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	public void resourceStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroResMap");
		
		String expected = "\n\t\t"
				+ "state.setString(1, toString(getHeroResMap()));";
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void resourceResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("heroResMap");

		String expected = "\n\t\t"
				+ "{"	+ "\n\t\t\t"
				+ "String value_ = result.getString(\"heroResMap\");"	+ "\n\t\t\t"
				+ "org.itas.util.Pair[] valueArray_ = parsePair(value_);"	+ "\n\t\t\t"
				+ "java.util.Map valueList_ = new java.util.HashMap(16);"	+ "\n\t\t\t"
				+ "org.itas.util.Pair pair;"	+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"	+ "\n\t\t\t\t"
				+ "pair = valueArray_[i];"	+ "\n\t\t\t\t"
				+ "valueList_.put(org.itas.core.Pool.getResource((String)pair.getKey()), java.lang.Integer.valueOf((String)pair.getValue()));"	+ "\n\t\t\t"
				+ "}"	+ "\n\t\t\t"
				+ "setHeroResMap(valueList_);"	+ "\n\t\t"
				+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumStatementTest() throws Exception {
		CtField field = clazz.getDeclaredField("sexCoinMap");
		
		String expected = "\n\t\t"
				+ "state.setString(1, toString(getSexCoinMap()));";
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void enumResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("sexCoinMap");
		
		String expected = 	"\n\t\t"
				+ "{"	+ "\n\t\t\t"
				+ "String value_ = result.getString(\"sexCoinMap\");"	+ "\n\t\t\t"
				+ "org.itas.util.Pair[] valueArray_ = parsePair(value_);"	+ "\n\t\t\t"
				+ "java.util.Map valueList_ = new java.util.HashMap(8);"	+ "\n\t\t\t"
				+ "org.itas.util.Pair pair;"	+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"	+ "\n\t\t\t\t"
				+ "pair = valueArray_[i];"	+ "\n\t\t\t\t"
				+ "valueList_.put(parse(org.itas.core.bytecode.Model.SexType.class, java.lang.Byte.valueOf((String)pair.getKey())), java.lang.Float.valueOf((String)pair.getValue()));"	+ "\n\t\t\t"
				+ "}"	+ "\n\t\t\t"
				+ "setSexCoinMap(valueList_);"	+ "\n\t\t"
				+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}
	
	public void simpleStatmentTest() throws Exception {
		CtField field = clazz.getDeclaredField("cardGroupNames");
		
		String expected = 
				"\n\t\t"
						+ "state.setString(1, toString(getCardGroupNames()));";
		String content = provider.setStatement(1, field);
		Assert.assertEquals(expected, content);
	}
	
	public void simpleResultTest() throws Exception {
		CtField field = clazz.getDeclaredField("cardGroupNames");
		
		String expected = 	"\n\t\t"
				+ "{"	+ "\n\t\t\t"
				+ "String value_ = result.getString(\"cardGroupNames\");"	+ "\n\t\t\t"
				+ "org.itas.util.Pair[] valueArray_ = parsePair(value_);"	+ "\n\t\t\t"
				+ "java.util.Map valueList_ = new java.util.LinkedHashMap(8);"	+ "\n\t\t\t"
				+ "org.itas.util.Pair pair;"	+ "\n\t\t\t"
				+ "for (int i = 0; i < valueArray_.length; i ++) {"	+ "\n\t\t\t\t"
				+ "pair = valueArray_[i];"	+ "\n\t\t\t\t"
				+ "valueList_.put(new org.itas.core.Simple((String)pair.getKey()), (String)pair.getValue());"	+ "\n\t\t\t"
				+ "}"	+ "\n\t\t\t"
				+ "setCardGroupNames(valueList_);"	+ "\n\t\t"
				+ "}";

		String content = provider.getResultSet(field);
		Assert.assertEquals(expected, content);
	}

	
}
