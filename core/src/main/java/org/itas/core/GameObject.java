package org.itas.core;

import java.sql.Timestamp;

import net.itas.core.annotation.PrimaryfKey;

/**
 * <p>自动同步数据库基类</p>
 * 所有自动 从数据库加载、自动修改数据库、自动删除等同步对象都需要继承此类；</br>
 * 继承此类后需要不会自动生成Id，需要制定Id
 * @author liuzhen<liuxing521a@gmail.com>
 * @createTime 2014年12月15日下午4:25:47
 */
public abstract class GameObject extends GameBase implements HashId {

	protected GameObject(String Id) {
		super(Id);
		this.Id = Id;
	}
	
	/**
	 * 唯一Id
	 */
	@PrimaryfKey
	protected final String Id;
	
	/** 
	 * 修改时间 
	 */
	protected Timestamp updateTime;
	
	/** 
	 * 创建时间 
	 */
	protected Timestamp createTime;
	
	/**
	 * id前缀，以次来区分一个字符串id属于那个对象
	 */
	protected abstract String PRIFEX();
	
	@Override
	public String getId() {
		return Id;
	}
	
	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(long millis) {
		this.updateTime.setTime(millis);
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public final boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		
		if (!(o instanceof GameObject)) {
			return false;
		}
		
		return ((GameObject) o).Id.equals(Id);
	}

	@Override
	public final int hashCode() {
		return 31 + Id.hashCode();
	}

	/**
	 * <p>自动生成对象</p>
	 * 注:需要指定id
	 * @param Id 新对象的id
	 * @return 生成的对象
	 */
	protected abstract <T extends GameObject> T autoInstance(String Id);

}
