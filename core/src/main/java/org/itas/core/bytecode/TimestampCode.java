package org.itas.core.bytecode;

import javassist.CtField;

public class TimestampCode extends ByteCodeType {

	public TimestampCode(Modify modify) {
		super(modify);
	}

	@Override
	protected String setStatement(CtField field) {
		StringBuilder buffer = new StringBuilder();
		
		buffer.append("\t\t");
		buffer.append("state.setTimestamp(");
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
		buffer.append("(result.getTimestamp(\"");
		buffer.append(field.getName());
		buffer.append("\"));");
		
		return buffer.toString();
	}

}
