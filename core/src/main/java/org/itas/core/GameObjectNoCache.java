package org.itas.core;

import static org.itas.core.GameObjectAotuID.aotoId;

/**
 * <p>自动同步数据库基类</p>
 * 与GameBase不同的是，此类不可以自动创建对象；如果手动创建对象，不需要制定Id
 * @author liuzhen<liuxing521a@gmail.com>
 * @createTime 2014年12月15日下午4:30:51
 */
public abstract class GameObjectNoCache extends GameBase implements HashId {
	
	protected GameObjectNoCache(String Id) {
		super(aotoId(Id));
	}
	
	@Override
	public final boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (!(o instanceof GameObjectNoCache)) {
			return false;
		}
		
		return ((GameObjectAotuID) o).Id.equals(Id);
	}
	
}