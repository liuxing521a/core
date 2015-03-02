package org.itas.core.bytecode;

import static org.itas.core.util.ByteCodeUtils.firstKeyUpCase;
import javassist.CtField;

/**
 * short数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午5:05:39
 */
class FieldShortProvider extends AbstractFieldProvider {

	private static final String STATEMENT_SET = 
			"\t\t" +
			"state.setShort(%s, get%s());";
	
	private static final String RESULTSET_GET = 
			"\t\t" +
			"set%s(result.getShort(\"%s\"));";
	
	public FieldShortProvider() {
		
	}

	@Override
	public String setStatement(CtField field) {
		return String.format(STATEMENT_SET, provider.getAndIncIndex(), firstKeyUpCase(field.getName()));
	}

	@Override
	public String getResultSet(CtField field) {
		return String.format(RESULTSET_GET, firstKeyUpCase(field.getName()), field.getName());
	}

}
