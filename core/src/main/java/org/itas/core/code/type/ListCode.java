package org.itas.core.code.type;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.SignatureAttribute.ClassType;
import net.itas.core.annotation.Clazz;

import org.itas.core.code.Modify;
import org.itas.util.Utils.Objects;

public class ListCode extends AbStractGeneric {

	public ListCode(Modify modify) {
		super(modify);
	}

	@Override
	protected String setStatement(CtField field) throws Exception {
		StringBuilder buffer = new StringBuilder();
		
		buffer.append("\t\t");
		buffer.append("state.setString(");
		buffer.append(modify.incIndex());
		buffer.append(", toString(get");
		buffer.append(field.getName());
		buffer.append(");");
		
		return buffer.toString();
	}

	@Override
	protected String getResultSet(CtField field) throws Exception {
		StringBuilder buffer = new StringBuilder();
		
		ClassType definType = (ClassType)SignatureAttribute.toFieldSignature(field.getGenericSignature());
		ClassType chirldType = (ClassType)(definType.getTypeArguments()[0].getType());
		CtClass genericType = ClassPool.getDefault().get(chirldType.getName());

		buffer.append("\t\t");
		buffer.append("String ");
		buffer.append(field.getName());
		buffer.append("Data = result.getString(\"");
		buffer.append(field.getName());
		buffer.append("\");");
		
		buffer.append("\n\t\t");
		buffer.append("java.util.List ");
		buffer.append(field.getName());
		buffer.append("List");
		buffer.append(" = parseList(");
		buffer.append(field.getName());
		buffer.append("Data);");

		buffer.append("\n\t\t");
		buffer.append(definType.getName());
		buffer.append(" ");
		buffer.append(field.getName());
		buffer.append("Array = ");
		buffer.append("new ");
		Object annotiation = field.getAnnotation(Clazz.class);
		if (Objects.nonNull(annotiation)) {
			Clazz clazz = (Clazz)annotiation;
			buffer.append(clazz.value().getName());
		} else {
			buffer.append("java.util.ArrayList");
		}
		buffer.append("(8);");
		
		buffer.append("\n\t\t");
		buffer.append("for (Object value : ");
		buffer.append(field.getName());
		buffer.append("List) {");
		
		buffer.append("\n\t\t\t");
		buffer.append(field.getName());
		buffer.append("Array.add(");
		buffer.append(string2Generic(genericType, "(String)value"));
		buffer.append(");");
		
		buffer.append("\n\t\t");
		buffer.append("}");
		
		buffer.append("\n\t\t");
		buffer.append("set");
		buffer.append(firstKeyUpCase(field.getName()));
		buffer.append("(");
		buffer.append(field.getName());
		buffer.append("Array();");
		
		return buffer.toString();
	}
	
}
