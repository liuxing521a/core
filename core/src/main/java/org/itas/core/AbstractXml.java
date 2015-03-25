package org.itas.core;

import static org.itas.core.bytecode.Type.booleanType;
import static org.itas.core.bytecode.Type.byteType;
import static org.itas.core.bytecode.Type.charType;
import static org.itas.core.bytecode.Type.doubleType;
import static org.itas.core.bytecode.Type.enumByteType;
import static org.itas.core.bytecode.Type.enumIntType;
import static org.itas.core.bytecode.Type.enumStringType;
import static org.itas.core.bytecode.Type.floatType;
import static org.itas.core.bytecode.Type.intType;
import static org.itas.core.bytecode.Type.listType;
import static org.itas.core.bytecode.Type.longType;
import static org.itas.core.bytecode.Type.mapType;
import static org.itas.core.bytecode.Type.resourceType;
import static org.itas.core.bytecode.Type.setType;
import static org.itas.core.bytecode.Type.shortType;
import static org.itas.core.bytecode.Type.stringType;
import static org.itas.core.bytecode.Type.timeStampType;

import java.lang.reflect.Field;

import org.itas.core.util.XmlContainers;
import org.itas.util.ItasException;

abstract class AbstractXml implements XmlContainers {
	
  void fill(Field field, String text) throws Exception {
	if (!field.isAccessible()) {
	  field.setAccessible(true);
	}
	
	if (booleanType.isType(field.getType())) {
	  field.setBoolean(this, parseBoolean(text));
	} else if (byteType.isType(field.getType())) {
	  field.setByte(this, parseByte(text));
	} else if (charType.isType(field.getType())) {
	  field.setChar(this, parseChar(text));
	} else if (shortType.isType(field.getType())) {
	  field.setShort(this, parseShort(text));
	} else if (intType.isType(field.getType())) {
	  field.setInt(this, parseInt(text));
	} else if (longType.isType(field.getType())) {
	  field.setLong(this, parseLong(text));
	} else if (floatType.isType(field.getType())) {
	  field.setFloat(this, parseFloat(text));
	} else if (doubleType.isType(field.getType())) {
	  field.setDouble(this, parseDouble(text));
	} else if (stringType.isType(field.getType())) {
	  field.set(this, text);
	} else if (resourceType.isType(field.getType())) {
	  field.set(this, Pool.getResource(text));
	} else if (enumByteType.isType(field.getType())) {
	  field.set(this, parse(field.getType(), parseByte(text)));
	} else if (enumIntType.isType(field.getType())) {
	  field.set(this, parse(field.getType(), parseInt(text)));
	} else if (enumStringType.isType(field.getType())) {
      field.set(this, parse(field.getType(), text));
	} else if (setType.isType(field.getType())) {
	  field.set(this, parseSet(field, text));
	} else if (listType.isType(field.getType())) {
	  field.set(this, parseList(field, text));
	} else if (mapType.isType(field.getType())) {
	  field.set(this, parseMap(field, text));
	} else if (timeStampType.isType(field.getType())) {
	  field.set(this, parseMap(field, text));
	} else {
	  throw new ItasException("unSupported:[type:" + field.getType() + "]");
	}
  }
  
}
