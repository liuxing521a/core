package org.itas.core.bytecode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import net.itas.core.annotation.SQLEntity;

import org.itas.core.Index;
import org.itas.core.Primary;
import org.itas.core.Unique;
import org.itas.util.ItasException;
import org.itas.util.Logger;

/**
 *  SQL操作方法字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月28日上午10:13:29
 */
abstract class MethodSQLProvider extends AbstractMethodProvider {
	
	MethodSQLProvider() {
		super();
	}
	
	protected String tableName(CtClass clazz) throws ClassNotFoundException {
		Object sqlEntity = clazz.getAnnotation(SQLEntity.class);
		if (sqlEntity == null) {
			throw new ItasException(clazz.getName() + " module must has annotation[SQLEntity|UnPersistence]");
		}

		return ((SQLEntity)sqlEntity).value();
	}

	static class SQLCreateProvider extends MethodSQLProvider {
		
		SQLCreateProvider() {
			super();
		}

		private List<String> keys;
		
		@Override
		public void begin(CtClass clazz) throws Exception {
			this.ctClass = clazz;
			this.keys = new ArrayList<String>(4);
			this.buffer = new StringBuffer();
			
			buffer.append("CREATE TABLE IF NOT EXISTS `");
			buffer.append(tableName(clazz));
			buffer.append("`(");
		}

		@Override
		public void append(CtField field) throws Exception {
			buffer.append(getType(field).columnSQL(field));
			buffer.append(",");
			
			if (field.hasAnnotation(Primary.class)) {
				keys.add(String.format("PRIMARY KEY `%s` (`%s`)", field.getName(), field.getName()));
			} else if (field.hasAnnotation(Unique.class)) {
				keys.add(String.format("UNIQUE KEY `%s` (`%s`)", field.getName(), field.getName()));
			} else if (field.hasAnnotation(Index.class)) {
				keys.add(String.format("KEY `%s` (`%s`)", field.getName(), field.getName()));
			}
		}
		
		@Override
		public void end() {
			
			for (String index : keys) {
				buffer.append(index);
				buffer.append(",");
			}

			buffer.deleteCharAt(buffer.length() - 1);
			buffer.append(") ENGINE=MyISAM DEFAULT CHARSET=utf8;");
		}
		
		
		@Override
		public CtMethod toMethod() throws CannotCompileException {
			StringBuffer methodBuf = new StringBuffer();
			
			methodBuf.append("protected void createSQL(java.sql.Statement stmt) {");
			methodBuf.append("stmt.addBatch(\"").append(buffer.toString()).append("\");");
			methodBuf.append("}");
			
			Logger.trace(methodBuf.toString());
			return CtMethod.make(methodBuf.toString(), ctClass);
		}

		@Override
		public String toString() {
			String sql =  buffer.toString();
			sql = sql.replace(",", ",\n\t");
			sql = sql.replace("`(`", "`(\n\t`");
			sql = sql.replace(") ENGINE=MyISAM", "\n) ENGINE=MyISAM");
			
			Logger.trace("\n{}", sql);
			return sql;
		}
		
		
	}
	
	static class SQLAlterProvider extends MethodSQLProvider {
		
		private Set<String> alters;
		
		private Set<String> exitsColumns;
		
		SQLAlterProvider() {
			super();
		}
		
		public Set<String> getExitsColumns() {
			return exitsColumns;
		}

		public void setExitsColumns(Set<String> exitsColumns) {
			this.exitsColumns = exitsColumns;
		}

		@Override
		public void begin(CtClass clazz) throws ClassNotFoundException {
			this.ctClass = clazz;
			this.alters = new HashSet<>();
		}

		@Override
		public void append(CtField field) throws NotFoundException, Exception {
			if (!exitsColumns.contains(field.getName())) {
				alters.add(String.format("ALTER TABLE `%s` ADD %s;", tableName(ctClass), getType(field).columnSQL(field)));
			}
		}

		@Override
		public void end() {

		}
		
		@Override
		public CtMethod toMethod() throws CannotCompileException {
			StringBuffer methodBuf = new StringBuffer();
			
			methodBuf.append("protected void alterSQL(java.sql.Statement stmt) {");
			for (String alter : alters) {
				methodBuf.append("stmt.addBatch(\"").append(alter).append("\");");
			}
			methodBuf.append("}");
			
			return CtMethod.make(methodBuf.toString(), ctClass);
		}
		
		@Override
		public String toString() {
			StringBuffer buffer = new StringBuffer();
			alters.forEach(sql->buffer.append(sql).append("\n"));
			
			Logger.trace("\n{}", buffer.toString());
			return buffer.toString();
		}

	}
	
	static class SQLSelectProvider extends MethodSQLProvider {
		
		SQLSelectProvider() {
			super();
		}
		
		@Override
		public void begin(CtClass clazz) {
			this.ctClass = clazz;
			this.buffer = new StringBuffer();
			
			buffer.append("SELECT ");
		}

		@Override
		public void append(CtField field) {
			buffer.append("`");
			buffer.append(field.getName());
			buffer.append("`");
			buffer.append(", ");
		}
		
