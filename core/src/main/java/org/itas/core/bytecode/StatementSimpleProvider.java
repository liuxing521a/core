package org.itas.core.code.type;

import javassist.CtField;

import org.itas.core.code.CodeType;
import org.itas.core.code.Modify;

public class SimpleCode extends CodeType {

	public SimpleCode(Modify modify) {
		super(modify);
	}

	@Override
	protected String setStatement(CtField field) {
		StringBuilder buffer = new StringBuilder();
		
		buffer.append("\t\t");
		buffer.append("String id_");
		buffer.append(field.getName());
		buffer.append(" = \"\";");
		
		buffer.append("\n\t\t");
		buffer.append("if (get");
		buffer.append(firstKeyUpCase(field.getName()));
		buffer.append("() != null) {");
		buffer.append("\n\t\t\t");
		
		buffer.append("id_");
		buffer.append(field.getName());
		buffer.append(" = get");
		buffer.append(firstKeyUpCase(field.getName()));
		buffer.append("().getId();");
		buffer.append("\n\t\t");
		buffer.append("}");
		
		buffer.append("\n\t\t");
		buffer.append("state.setString(");
		buffer.append(modify.incIndex());
		buffer.append(", id_");
		buffer.append(field.getName());
		buffer.append(");");
		
		return buffer.toString();
	}

	@Override
	protected String getResultSet(CtField field) {
		StringBuilder buffer = new StringBuilder();
		
		buffer.append("\t\t");
		buffer.append("String id_");
		buffer.append(field.getName());
		buffer.append(" = result.getString(\"");
		buffer.append(field.getName());
		buffer.append("\");");

		buffer.append("\n\t\t");
		buffer.append("if (org.itas.util.Utils.Objects.nonEmpty(id_");
		buffer.append(field.getName());
		buffer.append(")) {");
		buffer.append("\n\t\t\t");
		buffer.append("set");
		buffer.append(firstKeyUpCase(field.getName()));
		buffer.append("(new org.itas.core.Simple(id_");
		buffer.append(field.getName());
		buffer.append("));");
		buffer.append("\n\t\t");
		buffer.append("}");
		
		
		return buffer.toString();
	}

}
