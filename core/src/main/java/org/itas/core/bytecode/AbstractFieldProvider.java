package org.itas.core.bytecode;


/**
 * 属性[field]字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月28日上午10:13:48
 */
abstract class AbstractFieldProvider implements FieldProvider {

	protected MethodProvider provider;
	
	@Override
	public void setMethodProvider(MethodProvider provider) {
		this.provider = provider;
	}
	
}
