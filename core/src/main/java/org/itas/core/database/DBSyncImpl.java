package org.itas.core.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.itas.core.AbstractDBSync;
import org.itas.core.Builder;
import org.itas.core.DBSync;
import org.itas.core.GameObject;
import org.itas.core.Pool.DBPool;
import org.itas.util.collection.CircularQueue;

class DBSyncImpl extends AbstractDBSync {

  private final DBPool dbPool;
  private final Map<Class<?>, CircularQueue<GameObject>> insertDatas;
  private final Map<Class<?>, CircularQueue<GameObject>> updateDatas;
  private final Map<Class<?>, CircularQueue<GameObject>> deleteDatas;
	
  DBSyncImpl(DBPool dbPool) {
	this.dbPool = dbPool;
	this.insertDatas = new HashMap<>();
	this.updateDatas = new HashMap<>();
	this.deleteDatas = new HashMap<>();
  }

  @Override
  public void setUP(Called...back) {
	final Class<?> clazz = back[0].callBack();
	insertDatas.put(clazz, new CircularQueue<>());
	updateDatas.put(clazz, new CircularQueue<>());
	deleteDatas.put(clazz, new CircularQueue<>());
  }
  
  @Override
  public void destoried() {
	insertDatas.clear();
	updateDatas.clear();
	deleteDatas.clear();
  }
	
  @Override
  protected Connection getConnection() throws SQLException {
	return dbPool.getConnection();
  }
	
  protected void addInsert(GameObject gameObject) {
	CircularQueue<GameObject> items = insertDatas.get(gameObject.getClass());
   	synchronized (items) {
	  items.push(gameObject);
	}
  }

  protected void addUpdate(GameObject gameObject) {
	CircularQueue<GameObject> items = updateDatas.get(gameObject.getClass());
	synchronized (items) {
	  items.push(gameObject);
	}
  }
	
  protected void addDelete(GameObject gameObject) {
	CircularQueue<GameObject> items = updateDatas.get(gameObject.getClass());
   	synchronized (items) {
	  items.push(gameObject);
	}
  }
	
  public void doPersistent() {
	for (CircularQueue<GameObject> gameObjects : insertDatas.values()) {
	  insertData(gameObjects);
	}

	for (CircularQueue<GameObject> gameObjects : updateDatas.values()) {
	  modifyData(gameObjects);
	}

	for (CircularQueue<GameObject> gameObjects : deleteDatas.values()) {
	  deleteData(gameObjects);
	}
  }
  
  public static class DBSyncBuilder implements Builder {
	  
	private DBPool dbPool;
	  
	public DBSyncBuilder() {
	}
	
	public DBSyncBuilder setDBPool(DBPool dbPool) {
	  this.dbPool = dbPool;
	  return this;
	}
	
	@Override
	public DBSync builder() {
		return new DBSyncImpl(this.dbPool);
	}
  }

}