		@Override
		public void end() throws ClassNotFoundException {
			buffer.delete(buffer.length() - 2, buffer.length());
			buffer.append(" FROM `");
			buffer.append(tableName(ctClass));
			buffer.append("` WHERE Id = ?;");
		}

		@Override
		public CtMethod toMethod() throws CannotCompileException {
			StringBuffer methodBuf = new StringBuffer();
			
			methodBuf.append("protected String selectSQL() {");
			methodBuf.append("return \"").append(buffer.toString()).append("\";");
			methodBuf.append("}");
			
			return CtMethod.make(methodBuf.toString(), ctClass);
		}
		
		@Override
		public String toString() {
			Logger.trace("\n{}", buffer.toString());
			return buffer.toString();
		}
		
	}

	static class SQLInsertProvider extends MethodSQLProvider {
		
		private int count;
		
		SQLInsertProvider() {
			super();
		}
		
		@Override
		public void begin(CtClass clazz) throws ClassNotFoundException {
			this.count = 0;
			this.ctClass = clazz;
			this.buffer = new StringBuffer();
			
			buffer.append("INSERT INTO `");
			buffer.append(tableName(clazz));
			buffer.append("` (");
		}

		@Override
		public void append(CtField field) {
			this.count ++;
			
			buffer.append("`");
			buffer.append(field.getName());
			buffer.append("`");
			buffer.append(", ");
		}
		
		@Override
		public void end() throws Exception {
			buffer.delete(buffer.length() - 2, buffer.length());
			buffer.append(") VALUES (");
			
			for (int i = 0; i < count; i++) {
				buffer.append("?, ");
			}
			buffer.delete(buffer.length() - 2, buffer.length());
			buffer.append(");");
		}

		@Override
		public CtMethod toMethod() throws CannotCompileException {
			StringBuffer methodBuf = new StringBuffer();
			
			methodBuf.append("protected String insertSQL() {");
			methodBuf.append("return \"").append(buffer.toString()).append("\";");
			methodBuf.append("}");
			
			return CtMethod.make(methodBuf.toString(), ctClass);
		}

		@Override
		public String toString() {
			Logger.trace("\n{}", buffer.toString());
			return buffer.toString();
		}
		
	}
	
	static class SQLUpdateProvider extends MethodSQLProvider {
		
		
		SQLUpdateProvider() {
			super();
		}
		
		@Override
		public void begin(CtClass clazz) throws ClassNotFoundException {
			this.ctClass = clazz;
			this.buffer = new StringBuffer();
			
			buffer.append("UPDATE `");
			buffer.append(tableName(clazz));
			buffer.append("` SET ");
		}

		@Override
		public void append(CtField field) {
			buffer.append("`");
			buffer.append(field.getName());
			buffer.append("`");
			buffer.append(" = ?, ");
		}

		@Override
		public void end() throws Exception {
			buffer.delete(buffer.length() - 2, buffer.length());
			buffer.append(" WHERE Id = ?;");
		}
		
		@Override
		public CtMethod toMethod() throws CannotCompileException {
			StringBuffer methodBuf = new StringBuffer();
			
			methodBuf.append("protected String updateSQL() {");
			methodBuf.append("return \"").append(buffer.toString()).append("\";");
			methodBuf.append("}");
			
			return CtMethod.make(methodBuf.toString(), ctClass);
		}
		
		@Override
		public String toString() {
			Logger.trace("\n{}", buffer.toString());
			return buffer.toString();
		}
	}
	
	static class SQLDeleteProvider extends MethodSQLProvider {
		
		SQLDeleteProvider() {
			super();
		}
		
		@Override
		public void begin(CtClass clazz) {
			this.ctClass = clazz;
			this.buffer = new StringBuffer();
		}

		@Override
		public void append(CtField field) {
			
		}

		@Override
		public void end() throws Exception {
			buffer.append("DELETE FROM `");
			buffer.append(tableName(ctClass));
			buffer.append("` WHERE Id = ?;");
		}
		
		@Override
		public CtMethod toMethod() throws CannotCompileException {
			StringBuffer methodBuf = new StringBuffer();
			
			methodBuf.append("protected String deleteSQL() {");
			methodBuf.append("return \"").append(buffer.toString()).append("\";");
			methodBuf.append("}");
			
			return CtMethod.make(methodBuf.toString(), ctClass);	
		}

		@Override
		public String toString() {
			Logger.trace("\n{}", buffer.toString());
			return buffer.toString();
		}
	}
	
	static class SQLTableNameProvider extends MethodSQLProvider {

		@Override
		public void begin(CtClass clazz) throws Exception {
			this.ctClass = clazz;
		}
		
		@Override
		public void append(CtField field) throws Exception {
			
		}

		@Override
		public CtMethod toMethod() throws CannotCompileException, ClassNotFoundException {
			StringBuffer methodBuf = new StringBuffer();
			
			methodBuf.append("protected String tableName() {");
			methodBuf.append("return \"").append(tableName(ctClass)).append("\";");
			methodBuf.append("}");
			
			return CtMethod.make(methodBuf.toString(), ctClass);
		}
		
	}
	
}
