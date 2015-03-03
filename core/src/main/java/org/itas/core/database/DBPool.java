package org.itas.core.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.typesafe.config.Config;

public final class DBPool {
	
	static DBPool PRIVODER = new DBPool();

	/** 主逻辑连接*/
	private DataSource dataSource;
	
	
	void initialized(Config config) throws Exception {
		dataSource = new DataSource();
		dataSource.initialize(config);
	}
	
	public static Connection getConnection() throws SQLException {
		return PRIVODER.dataSource.getConnection();
	}
	
	public static void shutdown() {
		if (PRIVODER.dataSource != null) {
			PRIVODER.dataSource.shutdown();
		}
	}
	
	private DBPool() {
	}
	
}
