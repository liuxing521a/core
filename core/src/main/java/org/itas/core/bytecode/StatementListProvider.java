package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.SignatureAttribute.ClassType;
import net.itas.core.annotation.Clazz;
import net.itas.core.annotation.Size;

import org.itas.util.Utils.Objects;

class StatementListProvider extends AbStractGeneric {

	private static final String STATEMENT_SET = 
			"\t\t" 
			+ "state.setString(%s, org.itas.core.util.GameObjects.toString(get%s()));" ;


	private static final String RESULTSET_GET = 
			"\t\t"
			+ "String %sData = result.getString(\"%s\");"
			+ "\n\t\t"
			+ "java.util.List %sList = org.itas.core.util.GameObjects.parseList(%sData);"
			+ "\n\t\t"
			+ "java.util.List %sArray = new %s();"
			+ "\n\t\t"
			+ "for (Object value : %sList) {"
			+ "\n\t\t\t"
			+ "%sArray.add(%s);"
			+ "\n\t\t"
			+ "}"
			+ "\n\t\t"
			+ "set%s(%sArray);";
	
	public StatementListProvider(Modify modify) {
		super(modify);
	}

	@Override
	protected String setStatement(CtField field) throws Exception {
		return String.format(STATEMENT_SET, modify.incIndex(), firstKeyUpCase(field.getName()));
	}

	@Override
	protected String getResultSet(CtField field) throws Exception {
		ClassType definType = (ClassType)SignatureAttribute.toFieldSignature(field.getGenericSignature());
		ClassType chirldType = (ClassType)(definType.getTypeArguments()[0].getType());
		CtClass genericType = ClassPool.getDefault().get("org.itas.core.bytecode.TestStatementListProvider$TestMode");
		
		Object annotiation = field.getAnnotation(Clazz.class);
		String listClassName = Objects.nonNull(annotiation) ? 
			((Clazz)annotiation).value().getName() : "java.util.ArrayList";
		
		
		return String.format(RESULTSET_GET, field.getName(), field.getName(), field.getName(),
				field.getName(), field.getName(), listClassName, field.getName(),
				field.getName(), string2Generic(genericType, "(String)value"),
				firstKeyUpCase(field.getName()), field.getName());
	}
	
}
