package org.itas.core.bytecode;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

public class EnumIntProviderTest extends AbstreactFieldProvider {

	@Before
	public void setUP() throws NotFoundException {
		super.setUP();
		provider = EnumIntProvider.PROVIDER;
		field = clazz.getDeclaredField("effect");
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
		Assert.assertEquals(false, provider.isType(Enum.class));
		Assert.assertEquals(false, provider.isType(EnumByte.class));
		Assert.assertEquals(true, provider.isType(EnumInt.class));
		Assert.assertEquals(false, provider.isType(EnumString.class));
		Assert.assertEquals(false, provider.isType(Resource.class));
		Assert.assertEquals(false, provider.isType(ArrayList.class));
		Assert.assertEquals(false, provider.isType(HashSet.class));
		Assert.assertEquals(false, provider.isType(HashMap.class));
		Assert.assertEquals(false, provider.isType(Timestamp.class));
	}
	
	@Override
	public void setStatementTest() throws Exception {
		String expected =  "\n\t\t"
				+ "{" + "\n\t\t\t" 
				+ "int value_ = 0;"	+ "\n\t\t\t"
				+ "if (getEffect() != null) {"  + "\n\t\t\t\t" 
				+ "value_ = getEffect().key();"  + "\n\t\t\t" 
				+ "}" + "\n\t\t\t" 
				+ "state.setInt(1, value_);"  + "\n\t\t" 
				+ "}";
			
			String actual = provider.setStatement(1, field);
			Assert.assertEquals(expected, actual);
	}

	@Override
	public void getResultSetTest() throws Exception {
		String expected = 
				"\n\t\t"
				+ "setEffect(parse(org.itas.core.bytecode.Model.Effect.class, result.getInt(\"effect\")));";
		
		String actual = provider.getResultSet(field);
		Assert.assertEquals(expected, actual);
	}
	
}
