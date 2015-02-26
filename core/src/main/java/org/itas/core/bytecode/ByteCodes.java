package org.itas.core.bytecode;

import static org.itas.core.util.Utils.ByteCodeUtils.getTableName;
import static org.itas.core.util.Utils.ByteCodeUtils.name;
import static org.itas.core.util.Utils.ByteCodeUtils.uname;
import static org.itas.core.util.Utils.CtClassUtils.getAllField;
import static org.itas.core.util.Utils.CtClassUtils.isBasicType;
import static org.itas.core.util.Utils.CtClassUtils.isBasicWarpType;
import static org.itas.core.util.Utils.CtClassUtils.isExtends;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import net.itas.core.annotation.Clazz;
import net.itas.core.annotation.UnSave;

import org.itas.core.GameBaseAotuID;
import org.itas.core.GameObject;
import org.itas.core.Simple;
import org.itas.core.enums.EByte;
import org.itas.core.enums.EString;
import org.itas.core.resource.Resource;
import org.itas.util.GenericInfo;
import org.itas.util.ItasException;
import org.itas.util.Utils.GenericUtils;
import org.itas.util.Utils.Objects;

/**
 * 字节码操作
 * @author liuzhen
 */
public final class ByteCodes {
	
	public Class<?> generate(CtClass clazz) throws Exception {
		ClassPool pool = ClassPool.getDefault();
		
		StringBuffer insertBuffer = new StringBuffer();
		insertBuffer.append(nextRow(1));
		insertBuffer.append("protected void doInsert(java.sql.PreparedStatement state) throws java.sql.SQLException {");
		
		StringBuffer updateBuffer = new StringBuffer();
		updateBuffer.append(nextRow(1));
		updateBuffer.append("protected void doUpdate(java.sql.PreparedStatement state) throws java.sql.SQLException {");

		StringBuffer deleteBuffer = new StringBuffer();
		deleteBuffer.append(nextRow(1));
		deleteBuffer.append("protected void doDelete(java.sql.PreparedStatement state) throws java.sql.SQLException {");

		StringBuffer fillBuffer = new StringBuffer();
		fillBuffer.append(nextRow(1));
		fillBuffer.append("protected void doFillData(java.sql.ResultSet result) throws java.sql.SQLException {");
		
		StringBuffer selectSQLBuffer = new StringBuffer();
		selectSQLBuffer.append(nextRow(1));
		selectSQLBuffer.append("private static final String select_SQL = \"SELECT ");
		
		StringBuffer insertSQLBuffer = new StringBuffer();
		insertSQLBuffer.append(nextRow(1));
		insertSQLBuffer.append("private static final String insert_SQL = \"INSERT INTO ");
		
		StringBuffer updateSQLBuffer = new StringBuffer();
		updateSQLBuffer.append(nextRow(1));
		updateSQLBuffer.append("private static final String update_SQL = \"UPDATE ");

		StringBuffer deleteSQLBuffer = new StringBuffer();
		deleteSQLBuffer.append(nextRow(1));
		deleteSQLBuffer.append("private static final String delete_SQL = \"DELETE FROM ");
		
		List<String> columns = new ArrayList<String>();
		
		CtField IdField = null;
		int insertIndex = 1, updateIndex = 1;

		List<CtField> fields = getAllField(clazz);
		for (CtField field : fields) {
			if (field.hasAnnotation(UnSave.class) || 
				Modifier.isStatic(field.getModifiers()) || 
				Modifier.isFinal(field.getModifiers())) {
				continue;
			} 
			
			columns.add(String.join(field.getName(), "`", "`"));
			if (isBasicType(field, byte.class)) {
//				insertBuffer.append(setByte(insertIndex ++, field));
//				updateBuffer.append(setByte(updateIndex ++, field));
//				fillBuffer.append(getByte(field));
			} else if (isBasicType(field, boolean.class)) {
//				insertBuffer.append(setBoolean(insertIndex ++, field));
//				updateBuffer.append(setBoolean(updateIndex ++, field));
//				fillBuffer.append(getBoolean(field));
			} else if (isBasicType(field, char.class)) {
//				insertBuffer.append(setChar(insertIndex ++, field));
//				updateBuffer.append(setChar(updateIndex ++, field));
//				fillBuffer.append(getChar(field));
			} else if (isBasicType(field, short.class)) {
//				insertBuffer.append(setShort(insertIndex ++, field));
//				updateBuffer.append(setShort(updateIndex ++, field));
//				fillBuffer.append(getShort(field));
			} else if (isBasicType(field, int.class)) {
//				insertBuffer.append(setInt(insertIndex ++, field));
//				updateBuffer.append(setInt(updateIndex ++, field));
//				fillBuffer.append(getInt(field));
			} else if (isBasicType(field, long.class)) {
//				insertBuffer.append(setLong(insertIndex ++, field));
//				updateBuffer.append(setLong(updateIndex ++, field));
//				fillBuffer.append(getLong(field));
			} else if (isBasicType(field, float.class)) {
//				insertBuffer.append(setFloat(insertIndex ++, field));
//				updateBuffer.append(setFloat(updateIndex ++, field));
//				fillBuffer.append(getFloat(field));
			} else if (isBasicType(field, double.class)) {
//				insertBuffer.append(setDouble(insertIndex ++, field));
//				updateBuffer.append(setDouble(updateIndex ++, field));
//				fillBuffer.append(getDouble(field));
			} else if (isExtends(field, String.class)) {
//				insertBuffer.append(setString(insertIndex ++, field));
//				if (field.hasAnnotation(PrimaryfKey.class)) {
//					IdField = field;
//					deleteBuffer.append(setString(1, field));
//				} else {
//					updateBuffer.append(setString(updateIndex ++, field));
//					fillBuffer.append(getString(field));
//				}
			} else if (isExtends(field, Timestamp.class)) {
//				insertBuffer.append(setTimestamp(insertIndex ++, field));
//				updateBuffer.append(setTimestamp(updateIndex ++, field));
//				fillBuffer.append(getTimestamp(field));
			} else if (isExtends(field, Resource.class)) {
//				insertBuffer.append(setSource(insertIndex ++, field));
//				updateBuffer.append(setSource(updateIndex ++, field));
//				fillBuffer.append(getSource(field));
			} else if (isExtends(field, EByte.class)) {
				insertBuffer.append(setEnumByte(insertIndex ++, field));
				updateBuffer.append(setEnumByte(updateIndex ++, field));
				fillBuffer.append(getEnumByte(field));
			} else if (isExtends(field, EString.class)) {
				insertBuffer.append(setEnumString(insertIndex ++, field));
				updateBuffer.append(setEnumString(updateIndex ++, field));
				fillBuffer.append(getEnumString(field));
			} else if (isExtends(field, Simple.class)) {
//				insertBuffer.append(setSimple(insertIndex ++, field, pool));
//				updateBuffer.append(setSimple(updateIndex ++, field, pool));
//				fillBuffer.append(getSimple(field, pool));
			} else if (isExtends(field, Collection.class)) {
//				insertBuffer.append(setCollection(insertIndex ++, field));
//				updateBuffer.append(setCollection(updateIndex ++, field));
//				fillBuffer.append(getCollection(field, pool));
			} else if (isExtends(field, Map.class)) {
				insertBuffer.append(setMap(insertIndex ++, field));
				updateBuffer.append(setMap(updateIndex ++, field));
				fillBuffer.append(getMap(field));
			} else if (field.getType().isArray()) {
				CtClass componentType = field.getType().getComponentType();
				if (isBasicType(componentType, byte.class)) {
					
				} else if (isBasicType(componentType, boolean.class)) {
					
				} else if (isBasicType(componentType, char.class)) {
					
				} else if (isBasicType(componentType, short.class)) {
					
				} else if (isBasicType(componentType, int.class)) {
					
				} else if (isBasicType(componentType, long.class)) {
					
				} else if (isBasicType(componentType, float.class)) {
					
				} else if (isBasicType(componentType, double.class)) {
					
				} else if (isExtends(componentType, String.class)) {
					
				} else if (isExtends(componentType, GameBaseAotuID.class)) {
					
				} else {
					throw new ItasException("bytecode unsupported class:" + clazz.getName() + "[" + field.getName() + ", " + field.getType().getName() + "]");
				}
			} else {
				if (field.getType().isEnum()) {
					throw new ItasException("bytecode Enum auto complete must implements EnumByte|EnumString interface");
				} else {
					throw new ItasException("bytecode unsupported class:" + clazz.getName() + "[" + field.getName() + ", " + field.getType().getName() + "]");
				}
			}
		}
		insertBuffer.append("\n\t}");
		updateBuffer.append(setId(updateIndex ++, IdField));
		updateBuffer.append("\n\t}");
		deleteBuffer.append("\n\t}");
		fillBuffer.append("\n\t}");
		
		addFieldSelectSQL(columns, selectSQLBuffer, clazz);

		addFieldInsertSQL(columns, insertSQLBuffer, clazz);
		
		addFieldUpdateSQL(columns, updateSQLBuffer, clazz);

		addFieldDeleteSQL(deleteSQLBuffer, clazz);
		
		CtMethod method = CtMethod.make(insertBuffer.toString(), clazz);
		clazz.addMethod(method);
		
		method = CtMethod.make(updateBuffer.toString(), clazz);
		clazz.addMethod(method);
		
		method = CtMethod.make(deleteBuffer.toString(), clazz);
		clazz.addMethod(method);
		
		method = CtMethod.make(fillBuffer.toString(), clazz);
		clazz.addMethod(method);
		
		addMethodGetSelectSQL(clazz);

		addMethodGetInsertSQL(clazz);

		addMethodGetUpdateSQL(clazz);

		addMethodGetDeleteSQL(clazz);

		addMethodClone(clazz);
		
		addMethodGetTableName(clazz);
		
//		clazz.writeFile("D:/");
		return clazz.toClass();
	}
	
