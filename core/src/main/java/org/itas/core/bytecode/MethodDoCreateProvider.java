package org.itas.core.bytecode;

import static org.itas.core.util.ByteCodeUtils.tableName;

import java.util.ArrayList;
import java.util.List;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import org.itas.core.Index;
import org.itas.core.Primary;
import org.itas.core.Unique;
import org.itas.util.Logger;

class MethodDoCreateProvider extends AbstractMethodProvider {


	private List<String> keys;
	
	@Override
	public void begin(CtClass clazz) throws Exception {
		super.begin(clazz);
		this.keys = new ArrayList<String>(4);
		
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
