package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

/**
 * simple数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午5:29:54
 */
class SimpleProvider extends AbstractFieldProvider 
		implements FieldProvider, TypeProvider {

	private static final String STATEMENT_SET = 
			"\t\t" 
			+ "String id_%s = \"\";" 
			+ "\n\t\t" 
			+ "if (get%s() != null) {" 
			+ "\n\t\t\t" 
			+ "id_%s = get%s().getId();" 
			+ "\n\t\t" 
			+ "}" 
			+ "\n\t\t" 
			+ "state.setString(%s, id_%s);";


	private static final String RESULTSET_GET = 
			"\t\t"
			+ "String id_%s = result.getString(\"%s\");" 
			+ "\n\t\t"
			+ "if (id_%s != null && id_%s.length() > 0) {" 
			+ "\n\t\t\t"
			+ "set%s(new org.itas.core.Simple(id_%s));" 
			+ "\n\t\t"
			+ "}";

	public static final SimpleProvider PROVIDER = new SimpleProvider();
	
	private SimpleProvider() {
	}
	
	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.simple_ == clazz;
	}
	
	@Override
	public boolean isType(CtClass clazz) {
		return javassistType.simple_ == clazz;
	}

	@Override
	public String sqlType(CtField field) throws Exception {
		return String.format("`%s` VARCHAR(36) NOT NULL DEFAULT ''", field.getName());
	}
	
	@Override
	public String setStatement(int index, CtField field) {
		return String.format(STATEMENT_SET, field.getName(), upCase(field.getName()),
				field.getName(), upCase(field.getName()), index, field.getName());
	}

	@Override
	public String getResultSet(CtField field) {
		return String.format(RESULTSET_GET, field.getName(), field.getName(),
				field.getName(), field.getName(), upCase(field.getName()), field.getName());
	}

}
