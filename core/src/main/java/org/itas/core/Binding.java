package org.itas.core;

public interface Binding {

	/**
	 * 绑定类实现自动处理
	 * @param clazz 要绑定父类
	 * @param pack 绑定包名
	 */
	abstract void bind(Class<?> clazz, String pack);
	
}
