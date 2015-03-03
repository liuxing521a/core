package org.itas.core;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.itas.util.collection.CircularQueue;

public interface DBSync {

	/**
	 * <p>根据对象创建数据库表 </p>
	 * @param gameObject 模版对象
	 * @return 创建结果
	 * @throws SQLException
	 */
	abstract int[] createTable(GameObject gameObject) throws SQLException;

	/**
	 * <p>根据对象修改已存在数据库表</p>
	 * @param gameObject 模版对象
	 * @return 修改结果
	 * @throws SQLException
	 */
	abstract int[] alterTable(GameObject gameObject) throws SQLException;
	
	/**
	 * <p>绑定处理对象</p>
	 * @param gameObject
	 */
	abstract void bind(GameObject gameObject);

	/**
	 * <p>获取数据库表字段名</p>
	 * @param tableName 表名
	 * @return 字段列表
	 * @throws SQLException
	 */
	abstract Set<String> tableColumns(String tableName) throws SQLException;
	
	/**
	 * <p>加载数据 </p>
	 * @param gameObject 模型数据
	 * @param Id 加载的数据Id
	 * @return 加载后对像
	 * @throws SQLException
	 */
	abstract GameObject loadData(GameObject gameObject, String Id)throws SQLException;

	/**
	 * <p>加载数据列表 </p>
	 * @param gameObject 模型数据
	 * @param IdArray 加载对性id列表
	 * @return 加载后对象列表
	 * @throws SQLException
	 */
	abstract List<GameObject> loadDataArray(GameObject gameObject, Collection<String> IdArray) throws SQLException;
	
	/**
	 * <p>数据库新增数据 </p>
	 * @param gameObject 新增对象
	 * @throws SQLException
	 */
	abstract void insertData(CircularQueue<GameObject> gameObject)throws SQLException;
	
	/**
	 * <p>修改数据库</p>
	 * @param modifyDatas 要修改对象队列
	 * @throws SQLException
	 */
	abstract void modifyData(CircularQueue<GameObject> modifyDatas) throws SQLException;
	
	/**
	 * <p>删除数据库</p>
	 * @param deletDatas 要删除对象队列
	 * @throws SQLException
	 */
	abstract void deleteData(CircularQueue<GameObject> deletDatas) throws SQLException;
	
	
	abstract void doPersistent();

}
