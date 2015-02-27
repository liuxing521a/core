package org.itas.core.bytecode;

import javassist.CtField;

/**
 * 资源类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月26日下午5:30:17
 */
class CodeResourcesProvider extends Provider {
	
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

	public CodeResourcesProvider(Modify modify) {
		super(modify);
	}

	@Override
	protected String setStatement(CtField field) {
		return String.format(STATEMENT_SET, field.getName(), firstKeyUpCase(field.getName()),
				field.getName(), firstKeyUpCase(field.getName()), modify.incIndex(), field.getName());
	}

	@Override
	protected String getResultSet(CtField field) {
		return String.format(RESULTSET_GET, field.getName(), field.getName(),
				field.getName(), field.getName(), firstKeyUpCase(field.getName()), field.getName());
	}
	
}