	private String nextRow(int tableNum) {
		StringBuffer buf = new StringBuffer('\n');
		
		for (int i = 0; i < tableNum; i++) {
			buf.append('\t');
		}
		
		return buf.toString();
	}
	
	
	private String getEnumByte(CtField field) throws NotFoundException {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(nextRow(2));
		buffer.append(String.format("set%s((%s)net.itas.core.util.Utils.EnumUtils.parse(%s.class, result.getByte(\"%s\")));", 
				uname(field), field.getType().getName(), field.getType().getName(), name(field)));
		
		return buffer.toString();
	}
	
	private String setEnumByte(int index, CtField field) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(nextRow(2));
		buffer.append("{");
		buffer.append(nextRow(3));
		buffer.append("byte eid = 0;");
		buffer.append(nextRow(3));
		buffer.append(String.format("if (get%s() != null) {", uname(field)));
		buffer.append(nextRow(4));
		buffer.append(String.format("eid =  get%s().key();", uname(field)));
		buffer.append(nextRow(3));
		buffer.append("}");
		buffer.append(nextRow(3));
		buffer.append(String.format("state.setByte(%s, eid);", index));
		buffer.append(nextRow(2));
		buffer.append("}");
		
		return buffer.toString();
	}
	
	private String getEnumString(CtField field) throws NotFoundException {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(String.format("set%s((%s)net.itas.core.util.Utils.EnumUtils.parse(%s.class, result.getString(\"%s\")));", 
				uname(field), field.getType().getName(), field.getType().getName(), name(field)));
		
		return buffer.toString();
	}
	
	private String setEnumString(int index, CtField field) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(nextRow(2));
		buffer.append("{");
		buffer.append(nextRow(3));
		buffer.append("String eid = null;");
		buffer.append(nextRow(3));
		buffer.append(String.format("if (get%s() != null) {", uname(field)));
		buffer.append(nextRow(4));
		buffer.append(String.format("eid =  get%s().getId();", uname(field)));
		buffer.append(nextRow(3));
		buffer.append("}");
		buffer.append(nextRow(3));
		buffer.append(String.format("state.setString(%s, eid);", index));
		buffer.append(nextRow(2));
		buffer.append("}");
		
		return buffer.toString();
	}
	

	
	private String getMap(CtField field) throws Exception {
		GenericInfo info = GenericUtils.getGenericInfo(field.getGenericSignature(), true);
		CtClass keyGenericType = ClassPool.getDefault().get(info.getChilds().get(0).getName());
		CtClass valueGenericType = ClassPool.getDefault().get(info.getChilds().get(1).getName());
		String childChildGenericKey = null;
		if (!info.getChilds().get(0).getChilds().isEmpty()) {
			childChildGenericKey = info.getChilds().get(0).getChilds().get(0).getName();
		}
		
		String childChildGenericValue = null;
		if (!info.getChilds().get(1).getChilds().isEmpty()) {
			childChildGenericValue = info.getChilds().get(1).getChilds().get(0).getName();
		}
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(nextRow(2));
		buffer.append("{");
		buffer.append(nextRow(3));
		buffer.append(String.format("String datas = result.getString(\"%s\");", name(field)));
		buffer.append(nextRow(3));
		buffer.append("java.util.Map dataMaps = parserMap(datas);");
		buffer.append(nextRow(3));
		buffer.append(String.format("%s dataArray = %s", info.getName(), newInstanceMap(field)));
		buffer.append(nextRow(3));
		buffer.append("java.util.Iterator it = dataMaps.entrySet().iterator();");
		buffer.append(nextRow(3));
		buffer.append("java.util.Map.Entry entry;");
		buffer.append(nextRow(3));
		buffer.append("while (it.hasNext()) {");
		buffer.append(nextRow(4));
		buffer.append("entry = (java.util.Map.Entry)it.next();");
		buffer.append(nextRow(4));
		buffer.append(String.format("dataArray.put(%s, %s);",
				getGenericStr(keyGenericType, childChildGenericKey, "entry.getKey()"),
				getGenericStr(valueGenericType, childChildGenericValue, "entry.getValue()")));
		buffer.append(nextRow(3));
		buffer.append("}");
		buffer.append(nextRow(3));
		buffer.append(String.format("set%s(dataArray);", uname(field)));
		buffer.append(nextRow(2));
		buffer.append("}");
		
		return buffer.toString();
	}

	private String setMap(int index, CtField field) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(nextRow(2));
		buffer.append(String.format("state.setString(%s, getMapValue(get%s()));", index, uname(field)));
		
		return buffer.toString();
	}
	
	private String newInstanceMap(CtField field) throws Exception {
		Object annotiation = field.getAnnotation(Clazz.class);
		if (Objects.nonNull(annotiation)) {
			Clazz clazz = (Clazz)annotiation;
			return String.format("new %s();", clazz.value().getName());
		}

		if (isExtends(field, Map.class)) {
			return "new java.util.HashMap(8);";
		}
		
		throw new ItasException("unsupported type:" + field.getType());
	}
	
	private String setId(int index, CtField field) throws Exception {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(nextRow(2));
		if (isBasicType(field, int.class) || isBasicWarpType(field, int.class)) {
			buffer.append(String.format("state.setInt(%s, get%s());", index, uname(field)));
		} else if (isBasicType(field, long.class) || isBasicWarpType(field, long.class)) {
			buffer.append(String.format("state.setLong(%s, get%s());", index, uname(field)));
		} else if (isExtends(field, String.class)) {
			buffer.append(String.format("state.setString(%s, get%s());", index, uname(field)));
		} else {
			throw new ItasException("unsupported field type:" + field.getType());
		}
		
		return buffer.toString();
	}
	
	private String getGenericStr(CtClass genericType, String childGeneric, String dataName) throws Exception {
		if (isBasicWarpType(genericType, byte.class)) {
			return String.format("java.lang.Byte.valueOf((java.lang.String)%s)", dataName);
		} else if (isBasicWarpType(genericType, short.class)) {
			return String.format("java.lang.Short.valueOf((java.lang.String)%s)", dataName);
		} else if (isBasicWarpType(genericType, int.class)) {
			return String.format("java.lang.Integer.valueOf((java.lang.String)%s)", dataName);
		} else if (isBasicWarpType(genericType, long.class)) {
			return String.format("java.lang.Long.valueOf((java.lang.String)%s)", dataName);
		} else if (isBasicWarpType(genericType, float.class)) {
			return String.format("java.lang.Float.valueOf((java.lang.String)%s)", dataName);
		} else if (isBasicWarpType(genericType, double.class)) {
			return String.format("java.lang.Double.valueOf((java.lang.String)%s)", dataName);
		} else if (isBasicWarpType(genericType, char.class)) {
			return String.format("%s.charAt(0)", dataName);
		} else if (isBasicWarpType(genericType, boolean.class)) {
			return String.format("java.lang.Boolean.valueOf((java.lang.String)%s)", dataName);
		} else if (isExtends(genericType, Simple.class)) {
			return String.format("net.itas.core.Simple.parse(%s.class, (java.lang.String)%s)", childGeneric, dataName);
		} else if (isExtends(genericType, Resource.class)) {
			return String.format("(%s)net.itas.core.Pool.getResource((java.lang.String)%s)", genericType.getName(), dataName);
		} else if (isExtends(genericType, EString.class)) {
			return String.format("net.itas.core.util.Utils.EnumUtils.parse(%s.class, (java.lang.String)%s)", genericType.getName(), dataName);
		} else if (isExtends(genericType, EByte.class)) {
			return String.format("net.itas.core.util.Utils.EnumUtils.parse(%s.class, java.lang.Byte.parseByte((java.lang.String)%s))", genericType.getName(), dataName);
		} else if (isExtends(genericType, String.class)) {
			return String.format("(java.lang.String)%s", dataName);
		} else {
			throw new ItasException("unsuppoted type:" + genericType.getName());
		}
	}

	private void addMethodGetSelectSQL(CtClass clazz) throws Exception {
		StringBuffer selectBuf = new StringBuffer();
		
//		selectBuf.append(nextRow(1));
//		selectBuf.append("@Override");
		selectBuf.append(nextRow(1));
		selectBuf.append("protected String getSelectSQL(){");
		selectBuf.append(nextRow(2));
		selectBuf.append("return this.select_SQL;");
		selectBuf.append(nextRow(1));
		selectBuf.append("}");
		CtMethod method = CtMethod.make(selectBuf.toString(), clazz);
		
		clazz.addMethod(method);
	}
	
	private void addMethodGetInsertSQL(CtClass clazz) throws Exception {
		StringBuffer insertBuf = new StringBuffer();
		
//		insertBuf.append(nextRow(1));
//		insertBuf.append("@Override");
		insertBuf.append(nextRow(1));
		insertBuf.append("protected String getInsertSQL(){");
		insertBuf.append(nextRow(2));
		insertBuf.append("return this.insert_SQL;");
		insertBuf.append(nextRow(1));
		insertBuf.append("}");
		CtMethod method = CtMethod.make(insertBuf.toString(), clazz);
		
		clazz.addMethod(method);
	}

	private void addMethodGetUpdateSQL(CtClass clazz) throws Exception {
		StringBuffer updateBuf = new StringBuffer();

//		updateBuf.append(nextRow(1));
//		updateBuf.append("@Override");
		updateBuf.append(nextRow(1));
		updateBuf.append("protected String getUpdateSQL(){");
		updateBuf.append(nextRow(2));
		updateBuf.append("return this.update_SQL;");
		updateBuf.append(nextRow(1));
		updateBuf.append("}");
		
		CtMethod method = CtMethod.make(updateBuf.toString(), clazz);
		clazz.addMethod(method);
	}

	private void addMethodGetDeleteSQL(CtClass clazz) throws Exception  {
		StringBuffer deleteBuf = new StringBuffer();
		
//		deleteBuf.append(nextRow(1));
//		deleteBuf.append("@Override");
		deleteBuf.append(nextRow(1));
		deleteBuf.append("protected String getDeleteSQL(){");
		deleteBuf.append(nextRow(2));
		deleteBuf.append("return this.delete_SQL;");
		deleteBuf.append(nextRow(1));
		deleteBuf.append("}");
		
		CtMethod method = CtMethod.make(deleteBuf.toString(), clazz);
		clazz.addMethod(method);
	}

	private void addMethodClone(CtClass clazz) throws Exception {
		StringBuffer colneBuf = new StringBuffer();

//		colneBuf.append(nextRow(1));
//		colneBuf.append("@Override");
		if (isExtends(clazz, GameObject.class)) {
			colneBuf.append(nextRow(1));
			colneBuf.append("protected net.itas.core.Game clone(java.lang.Object Id) {");
			colneBuf.append(nextRow(2));
			colneBuf.append("int oid =((java.lang.Integer)Id).intValue();");
			colneBuf.append(nextRow(2));
			colneBuf.append(String.format("return new %s(oid);", clazz.getName()));
			colneBuf.append(nextRow(1));
			colneBuf.append("}");
		} else if (isExtends(clazz, GameBaseAotuID.class)) {
			colneBuf.append(nextRow(1));
			colneBuf.append("protected net.itas.core.Game clone(java.lang.Object Id) {");
			colneBuf.append(nextRow(2));
			colneBuf.append("String oid =(java.lang.String)Id;");
			colneBuf.append(nextRow(2));
			colneBuf.append(String.format("return new %s(oid);", clazz.getName()));
			colneBuf.append(nextRow(1));
			colneBuf.append("}");
		} else {
			throw new ItasException("unkown class type:" + clazz.getName());
		}
		
		CtMethod method = CtMethod.make(colneBuf.toString(), clazz);
		clazz.addMethod(method);
	}
	
	private void addMethodGetTableName(CtClass clazz) throws Exception {
		StringBuffer tableNameBuf = new StringBuffer();

//		tableNameBuf.append(nextRow(1));
//		tableNameBuf.append("@Override");
		tableNameBuf.append("protected String getTableName() {");
		tableNameBuf.append(nextRow(2));
		tableNameBuf.append(String.format("return \"%s\";", getTableName(clazz)));
		tableNameBuf.append(nextRow(2));
		tableNameBuf.append("}");
		
		CtMethod method = CtMethod.make(tableNameBuf.toString(), clazz);
		clazz.addMethod(method);
	}
	
	private void addFieldSelectSQL(List<String> columns, StringBuffer buf, CtClass clazz) throws Exception {
		buf.append(String.join(",", columns));
		buf.append(String.join("", " FROM `", getTableName(clazz), "` WHERE Id = ?;\";"));
		
		CtField field = CtField.make(buf.toString(), clazz);
		clazz.addField(field);
	}
	
	private void addFieldInsertSQL(List<String> columns, StringBuffer buf, CtClass clazz) throws Exception {
		buf.append(String.join("", "`", getTableName(clazz), "` ("));
		buf.append(String.join(",", columns));
		buf.append(") VALUES (");
		for (int i = 0; i < columns.size(); i ++) {
			buf.append("?").append(",");
		}
		
		buf.deleteCharAt(buf.length() - 1);
		buf.append(");\";");
		
		CtField field = CtField.make(buf.toString(), clazz);  
		clazz.addField(field);
	}
	
	private void addFieldUpdateSQL(List<String> columns, StringBuffer buf, CtClass clazz) throws Exception {
		buf.append(String.join("", "`", getTableName(clazz), "` SET "));
		for (String s : columns) {
			if(!s.equals("`Id`")){buf.append(s).append("=?,");}
		}
		
		buf.deleteCharAt(buf.length() - 1);
		buf.append(" WHERE `Id` = ?;\";");
		
		CtField field = CtField.make(buf.toString(), clazz);
		clazz.addField(field);
	}
	
	private void addFieldDeleteSQL(StringBuffer buf, CtClass clazz) throws Exception {
		buf.append(String.join("", "`", getTableName(clazz), "` WHERE `Id` = ?;\";"));
		
		CtField field = CtField.make(buf.toString(), clazz);
		clazz.addField(field);
	}
}
