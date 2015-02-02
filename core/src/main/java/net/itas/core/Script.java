package net.itas.core;

import java.lang.reflect.Method;

/**
 * 加载脚本
 * 
 * @author liuzhen<liuxing521a@gmail.com>
 * @createTime 2014-06-12
 */
public abstract class Script {
	
	private Reference<Method> ref;

	public Script() {
		this.ref = new Reference<>();
	}
	
	public void setMethod(Method method) {
		method.setAccessible(true);
		ref.set(method);
	}
	
	@SuppressWarnings("unchecked")
	public <Value> Value eval(Object... args) {
		try {
			if (ref.isPresent()) {
				return (Value)ref.get().invoke(null, args);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
		throw new RuntimeException("unkown method");
	}
	
	public static final class Reference<T> {

		private T value;

		public T get() {
			return value;
		}
		
		public boolean isPresent() {
			return value != null;
	    }

		public void set(T value) {
			this.value = value;
		}
	}

}
