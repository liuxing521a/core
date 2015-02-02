package net.itas.core.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import com.typesafe.config.Config;

public final class DBPool {

	/** 主逻辑连接*/
	private DataSource connection;
	
	private static DBPool instance = new DBPool();
	
	public static DBPool getInstance() {
		return instance;
	}
	
	public void initMainDB(Config config) throws Exception {
		connection = new DataSource();
		connection.initialize(config);
	}
	
	public void initLogDB(Config config) throws Exception {
		connection = new DataSource();
		connection.initialize(config);
	}
	
	public static Connection getConnection() throws SQLException {
		return getInstance().connection.getConnection();
	}
	
	public void shutdown() {
		if (connection != null) {
			connection.shutdown();
		}
	}
	
	private DBPool() {
	}
	
}
