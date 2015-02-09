package org.itas.core;
//package net.itas.core;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import net.itas.core.GameBase.SQLType;
//import net.itas.util.collection.CircularQueue;
//
///**
// * <p>脏数据</p>
// * 需要同步数据库的数据堆积
// * @author liuzhen<liuxing521a@163.com>
// * @date 2014-3-17
// */
//final class SQLDirtys extends SQLCore {
//	
//	/**
//	 * 插入数据缓存 key->类名, value->插入数据集合
//	 */
//	private final Map<Class<?>, CircularQueue<GameBase>> insertDatas;
//	
//    /**
//     * 修改数据缓存 key->类名, value->修改数据集合
//     */
//	private final Map<Class<?>, CircularQueue<GameBase>> updateDatas;
//   
//	/**
//	 * 删除数据缓存 key->类名, value->删除数据集合
//	 */
//	private final Map<Class<?>, CircularQueue<GameBase>> deleteDatas;
//	
//	private SQLDirtys() {
//		this.insertDatas = new HashMap<Class<?>, CircularQueue<GameBase>>(64);
//		this.updateDatas = new HashMap<Class<?>, CircularQueue<GameBase>>(64);
//		this.deleteDatas = new HashMap<Class<?>, CircularQueue<GameBase>>(64);
//	}
//	
//	
//	public void addInsert(GameBase o) {
//		doAddHandler(o, insertDatas);
//	}
//
//	public void addUpdate(GameBase o) {
//		doAddHandler(o, updateDatas);
//	}
//	
//	public void addDelete(GameBase o) {
//		doAddHandler(o, deleteDatas);
//	}
//	
//	void initDataStruct(Class<?> clazz) {
//		insertDatas.put(clazz, new CircularQueue<GameBase>());
//		updateDatas.put(clazz, new CircularQueue<GameBase>());
//		deleteDatas.put(clazz, new CircularQueue<GameBase>());
//	}
//
//	void doPersistent() {
//		doInsert();
//		doUpdate();
//		doDelete();
//	}
//	
//	private void doInsert() {
//		doBatchHandler(insertDatas.values(), SQLType.insert);
//	}
//	
//	private void doUpdate() {
//		doBatchHandler(updateDatas.values(), SQLType.update);
//	}
//	
//	private void doDelete(){
//		doBatchHandler(deleteDatas.values(), SQLType.delete);
//	}
//	
//	
//	private void doAddHandler(GameBase o, Map<Class<?>, CircularQueue<GameBase>> map) {
//		CircularQueue<GameBase> items = map.get(o.getClass());
//    	
//    	synchronized (items) {
//			items.push(o);
//		}
//	}
//
//	
//}
