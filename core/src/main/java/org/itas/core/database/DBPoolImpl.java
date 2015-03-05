package org.itas.core.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.itas.core.Builder;
import org.itas.core.DBPool;
import org.itas.util.ItasException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.typesafe.config.Config;

final class DBPoolImpl implements DBPool {

  private final DataSource dataSource;
	
  public DBPoolImpl(Config config) {
	dataSource = new DataSource(config);
  }
	
  @Override
  public Connection getConnection() throws SQLException {
	return dataSource.getConnection();
  }
	
  @Override
  public void shutdown() {
	if (dataSource != null) {
	  dataSource.shutdown();
	}
  }
  
  private class DataSource {
		
	/** 连接池*/
	private final BoneCP dbPool;
	
	public DataSource(Config config) {
	  try {
		BoneCPConfig poolConfig = new BoneCPConfig();
		
		poolConfig.setJdbcUrl(config.getString("url"));
		poolConfig.setUsername(config.getString("userName"));
		poolConfig.setPassword(config.getString("password"));
		poolConfig.setAcquireRetryAttempts(Short.MAX_VALUE);
		poolConfig.setPartitionCount(config.getInt("partitionCount"));
		poolConfig.setAcquireIncrement(config.getInt("acquireIncrement"));
		poolConfig.setMinConnectionsPerPartition(config.getInt("minConnPerPart"));
		poolConfig.setMaxConnectionsPerPartition(config.getInt("maxConnPerPart"));
		poolConfig.setPoolAvailabilityThreshold(config.getInt("poolAvailabilityThreshold"));
		poolConfig.setAcquireRetryDelayInMs(8000L);
		poolConfig.setLogStatementsEnabled(true);
		
		dbPool = new BoneCP(poolConfig);
	  } catch (SQLException e) {
		throw new ItasException(e);
	  }
	}
	
	/**
	 * 从连接池中取出一个连接
	 */
	public Connection getConnection() throws SQLException {
	  return dbPool.getConnection();
	}
	
	/**
	 * 关闭连接池
	 */
	public void shutdown()  {
	  if (dbPool != null) {
	  	dbPool.shutdown();
	  }
	}
	
  }
	
  public static class DBPoolBuilder implements Builder {
  
	private Config config;
	  
	public DBPoolBuilder() {
	}
	
	public DBPoolBuilder setConfig(Config config) {
	  this.config = config;
	  return this;
	}
	
	@Override
	public DBPool builder() {
		return new DBPoolImpl(this.config);
	}
  }
	
}
