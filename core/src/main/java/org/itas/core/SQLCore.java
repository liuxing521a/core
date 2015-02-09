package org.itas.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.itas.core.GameBase.DataStatus;
import org.itas.util.ItasException;
import org.itas.util.Logger;
import org.itas.util.Utils.SQLUtils;
import org.itas.util.collection.CircularQueue;

import javassist.CtField;
import javassist.Modifier;
import net.itas.core.annotation.Key;
import net.itas.core.annotation.Size;
import net.itas.core.annotation.UnSave;
import net.itas.core.jdbc.DBPool;
import net.itas.core.jdbc.JdbcType;

abstract class SQLCore {

	/**
	 * <p>加载数据 </p>
	 * @param module 模型数据
	 * @param Id 加载的数据Id
	 * @return 加载对象
	 */
	GameBase loadData(GameBase module, String Id) {
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet result = null;
		try {
			conn = DBPool.getConnection();
			ppst = conn.prepareStatement(module.getSQL(DataStatus.LOADED));
			ppst.setString(1, Id);
			result = ppst.executeQuery();

			GameBase data = null;
			if (result.next()) {
				data = module.clone(Id);
				data.fillData(result);
			}

			return data;
		} catch (Exception e) {
			Logger.error("[clazz=" + module.getClass().getName() + "],[key=" + Id + "]", e);
			return null;
		} finally {
			SQLUtils.close(conn, ppst, result);
		}
	}
	
	/**
	 * <p>加载数据 </p>
	 * @param module 模型数据
	 * @param Id 加载的数据Id
	 * @return 加载对象列表
	 */
	List<GameBase> loadDataArray(GameBase module, Collection<String> IdArray) {
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet result = null;
		try {
			conn = DBPool.getConnection();
			ppst = conn.prepareStatement(module.getSQL(DataStatus.LOADEDARRAY));
			
			int index = 1;
			for (String Id : IdArray) {
				ppst.setString(index, Id);
				index ++;
			}
			result = ppst.executeQuery();

			List<GameBase> dataList = new ArrayList<>(IdArray.size());
			GameBase data;
			while (result.next()) {
				data = module.clone(result.getString("Id"));
				data.fillData(result);
				dataList.add(data);
			}

			return dataList;
		} catch (Exception e) {
			Logger.error("[clazz=" + module.getClass().getName() + "],[key=" + String.join(", ", IdArray) + "]", e);
			return null;
		} finally {
			SQLUtils.close(conn, ppst, result);
		}
	}
	
