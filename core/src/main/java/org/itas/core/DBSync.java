package org.itas.core;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.itas.util.collection.CircularQueue;

public interface DBSync extends Binding {

  /**
   * <p>根据对象创建数据库表 </p>
   * @param gameObject 模版对象
   * @return 创建结果
   * @throws SQLException
   */
  int[] createTable(List<GameObject> gameObjects) throws SQLException;

  /**
   * <p>根据对象修改已存在数据库表</p>
   * @param gameObject 模版对象
   * @return 修改结果
   * @throws SQLException
   */
  int[] alterTable(List<GameObject> gameObjects) throws SQLException;
  
  /**
   * <p>加载数据 </p>
   * @param gameObject 模型数据
   * @param Id 加载的数据Id
   * @return 加载后对像
   * @throws SQLException
   */
  GameObject loadData(GameObject gameObject, String Id);

  /**
   * <p>加载数据列表 </p>
   * @param gameObject 模型数据
   * @param IdArray 加载对性id列表
   * @return 加载后对象列表
   * @throws SQLException
   */
  List<GameObject> loadDataArray(GameObject gameObject, 
		Collection<String> IdArray) throws SQLException;
	
  /**
   * <p>数据库新增数据 </p>
   * @param gameObject 新增对象
   * @throws SQLException
   */
  void insertData(CircularQueue<GameObject> gameObject)throws SQLException;
	
  /**
   * <p>修改数据库</p>
   * @param modifyDatas 要修改对象队列
   * @throws SQLException
   */
  void modifyData(CircularQueue<GameObject> modifyDatas) throws SQLException;
	
  /**
   * <p>删除数据库</p>
   * @param deletDatas 要删除对象队列
   * @throws SQLException
   */
  void deleteData(CircularQueue<GameObject> deletDatas) throws SQLException;

}
