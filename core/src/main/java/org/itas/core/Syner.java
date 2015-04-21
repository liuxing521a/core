package org.itas.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.itas.core.GameBase.DataStatus;
import org.itas.core.SQLExecutor.Called;
import org.itas.core.SQLExecutor.RowMapper;
import org.itas.core.util.AutoClose;
import org.itas.util.Utils.Objects;
import org.itas.util.Utils.TimeUtil;
import org.itas.util.collection.CircularQueue;

public abstract class Syner implements Runnable {

	private static GameBaseSyner syner;
	
	static {
		syner = Ioc.getInstance(GameBaseSyner.class);
	}
	
	protected Syner(long interval) {
		this.interval = interval;
		this.lastTime = TimeUtil.systemTime();
	}
	
	/** 上次同步数据库时间  */
	private long lastTime;
	
	/** 同步间隔时间  */
	private final long interval;
	
	/**
	 * 做同步数据库操作，只有当当前时间和上次同步时间比较大于间隔时间才会同步
	 * @return 同步间隔时间
	 * @throws Exception
	 */
	public long doPersistent() throws Exception {
		if (isPresistentAble()) {
			syner.doPersistent();
			lastTime = TimeUtil.systemTime();
		}
		
		return interval;
	}

	/**
	 * 强制同步数据库
	 * @throws Exception
	 */
	public void forcePersistent() throws Exception {
		syner.doPersistent();
	}
	
	private boolean isPresistentAble() {
		return TimeUtil.systemTime() - lastTime > interval;
	}
	
	abstract static class GameBaseSyner {
		
		abstract void addInsert(GameBase GameBase);
		
		abstract void addUpdate(GameBase GameBase);
		
		abstract void addDelete(GameBase GameBase);
		
		abstract void doPersistent() throws Exception;
	}
  
	abstract static class DataBaseSyner {
  	
  	abstract void loadData(GameBase GameBase) throws Exception;
  	
  	abstract void loadData(Map<String, GameBase> GameBases)throws Exception;
  	
  	abstract void insertData(CircularQueue<GameBase> GameBases)throws Exception;
  	
  	abstract void modifyData(CircularQueue<GameBase> GameBases)throws Exception;
  	
  	abstract void deleteData(CircularQueue<GameBase> GameBases)throws Exception;
  	
  	abstract void createTable(List<GameBase> GameBases)throws Exception;
  	
  	abstract void alterTable(List<GameBase> GameBases) throws Exception;
  }
	
  private static class GameBaseSynerImpl extends GameBaseSyner implements Ioc {

  	private static DataBaseSyner syner;
  	
  	static {
  		syner = Ioc.getInstance(DataBaseSyner.class);
  	}
  	
    private static Map<Class<?>, CircularQueue<GameBase>> insertDatas;
    private static Map<Class<?>, CircularQueue<GameBase>> updateDatas;
    private static Map<Class<?>, CircularQueue<GameBase>> deleteDatas;
  	
  	@Override
    public void addInsert(GameBase GameBase) {
  		CircularQueue<GameBase> items = insertDatas.get(GameBase.getClass());
     	synchronized (items) {
     		items.push(GameBase);
     	}
    }

  	@Override
  	public void addUpdate(GameBase GameBase) {
    	CircularQueue<GameBase> items = updateDatas.get(GameBase.getClass());
  		synchronized (items) {
  			items.push(GameBase);
  		}
    }
  	
  	@Override
  	public void addDelete(GameBase GameBase) {
    	CircularQueue<GameBase> items = updateDatas.get(GameBase.getClass());
   		synchronized (items) {
     			items.push(GameBase);
     	}
    }
  	
    public void doPersistent() throws Exception {
	  	for (CircularQueue<GameBase> GameBases : insertDatas.values()) {
	  	  syner.insertData(GameBases);
	  	}
	
	  	for (CircularQueue<GameBase> GameBases : updateDatas.values()) {
	  	  syner.modifyData(GameBases);
	  	}
	
	  	for (CircularQueue<GameBase> GameBases : deleteDatas.values()) {
	  		syner.deleteData(GameBases);
	  	}
    }

  }

  private static class DataBaseSynerImpl extends DataBaseSyner implements AutoClose, Ioc {

  	static SQLExecutor excuter;
  	
  	static {
  		excuter = Ioc.getInstance(SQLExecutor.class);
  	}
  	
		@Override
		public void loadData(GameBase GameBase) throws Exception {
			excuter.queryForObject(
					new RowMapper<GameBase>() {
						@Override
						public GameBase mapRow(ResultSet rs) throws SQLException {
							GameBase.doFill(rs);
							return GameBase;
						}
					}, 
					
					new Called<PreparedStatement, Connection>() {
						@Override
						public PreparedStatement called(Connection conn) throws Exception {
							PreparedStatement ppst = conn.prepareStatement(GameBase.selectSQL());
							ppst.setString(1, GameBase.getId());
							return ppst;
						}
					});
		}
			
