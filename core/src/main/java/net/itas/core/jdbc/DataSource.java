package net.itas.core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.typesafe.config.Config;

/**
 * <p>数据库连接资源</p>
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014-3-17
 */
final class DataSource {
	
	/** 连接池*/
	private BoneCP dbpool;

	public DataSource() {
	}
    
    public void initialize(Config config) throws Exception {
        BoneCPConfig poolConfig = new BoneCPConfig();
        
        poolConfig.setJdbcUrl( config.getString("url") );
        poolConfig.setUsername( config.getString("userName" ) );
        poolConfig.setPassword( config.getString("password") );
        poolConfig.setAcquireRetryAttempts(Short.MAX_VALUE);
        poolConfig.setPartitionCount(config.getInt("partitionCount"));
        poolConfig.setAcquireIncrement(config.getInt("acquireIncrement"));
        poolConfig.setMinConnectionsPerPartition(config.getInt("minConnPerPart"));
        poolConfig.setMaxConnectionsPerPartition(config.getInt("maxConnPerPart"));
        poolConfig.setPoolAvailabilityThreshold(config.getInt("poolAvailabilityThreshold"));
        poolConfig.setAcquireRetryDelayInMs( 8000L );
        poolConfig.setLogStatementsEnabled( true );

        dbpool = new BoneCP(poolConfig);
    }
    
    /**
     * 从连接池中取出一个连接
     */
    public Connection getConnection() throws SQLException {
        return dbpool.getConnection();
    }

    /**
     * 关闭连接池
     */
    public void shutdown()  {
    	if (dbpool != null) {
    		dbpool.shutdown();
    	}
    }

}
