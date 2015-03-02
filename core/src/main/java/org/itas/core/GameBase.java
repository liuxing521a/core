package org.itas.core;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import net.itas.core.annotation.UnSave;

import org.itas.util.ItasException;

abstract class GameBase implements Externalizable {

	/**
	 * 当前数据状态
	 */
	enum DataStatus {
		/**
		 * 需要一次性加载数据
		 */
		LOADEDARRAY,
		
		/**
		 * 无状态数据
		 */
		LOADED,		
		
		/**
		 * 新状态[等待插入数据库]
		 */
		NEW, 	
		
		/**
		 * 脏数据[等待同步数据库]
		 */
		DIRTY,		 
		
		/**
		 *  删除状态
		 */
		DESTROY, 	
	}

	/** 
	 * 对象当前状态 
	 */
	@UnSave 
	private volatile DataStatus status;

	protected GameBase(String Id) {
		this.status = DataStatus.LOADED;
	}

	protected void modify(String modify) {
		if (status == DataStatus.LOADED) {
			status = DataStatus.DIRTY;
//			sqlTools.addUpdate(this);
		}
	}

	public void destroy() {
		if (status != DataStatus.DESTROY) {
			status = DataStatus.DESTROY;
//			sqlTools.addDelete(this);
		}
	}

	void setLoaded() {
		this.status = DataStatus.LOADED;
	}

	

	DataStatus getDataStatus() {
		return status;
	}

	final String getSQL(DataStatus status) {
		switch (status) {
		case LOADEDARRAY: 	
			return selectSQLArray();
		case LOADED: 	
			return selectSQL();
		case NEW: 		
			return insertSQL();
		case DIRTY: 	
			return updateSQL();
		case DESTROY: 	
			return deleteSQL();
		default: 
			throw new ItasException(this.getClass().getName() + " unkown data status:" + status);
		}
	}
	
	final void doStatement(PreparedStatement statement, DataStatus status) throws SQLException {
		switch (status) {
		case NEW: 
			setLoaded();
			doInsert(statement);
			statement.addBatch();
			break;
		case DIRTY:
			setLoaded();
			doUpdate(statement);
			statement.addBatch();
			break;
		case DESTROY: 
			doDelete(statement);
			statement.addBatch();
			break;
		default: 
			throw new ItasException(this.getClass().getName() + " unkown data status:" + status);
		}
	}
	
	final void doResultSet(ResultSet result) throws SQLException {
		doFill(result);
		this.status = DataStatus.LOADED;
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
	
	protected void doALter(Statement statement) {
		throw new ItasException("doALter must Override");
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

	protected GameBase clone(String Id) {
		throw new ItasException("clone(String Id) must Override");
	}
	
	@Override
	public void writeExternal(ObjectOutput out) throws IOException {
	}

	@Override
	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		
	}
	
}
