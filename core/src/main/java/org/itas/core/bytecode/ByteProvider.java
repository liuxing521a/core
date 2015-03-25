package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

/**
 * byte数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午4:51:14
 */
class ByteProvider extends AbstractFieldProvider 
    implements FieldProvider, TypeProvider {

	private static final String STATEMENT_SET = 
			"\t\t" +
			"state.setByte(%s, get%s());";
	
	private static final String RESULTSET_GET = 
			"\t\t" +
			"set%s(result.getByte(\"%s\"));";
	
	public static final ByteProvider PROVIDER = new ByteProvider();
	
	private ByteProvider() {
	}
	
	@Override
	public String setStatement(int index, CtField field) {
		return String.format(STATEMENT_SET, index, upCase(field.getName()));
	}

	@Override
	public String getResultSet(CtField field) {
		return String.format(RESULTSET_GET, upCase(field.getName()), field.getName());
	}
	
	public boolean isType(Class<?> clazz) {
		return javaType.byte_ == clazz || javaType.byteWrap == clazz;
	}

	@Override
	public boolean isType(CtClass clazz) {
		return javassistType.byte_ == clazz || javassistType.byteWrap == clazz;
	}

	@Override
	public String sqlType(CtField field) {
		return String.format("`%s` TINYINT(4) NOT NULL DEFAULT '0'", field.getName());
	}

}
