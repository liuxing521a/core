package org.itas.core.bytecode;

import static org.itas.core.util.Utils.ByteCodeUtils.getTableName;
import static org.itas.core.util.Utils.ByteCodeUtils.uname;
import static org.itas.core.util.Utils.CtClassUtils.getAllField;

import java.util.ArrayList;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import net.itas.core.annotation.UnSave;

import org.itas.core.util.Type;
import org.itas.util.ItasException;

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

		CtClass filedClassType;
		List<CtField> fields = getAllField(clazz);
		for (CtField field : fields) {
			if (field.hasAnnotation(UnSave.class) || 
				Modifier.isStatic(field.getModifiers()) || 
				Modifier.isFinal(field.getModifiers())) {
				continue;
			} 
			
			filedClassType = field.getType();

			columns.add(String.join(field.getName(), "`", "`"));
			if (Type.byteType.is(filedClassType)) {
//				insertBuffer.append(setByte(insertIndex ++, field));
//				updateBuffer.append(setByte(updateIndex ++, field));
//				fillBuffer.append(getByte(field));
			} else if (Type.booleanType.is(filedClassType)) {
//				insertBuffer.append(setBoolean(insertIndex ++, field));
//				updateBuffer.append(setBoolean(updateIndex ++, field));
//				fillBuffer.append(getBoolean(field));
			} else if (Type.charType.is(filedClassType)) {
//				insertBuffer.append(setChar(insertIndex ++, field));
//				updateBuffer.append(setChar(updateIndex ++, field));
//				fillBuffer.append(getChar(field));
			} else if (Type.shortType.is(filedClassType)) {
//				insertBuffer.append(setShort(insertIndex ++, field));
//				updateBuffer.append(setShort(updateIndex ++, field));
//				fillBuffer.append(getShort(field));
			} else if (Type.intType.is(filedClassType)) {
//				insertBuffer.append(setInt(insertIndex ++, field));
//				updateBuffer.append(setInt(updateIndex ++, field));
//				fillBuffer.append(getInt(field));
			} else if (Type.longType.is(filedClassType)) {
//				insertBuffer.append(setLong(insertIndex ++, field));
//				updateBuffer.append(setLong(updateIndex ++, field));
//				fillBuffer.append(getLong(field));
			} else if (Type.floatType.is(filedClassType)) {
//				insertBuffer.append(setFloat(insertIndex ++, field));
//				updateBuffer.append(setFloat(updateIndex ++, field));
//				fillBuffer.append(getFloat(field));
			} else if (Type.doubleType.is(filedClassType)) {
//				insertBuffer.append(setDouble(insertIndex ++, field));
//				updateBuffer.append(setDouble(updateIndex ++, field));
//				fillBuffer.append(getDouble(field));
			} else if (Type.stringType.is(filedClassType)) {
//				insertBuffer.append(setString(insertIndex ++, field));
//				if (field.hasAnnotation(PrimaryfKey.class)) {
//					IdField = field;
//					deleteBuffer.append(setString(1, field));
//				} else {
//					updateBuffer.append(setString(updateIndex ++, field));
//					fillBuffer.append(getString(field));
//				}
			} else if (Type.timeStampType.is(filedClassType)) {
//				insertBuffer.append(setTimestamp(insertIndex ++, field));
//				updateBuffer.append(setTimestamp(updateIndex ++, field));
//				fillBuffer.append(getTimestamp(field));
			} else if (Type.resourceType.is(filedClassType)) {
//				insertBuffer.append(setSource(insertIndex ++, field));
//				updateBuffer.append(setSource(updateIndex ++, field));
//				fillBuffer.append(getSource(field));
			} else if (Type.enumByteType.is(filedClassType)) {
//				insertBuffer.append(setEnumByte(insertIndex ++, field));
//				updateBuffer.append(setEnumByte(updateIndex ++, field));
//				fillBuffer.append(getEnumByte(field));
			} else if (Type.enumStringType.is(filedClassType)) {
//				insertBuffer.append(setEnumString(insertIndex ++, field));
//				updateBuffer.append(setEnumString(updateIndex ++, field));
//				fillBuffer.append(getEnumString(field));
			} else if (Type.simpleType.is(filedClassType)) {
//				insertBuffer.append(setSimple(insertIndex ++, field, pool));
//				updateBuffer.append(setSimple(updateIndex ++, field, pool));
//				fillBuffer.append(getSimple(field, pool));
			} else if (Type.listType.is(filedClassType)) {
//				insertBuffer.append(setCollection(insertIndex ++, field));
//				updateBuffer.append(setCollection(updateIndex ++, field));
//				fillBuffer.append(getCollection(field, pool));
			} else if (Type.mapType.is(filedClassType)) {
//				insertBuffer.append(setMap(insertIndex ++, field));
//				updateBuffer.append(setMap(updateIndex ++, field));
//				fillBuffer.append(getMap(field));
			} else if (field.getType().isArray()) {
//				CtClass componentType = field.getType().getComponentType();
//				if (isBasicType(componentType, byte.class)) {
//					
//				} else if (isBasicType(componentType, boolean.class)) {
//					
//				} else if (isBasicType(componentType, char.class)) {
//					
//				} else if (isBasicType(componentType, short.class)) {
//					
//				} else if (isBasicType(componentType, int.class)) {
//					
//				} else if (isBasicType(componentType, long.class)) {
//					
//				} else if (isBasicType(componentType, float.class)) {
//					
//				} else if (isBasicType(componentType, double.class)) {
//					
//				} else if (isExtends(componentType, String.class)) {
//					
//				} else if (isExtends(componentType, GameBaseAotuID.class)) {
//					
//				} else {
//					throw new ItasException("bytecode unsupported class:" + clazz.getName() + "[" + field.getName() + ", " + field.getType().getName() + "]");
//				}
			} else {
				if (field.getType().isEnum()) {
					throw new ItasException("bytecode Enum auto complete must implements EnumByte|EnumInt|EnumString interface");
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

	
	private String setId(int index, CtField field) throws Exception {
		if (Type.stringType.is(field.getType())) {
			return String.format("\n\t\tstate.setString(%s, get%s());", index, uname(field));
		}
		
		throw new ItasException("unsupported field type:" + field.getType());
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
		if (Type.gameObjectAutoIdType.is(clazz)) {
			StringBuffer colneBuf = new StringBuffer();
			
			colneBuf.append(nextRow(1));
			colneBuf.append("protected net.itas.core.Game clone(java.lang.Object Id) {");
			colneBuf.append(nextRow(2));
			colneBuf.append("int oid =((java.lang.Integer)Id).intValue();");
			colneBuf.append(nextRow(2));
			colneBuf.append(String.format("return new %s(oid);", clazz.getName()));
			colneBuf.append(nextRow(1));
			colneBuf.append("}");
			CtMethod method = CtMethod.make(colneBuf.toString(), clazz);
			clazz.addMethod(method);
			
			return;
		} 

		if (Type.gameObjectType.is(clazz)) {
			StringBuffer colneBuf = new StringBuffer();
			
			colneBuf.append(nextRow(1));
			colneBuf.append("protected net.itas.core.Game clone(java.lang.Object Id) {");
			colneBuf.append(nextRow(2));
			colneBuf.append("String oid =(java.lang.String)Id;");
			colneBuf.append(nextRow(2));
			colneBuf.append(String.format("return new %s(oid);", clazz.getName()));
			colneBuf.append(nextRow(1));
			colneBuf.append("}");
			
			CtMethod method = CtMethod.make(colneBuf.toString(), clazz);
			clazz.addMethod(method);
			return;
		} 

		throw new ItasException("unkown class type:" + clazz.getName());
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
