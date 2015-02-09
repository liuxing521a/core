package org.itas.core.code.type;

import javassist.CtField;

import org.itas.core.code.CodeType;
import org.itas.core.code.Modify;

public class IntCode extends CodeType {

	public IntCode(Modify modify) {
		super(modify);
	}

	@Override
	protected String setStatement(CtField field) {
		StringBuilder buffer = new StringBuilder();
		
		buffer.append("\t\t");
		buffer.append("state.setInt(");
		buffer.append(modify.incIndex());
		buffer.append(", get");
		buffer.append(firstKeyUpCase(field.getName()));
		buffer.append("());");
		
		return buffer.toString();
	}

	@Override
	protected String getResultSet(CtField field) {
		StringBuilder buffer = new StringBuilder();
		
		buffer.append("\t\t");
		buffer.append("set");
		buffer.append(firstKeyUpCase(field.getName()));
		buffer.append("(result.getInt(\"");
		buffer.append(field.getName());
		buffer.append("\"));");
		
		return buffer.toString();
	}

}
