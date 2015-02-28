package org.itas.core.bytecode;

import javassist.CtField;

/**
 * long类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午5:11:36
 */
class FieldLongProvider extends AbstractFieldProvider {

	private static final String STATEMENT_SET = 
			"\t\t" +
			"state.setLong(%s, get%s());";
	
	private static final String RESULTSET_GET = 
			"\t\t" +
			"set%s(result.getLong(\"%s\"));";
	
	public FieldLongProvider(Modify modify) {
		super(modify);
	}

	@Override
	protected String setStatement(CtField field) {
		return String.format(STATEMENT_SET, modify.incIndex(), firstKeyUpCase(field.getName()));
	}

	@Override
	protected String getResultSet(CtField field) {
		return String.format(RESULTSET_GET, firstKeyUpCase(field.getName()), field.getName());
	}

}