	/**
	 * 插入数据库
	 * @param dataArray 要插入数据列表
	 */
	void insertData(CircularQueue<GameBase> newDatas) {
		int size = newDatas.size();
		if (size <= 0) {
			return;
		}
		
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = DBPool.getConnection();
			statement = conn.prepareStatement(newDatas.peek().getSQL(DataStatus.NEW));

			GameBase data;
			for (; size > 0; size--) {
				synchronized (newDatas) {
					data = newDatas.pop();
				}
				
				if (data.getDataStatus() == DataStatus.NEW) {
					data.doStatement(statement, DataStatus.NEW);
				}
			}

			statement.executeBatch();
		} catch (Exception e) {
			Logger.error("", e);
		} finally {
			SQLUtils.close(conn, statement);
		}
	}

	
	/**
	 * 修改数据库
	 * @param modifyDatas 要修改数据列表
	 */
	void modifyData(CircularQueue<GameBase> modifyDatas) {
		int size = modifyDatas.size();
		if (size <= 0) {
			return;
		}
		
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = DBPool.getConnection();
			statement = conn.prepareStatement(modifyDatas.peek().getSQL(DataStatus.DIRTY));

			GameBase data;
			for (; size > 0; size--) {
				synchronized (modifyDatas) {
					data = modifyDatas.pop();
				}
				
				if (data.getDataStatus() == DataStatus.DIRTY) {
					data.doStatement(statement, DataStatus.DIRTY);
				}
			}

			statement.executeBatch();
		} catch (Exception e) {
			Logger.error("", e);
		} finally {
			SQLUtils.close(conn, statement);
		}
	}
	
	/**
	 * 删除数据库
	 * @param deletDatas 要修改数据列表
	 */
	void deleteData(CircularQueue<GameBase> deletDatas) {
		int size = deletDatas.size();
		if (size <= 0) {
			return;
		}
		
		Connection conn = null;
		PreparedStatement statement = null;
		try {
			conn = DBPool.getConnection();
			statement = conn.prepareStatement(deletDatas.peek().getSQL(DataStatus.DESTROY));

			GameBase data;
			for (; size > 0; size--) {
				synchronized (deletDatas) {
					data = deletDatas.pop();
				}
				
				if (data.getDataStatus() == DataStatus.DESTROY) {
					data.doStatement(statement, DataStatus.DESTROY);
				}
			}

			statement.executeBatch();
		} catch (Exception e) {
			Logger.error("", e);
		} finally {
			SQLUtils.close(conn, statement);
		}
	}
	
	// =====================================================================
	// 数据库创建或修改
	// =====================================================================
	/**
	 * <p>创建数据库</p>
	 * @param clazz 要创建的模型
	 * @return 返回创建结果
	 * @throws Exception
	 */
	int createTable(GameBase data, List<CtField> fields) {
		Connection conn = null;
		PreparedStatement ppst = null;
		try {
			conn = DBPool.getConnection();
			ppst = conn.prepareStatement(createTableSQL(data, fields));
			
			return ppst.executeUpdate();
		} catch (Exception e) {
			throw new ItasException(e);
		} finally {
			SQLUtils.close(conn, ppst);
		}
	}

	/** 修改表 */
	int[] alterTable(GameBase data, List<CtField> fields) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = DBPool.getConnection();
			stmt = conn.createStatement();

			StringBuffer buffer;
			
			Set<String> columns = getColumnNames(data.tableName());
			for (CtField field : fields) {
				if (isUnSave(field) || columns.contains(field.getName())) {
					continue;
				}

				buffer = new StringBuffer();
				buffer.append("ALTER TABLE `");
				buffer.append(data.tableName());
				buffer.append("` ADD ");
				buffer.append(JdbcType.columnSQL(field, (Size)field.getAnnotation(Size.class)));
				buffer.append(";");
				
				stmt.addBatch(buffer.toString());
			}

			return stmt.executeBatch();
		} catch (Exception e) {
			throw new ItasException(e);
		} finally {
			SQLUtils.close(conn, stmt);
		}
	}

	private static Set<String> getColumnNames(String tableName) throws Exception {
		Connection conn = null;
		PreparedStatement ppst = null;
		ResultSet rs = null;
		try {
			conn = DBPool.getConnection();
			String sql = "SELECT column_name FROM information_schema.columns WHERE table_name=?;";
			ppst = conn.prepareStatement(sql);
			ppst.setString(1, tableName);
			rs = ppst.executeQuery();

			Set<String> columns = new HashSet<String>();
			while (rs.next()) {
				columns.add(rs.getString("column_name"));
			}

			return columns;
		} finally {
			SQLUtils.close(conn, ppst, rs);
		}
	}

	private String createTableSQL(GameBase data, List<CtField> fields) throws Exception {
		StringBuffer sql = new StringBuffer();
		
		sql.append("CREATE TABLE IF NOT EXISTS `").append(data.tableName());
		sql.append("`\r\n(");
		sql.append("\t`Id` VARCHAR(35)  DEFAULT '',");

		List<String> keys = new ArrayList<String>(4);
		for (CtField field : fields) {
			if (isUnSave(field)) {
				continue;
			}

			sql.append("\n\t");
			sql.append(JdbcType.columnSQL(field, (Size)field.getAnnotation(Size.class)));

			if (field.hasAnnotation(Key.class)) {
				keys.add(field.getName());
			}

			sql.append(",");
		}

		sql.append("\n\t`updateTime` TIMESTAMP DEFAULT '2013-01-01 00:00:00',\r\n");
		sql.append("\t`createTime` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,\r\n");
		sql.append("\tPRIMARY KEY  (`Id`)");

		for (String key : keys) {
			sql.append(",\r\n\tKEY `").append(key).append("` (`").append(key).append("`)");
		}
		sql.append("\r\n)ENGINE=MyISAM DEFAULT CHARSET=utf8;");

		Logger.trace("{}", sql.toString());
		return sql.toString();
	}


	// =========================================================================
	// 工具方法
	// =========================================================================
	private boolean isUnSave(CtField field) {
		if (field.getName().equals("Id")) {
			return false;
		}
		
		if (Modifier.isStatic(field.getModifiers())) {
			return true;
		} 
		
		if (field.hasAnnotation(UnSave.class)) {
			return true;
		}
		
		if (Modifier.isFinal(field.getModifiers())) {
			return true;
		}
		
		return false;
	}

	protected SQLCore() {
	}

}