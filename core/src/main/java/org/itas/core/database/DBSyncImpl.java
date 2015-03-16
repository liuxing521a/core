package org.itas.core.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.itas.core.AbstractDBSync;
import org.itas.core.Binding;
import org.itas.core.Builder;
import org.itas.core.DBSync;
import org.itas.core.GameObject;
import org.itas.core.IllnessException;
import org.itas.core.Pool.DBPool;
import org.itas.util.collection.CircularQueue;

class DBSyncImpl extends AbstractDBSync implements Binding {

  private final DBPool dbPool;
  private static Map<Class<?>, CircularQueue<GameObject>> insertDatas;
  private static Map<Class<?>, CircularQueue<GameObject>> updateDatas;
  private static Map<Class<?>, CircularQueue<GameObject>> deleteDatas;
	
  DBSyncImpl(DBPool dbPool) {
	this.dbPool = dbPool;
  }

  @Override
  public void bind(Called back) {
	checkInitialized();
	final List<GameObject> gameObjects = back.callBack();
	final Map<Class<?>, CircularQueue<GameObject>> insertMap = 
			new HashMap<>(gameObjects.size());
	final Map<Class<?>, CircularQueue<GameObject>> updateMap = 
			new HashMap<>(gameObjects.size());
	final Map<Class<?>, CircularQueue<GameObject>> deleteMap = 
	    new HashMap<>(gameObjects.size());
	
	for (final GameObject gameObject : gameObjects) {
	  insertMap.put(gameObject.getClass(), new CircularQueue<>());
	  updateMap.put(gameObject.getClass(), new CircularQueue<>());
	  deleteMap.put(gameObject.getClass(), new CircularQueue<>());
	}
	
	insertDatas = Collections.unmodifiableMap(insertMap);
	updateDatas = Collections.unmodifiableMap(updateMap);
	deleteDatas = Collections.unmodifiableMap(deleteMap);
  }
  
  @Override
  public void unBind() {
	insertDatas = null;
	updateDatas = null;
	deleteDatas = null;
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
  
  private void checkInitialized() {
	if (insertDatas != null || updateDatas != null || deleteDatas != null) {
	  throw new IllnessException("can't init agin");
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
