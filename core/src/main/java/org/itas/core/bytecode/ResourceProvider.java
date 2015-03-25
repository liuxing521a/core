package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;

/**
 * 资源数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午5:30:17
 */
class ResourceProvider extends AbstractFieldProvider 
		implements FieldProvider, TypeProvider {
	
	private static final String STATEMENT_SET = 
			"\t\t" 
			+ "String rid_%s = \"\";" 
			+ "\n\t\t" 
			+ "if (get%s() != null) {" 
			+ "\n\t\t\t" 
			+ "rid_%s = get%s().getId();" 
			+ "\n\t\t" 
			+ "}" 
			+ "\n\t\t" 
			+ "state.setString(%s, rid_%s);";


	private static final String RESULTSET_GET = 
			"\t\t"
			+ "String rid_%s = result.getString(\"%s\");" 
			+ "\n\t\t"
			+ "if (rid_%s != null && rid_%s.length() > 0) {" 
			+ "\n\t\t\t"
			+ "set%s(org.itas.core.Pool.getResource(rid_%s));" 
			+ "\n\t\t"
			+ "}";
	
	public static final ResourceProvider PROVIDER = new ResourceProvider();
	
	private ResourceProvider() {
	}
	
	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.resource_.isAssignableFrom(clazz);
	}
	
	@Override
	public boolean isType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.resource_);
	}

	@Override
	public String sqlType(CtField field) throws Exception {
		return String.format("`%s` VARCHAR(24) NOT NULL DEFAULT ''", field.getName());
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
