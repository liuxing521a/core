package org.itas.core.bytecode;

import javassist.CtField;

/**
 * int类型statement预处理生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午5:11:23
 */
class StatementIntProvider extends ByteCodeType {

	private static final String STATEMENT_SET = 
			"\t\t" +
			"state.setInt(%s, get%s());";
	
	private static final String RESULTSET_GET = 
			"\t\t" +
			"set%s(result.getInt(\"%s\"));";
	
	public StatementIntProvider(Modify modify) {
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
