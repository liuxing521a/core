package org.itas.core.bytecode;

import javassist.CtField;

/**
 * 枚举关键字为String类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午4:51:14
 */
class FieldEnumStringProvider extends AbstractFieldProvider {
	
	private static final String STATEMENT_SET = 
			"\t\t" 
					+ "String estr_%s = \"\";"
					+ "\n\t\t"
					+ "if (get%s() != null) {"
					+ "\n\t\t\t"
					+ "estr_%s = get%s().key();"
					+ "\n\t\t"
					+ "}"
					+ "\n\t\t"
					+ "state.setString(%s, estr_%s);";

	private static final String RESULTSET_GET = 
			"\t\t" +
			"set%s(org.itas.core.util.Utils.EnumUtils.parse(%s.class, result.getString(\"%s\")));";
	

	public FieldEnumStringProvider(Modify modify) {
		super(modify);
	}

	@Override
	protected String setStatement(CtField field) {
		return String.format(STATEMENT_SET, field.getName(), firstKeyUpCase(field.getName()), 
				field.getName(), firstKeyUpCase(field.getName()), modify.incIndex(), field.getName());
	}

	@Override
	protected String getResultSet(CtField field) throws Exception {
		return String.format(RESULTSET_GET, firstKeyUpCase(field.getName()), 
				field.getType().getName(), field.getName());
	}
}