	  @Override// TODO
	  public void loadData(Map<String, GameBase> GameBases) throws Exception {
	  	if (Objects.nonEmpty(GameBases)) {
	  		excuter.queryForArray(
  				new RowMapper<GameBase>() {
  					@Override
  					public GameBase mapRow(ResultSet rs) throws SQLException {
  						GameBase GameBase = GameBases.get(rs.getString("Id"));
							GameBase.doFill(rs);
		  						
  						return GameBase;
  					}
  				}, 
		  				
  				new Called<PreparedStatement, Connection>() {
  					@Override
  					public PreparedStatement called(Connection conn) {
//		  						PreparedStatement ppst = conn.prepareStatement(buffer.toString());
//		  						ppst.setString(1, GameBase.getId());
  						return null;
  					}
  				});
	  	}
  }
			
  @Override
  public void insertData(CircularQueue<GameBase> GameBases) throws Exception {
  	if (!GameBases.isEmpty()) {
  		final int size = GameBases.size();
  		excuter.executeBatch(
  				new Called<PreparedStatement, Connection>() {
  					@Override
  					public PreparedStatement called(Connection conn) throws Exception {
  						final PreparedStatement ppst = 
  								conn.prepareStatement(GameBases.peek().insertSQL());
	  							
  						GameBase data;
  						for (int i = 0; i < size; i++) {
  							synchronized (GameBases) {
  								data = GameBases.pop();
								}
	  								
  							if (data.getDataStatus() == DataStatus.news) {
									data.doStatement(ppst, DataStatus.news);
  							}
	  								
  							if ((i & 0xFF) == 0xFF) {
  								ppst.executeBatch();// 每255个执行一次,避免一次性太多造成性能下降
  							}
  						}
	  							
  						return ppst;
  					}
  				});
  	}
  }

	  @Override
	  public void modifyData(CircularQueue<GameBase> GameBases) throws Exception {
	  	if (!GameBases.isEmpty()) {
	  		final int size = GameBases.size();
	  			excuter.executeBatch(
	  					new Called<PreparedStatement, Connection>(){
	  						@Override
	  						public PreparedStatement called(Connection conn) throws Exception {
	  							final PreparedStatement ppst = 
	  									conn.prepareStatement(GameBases.peek().updateSQL());
	  							
	  							GameBase data;
	  							for (int i = 0; i < size; i++) {
	  								synchronized (GameBases) {
	  									data = GameBases.pop();
	  								}
	  								
	  								if (data.getDataStatus() == DataStatus.modify) {
	  									data.doStatement(ppst, DataStatus.modify);
	  								}
	  								
	  								if ((i & 0xFF) == 0xFF) {
	  									ppst.executeBatch();// 每255个执行一次,避免一次性太多造成性能下降
	  								}
	  							}
	  							
	  							return ppst;
	  						}
	  					});
  		}
  	}
			
	  @Override
		  public void deleteData(CircularQueue<GameBase> GameBases) throws Exception {
	  	if (!GameBases.isEmpty()) {
	  		final int size = GameBases.size();
	  			excuter.executeBatch(
	  					new Called<PreparedStatement, Connection>(){
	  						@Override
	  						public PreparedStatement called(Connection conn) throws Exception  {
	  							final PreparedStatement ppst = 
	  									conn.prepareStatement(GameBases.peek().updateSQL());
	  							
	  							GameBase data;
	  							for (int i = 0; i < size; i++) {
	  								synchronized (GameBases) {
	  									data = GameBases.pop();
	  								}
	  								
	  								if (data.getDataStatus() == DataStatus.destory) {
	  									data.doStatement(ppst, DataStatus.destory);
	  								}
	  								
	  								if ((i & 0xFF) == 0xFF) {
	  									ppst.executeBatch();// 每255个执行一次,避免一次性太多造成性能下降
	  								}
	  							}
	  							
	  							return ppst;
	  						}
	  					});
  		}
	  }
			
	  @Override
	  public void createTable(List<GameBase> GameBases) throws Exception {
	  	if (Objects.nonEmpty(GameBases)) {
	  		excuter.execute(
  				new Called<PreparedStatement, Connection>() {
						@Override
						public PreparedStatement called(Connection conn) throws Exception {
							PreparedStatement statement = conn.prepareStatement("");
							for (GameBase GameBase : GameBases) {
				        GameBase.doCreate(statement);
				      }
							return statement;
						}
					});
	  	}
	  }

	  @Override
	  public void alterTable(List<GameBase> GameBases) throws Exception {
	  	if (Objects.nonEmpty(GameBases)) {
	  		excuter.execute(
  				new Called<PreparedStatement, Connection>() {
						@Override
						public PreparedStatement called(Connection conn) throws Exception {
							final PreparedStatement statement = conn.prepareStatement("");
							Set<String> clumns;
							for (GameBase GameBase : GameBases) {
						    clumns = excuter.tableColumns(GameBase.tableName());
						    GameBase.doAlter(statement, clumns);
				      }
							
							return statement;
						}
					});
	  	}
	  }
  }
  
}