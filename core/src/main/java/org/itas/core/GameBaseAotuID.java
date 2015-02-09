package org.itas.core;

import org.itas.util.ItasException;

/**
 * <p>自动同步数据库基类</p>
 * 与GameBase不同的是，此类不可以自动创建对象；如果手动创建对象，不需要制定Id
 * @author liuzhen<liuxing521a@gmail.com>
 * @createTime 2014年12月15日下午4:30:51
 */
public abstract class GameBaseAotuID extends GameObject {
	
	protected GameBaseAotuID(String Id) {
		super(Id);
	}

	protected <T extends GameObject> T autoInstance(String Id) {
		throw new ItasException("child of GameBaseAotuID can not auto new instance");
	}
}
