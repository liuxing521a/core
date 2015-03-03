package org.itas.core.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.itas.core.AbstractDBSync;
import org.itas.core.GameObject;
import org.itas.util.collection.CircularQueue;

class DBSyncImpl extends AbstractDBSync {

	private final Map<Class<?>, CircularQueue<GameObject>> insertDatas;
	private final Map<Class<?>, CircularQueue<GameObject>> updateDatas;
	private final Map<Class<?>, CircularQueue<GameObject>> deleteDatas;
	
	DBSyncImpl() {
		this.insertDatas = new HashMap<>();
		this.updateDatas = new HashMap<>();
		this.deleteDatas = new HashMap<>();
	}
	
	@Override
	public void bind(GameObject gameObject) {
		insertDatas.put(gameObject.getClass(), new CircularQueue<>());
		updateDatas.put(gameObject.getClass(), new CircularQueue<>());
		deleteDatas.put(gameObject.getClass(), new CircularQueue<>());
	}
	
	@Override
	protected Connection getConnection() throws SQLException {
		return DBPool.getConnection();
	}
	
	void addInsert(GameObject gameObject) {
		CircularQueue<GameObject> items = insertDatas.get(gameObject.getClass());
    	
    	synchronized (items) {
			items.push(gameObject);
		}
	}

	void addUpdate(GameObject gameObject) {
		CircularQueue<GameObject> items = updateDatas.get(gameObject.getClass());
    	
    	synchronized (items) {
			items.push(gameObject);
		}
	}
	
	void addDelete(GameObject gameObject) {
		CircularQueue<GameObject> items = updateDatas.get(gameObject.getClass());
    	
    	synchronized (items) {
			items.push(gameObject);
		}
	}
	
	@Override
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

}
