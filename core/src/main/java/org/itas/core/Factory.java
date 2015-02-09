package org.itas.core;

import java.util.HashMap;
import java.util.Map;

import org.itas.util.Utils.ClassUtils;
import org.itas.util.Utils.Objects;

public final class Factory {

	/** 单例缓存*/
	private static final Map<Class<?>, Object> INSTANCE_DATAS;
	
	static {
		INSTANCE_DATAS = new HashMap<>();
	}

	/**
	 * <p>获取单例对象</p>
	 * @param clzz 对象类
	 * @return 单例对象
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> clzz) {
		Object data = INSTANCE_DATAS.get(clzz);
		if (Objects.nonNull(data)) {
			return (T)data;
		}
		

		return (T) newInstance0(clzz);
	}
	
	private static Object newInstance0(Class<?> clzz) {
		synchronized (clzz) {
			Object data = ClassUtils.newInstance(clzz);
			INSTANCE_DATAS.putIfAbsent(clzz, data);
			
			return data;
		}
	}

	private Factory() { }

}
