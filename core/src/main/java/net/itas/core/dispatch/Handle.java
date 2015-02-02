package net.itas.core.dispatch;

import net.itas.core.User;
import net.itas.core.io.nio.Message;


/**
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014-3-13
 */
public abstract class Handle {
	
	/**
	 * <p>事件分配器</p>
	 * @param message 事件附带信息 
	 */
	public void dispatch(User action, Message message) throws Exception {
		
	}
	
	/**
	 * <p>获取处理者协议</p>
	 * @return
	 */
	public short getEventNum() {
		return 0;
	}
	
}
