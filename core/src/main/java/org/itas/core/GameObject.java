package org.itas.core;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Set;

import org.itas.core.annotation.Primary;
import org.itas.core.annotation.UnSave;
import org.itas.core.util.DataContainers;
import org.itas.util.ItasException;
import org.itas.util.cache.Cacheable;

/**
 * <p>自动同步数据库基类</p>
 * 所有自动 从数据库加载、自动修改数据库、自动删除等同步对象都需要继承此类；</br>
 * 继承此类后需要不会自动生成Id，需要制定Id
 * @author liuzhen<liuxing521a@gmail.com>
 * @createTime 2014年12月15日下午4:25:47
 */
public abstract class GameObject implements HashId, 
    Externalizable, Cacheable, DataContainers {

	enum DataStatus {
		unload,		// 未加载
		load,		// 无状态数据
		news,		// 新数据
		modify,		// 修改数据
		destory,	// 销毁数据
	}
	
	protected GameObject(String Id) {
		this.Id = Id;
	}
	
	/**
	 * 唯一Id
	 */
	@Primary
	protected String Id;
	
	/** 
	 * 修改时间 
	 */
	protected Timestamp updateTime;
	
	/** 
	 * 创建时间 
	 */
	protected Timestamp createTime;
	
	/** 
	 * 对象当前状态 
	 */
	@UnSave private volatile DataStatus status = DataStatus.unload;
	
	@UnSave private static final AbstractDBSync dbSync;
	
	static {
		dbSync = (AbstractDBSync)Ioc.Ioc.getInstance(DBSync.class);
	}
	
	/**
	 * id前缀，以次来区分一个字符串id属于那个对象
	 */
	protected abstract String PRIFEX();
	
	/**
	 * <p>自动生成对象</p>
	 * 注:需要指定id
	 * @param Id 新对象的id
	 * @return 生成的对象
	 */
	protected abstract <T extends GameObject> T autoInstance(String Id);
	
	protected void initialize() {
		status = DataStatus.news;
	}
	
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
	
	protected void modify() {
		if (status == DataStatus.load) {
			status = DataStatus.modify;
			dbSync.addUpdate(this);
		}
	}

	public void destroy() {
		if (status != DataStatus.destory) {
			status = DataStatus.destory;
			dbSync.addDelete(this);
		}
	}

	DataStatus getDataStatus() {
		return status;
	}

	final void doStatement(PreparedStatement statement, DataStatus status) throws SQLException {
		switch (status) {
		case news: 
			doInsert(statement);
			statement.addBatch();
			status = DataStatus.load;
			break;
		case modify:
			doUpdate(statement);
			statement.addBatch();
			status = DataStatus.load;
			break;
		case destory: 
			doDelete(statement);
			statement.addBatch();
			break;
		default: 
			throw new ItasException(this.getClass().getName() + " unkown data status:" + status);
		}
	}
	
	final void doResultSet(ResultSet result) throws SQLException {
		if (status == DataStatus.unload) {
			doFill(result);
			this.status = DataStatus.load;
		}
	}
	
	protected String tableName() {
		throw new ItasException("getTableName must @Override");
	}
	
	protected String selectSQLArray() {
		throw new ItasException("selectSQLArray must Override");
	}
	
	protected String selectSQL() {
		throw new ItasException("selectSQL must Override");
	}
	
	protected String insertSQL() {
		throw new ItasException("insertSQL must Override");
	}
	
	protected String updateSQL() {
		throw new ItasException("updateSQL must Override");
	}
	
	protected String deleteSQL() {
		throw new ItasException("deleteSQL must Override");
	}
	
	protected void doCreate(Statement statement) {
		throw new ItasException("doCreate must Override");
	}
	
	protected void doAlter(Statement statement, Set<String> excludeColums) throws java.sql.SQLException {
		throw new ItasException("doAlter must Override");
	}
	
	protected void doInsert(PreparedStatement statement) throws java.sql.SQLException {
		throw new ItasException("doInsert must Override");
	}

	protected void doUpdate(PreparedStatement statement) throws java.sql.SQLException {
		throw new ItasException("doUpdate must Override");
	}
	
	protected void doDelete(PreparedStatement statement) throws java.sql.SQLException {
		throw new ItasException("doDelete must Override");
	}
	
	protected void doFill(ResultSet result) throws java.sql.SQLException {
		throw new ItasException("doFill must Override");
	}

	protected GameObject clone(String Id) {
		throw new ItasException("clone(String Id) must Override");
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
	
	public int getCachedSize() {
		return 86;
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeUTF(Id);
		out.writeObject(updateTime);
		out.writeObject(createTime);
		out.writeObject(status);
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		Id = in.readUTF();
		updateTime = (Timestamp)in.readObject();
		createTime = (Timestamp)in.readObject();
		status = (DataStatus)in.readObject();
	}

}
