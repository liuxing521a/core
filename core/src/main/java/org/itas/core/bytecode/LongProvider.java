package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

/**
 * long数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午5:11:36
 */
class LongProvider extends AbstractFieldProvider 
		implements FieldProvider, TypeProvider {

	private static final String STATEMENT_SET = 
			"\t\t" +
			"state.setLong(%s, get%s());";
	
	private static final String RESULTSET_GET = 
			"\t\t" +
			"set%s(result.getLong(\"%s\"));";
	
	public static final LongProvider PROVIDER = new LongProvider();
	
	private LongProvider() {
	}
	
	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.long_ == clazz || javaType.longWrap == clazz;
	}

	@Override
	public boolean isType(CtClass clazz) {
		return javassistType.long_ == clazz || javassistType.longWrap == clazz;
	}

	@Override
	public String sqlType(CtField field) {
		return String.format("`%s` BIGINT(20) NOT NULL DEFAULT '0'", field.getName());
	}
	
	@Override
	public String setStatement(int index, CtField field) {
		return String.format(STATEMENT_SET, index, upCase(field.getName()));
	}

	@Override
	public String getResultSet(CtField field) {
		return String.format(RESULTSET_GET, upCase(field.getName()), field.getName());
	}

}
