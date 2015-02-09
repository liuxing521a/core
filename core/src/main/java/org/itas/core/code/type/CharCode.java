package org.itas.core.code.type;

import javassist.CtField;

import org.itas.core.code.CodeType;
import org.itas.core.code.Modify;

public class CharCode extends CodeType {

	public CharCode(Modify modify) {
		super(modify);
	}

	@Override
	protected String setStatement(CtField field) {
		StringBuilder buffer = new StringBuilder();
		
		buffer.append("\t\t");
		buffer.append("state.setString(");
		buffer.append(modify.incIndex());
		buffer.append(", String.valueOf(get");
		buffer.append(firstKeyUpCase(field.getName()));
		buffer.append("()));");
		
		return buffer.toString();
	}

	@Override
	protected String getResultSet(CtField field) {
		StringBuilder buffer = new StringBuilder();
		
		buffer.append("\t\t");
		buffer.append("set");
		buffer.append(firstKeyUpCase(field.getName()));
		buffer.append("(result.getString(\"");
		buffer.append(field.getName());
		buffer.append("\").charAt(0));");
		
		return buffer.toString();
	}

}
