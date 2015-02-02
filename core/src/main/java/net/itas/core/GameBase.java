package net.itas.core;

import java.io.Externalizable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.itas.core.annotation.UnSave;
import net.itas.util.ItasException;

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
			insertStatement(statement);
			statement.addBatch();
			break;
		case DIRTY:
			setLoaded();
			updateStatement(statement);
			statement.addBatch();
			break;
		case DESTROY: 
			deleteStatement(statement);
			statement.addBatch();
			break;
		default: 
			throw new ItasException(this.getClass().getName() + " unkown data status:" + status);
		}
	}
	
	final void doResultSet(ResultSet result) throws SQLException {
		fillData(result);
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
	
	protected void insertStatement(PreparedStatement statement) throws java.sql.SQLException {
		throw new ItasException("insertStatement must Override");
	}

	protected void updateStatement(PreparedStatement statement) throws java.sql.SQLException {
		throw new ItasException("updateStatement must Override");
	}
	
	protected void deleteStatement(PreparedStatement statement) throws java.sql.SQLException {
		throw new ItasException("deleteStatement must Override");
	}
	
	protected void fillData(ResultSet result) throws java.sql.SQLException {
		throw new ItasException("fillData must Override");
	}

	protected GameBase clone(String Id) {
		throw new ItasException("clone(String Id) must Override");
	}
	
}
