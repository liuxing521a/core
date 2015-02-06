package org.itas.buffer;

import java.nio.ByteBuffer;


/**
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014-3-13
 */
public abstract class Service<T> {
	
	
	/**
	 * <p>事件头版半部分</p>
	 * @return
	 */
	public abstract byte PREFIX();

	/**
	 * <p>事件分配器</p>
	 * @param message 事件附带信息 
	 */
	public abstract void dispatch(T user, byte suffix, ByteBuffer buffer) throws Exception;
	
}
