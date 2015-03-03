package org.itas.core;

import java.util.Collection;

import org.itas.core.database.DBSyncInitImpl;

import com.typesafe.config.Config;

public abstract class GameServerInit {
	
	private Config config;
	
	private DBSyncInit dbsyncInit;

	private PoolInit poolInit;
	
	protected GameServerInit(Config config) {
		this.config = config;
		this.dbsyncInit = new DBSyncInitImpl();
		this.poolInit = new PoolInitImpl();
	}

	public void onServiceStartup() throws Exception {
		dbsyncInit.onStartup(config, modules);
		
		onStartup();
	}
	
	public void onServiceDestroyed() throws Exception {
		dbsyncInit.onDestroyed();
		
		onDestroyed();
	}
	
	/**
	 * 游戏启动初始化
	 * @throws Exception
	 */
	abstract void onStartup() throws Exception;
	
	/**
	 * 游戏销毁处理
	 * @throws Exception
	 */
	abstract void onDestroyed() throws Exception;
	
	public interface PoolInit {
		/**
		 * 游戏启动初始化
		 * @throws Exception
		 */
		abstract void onStartup(Config share, String pack) throws Exception;
		
		/**
		 * 游戏销毁处理
		 * @throws Exception
		 */
		abstract void onDestroyed() throws Exception;
	}
	
	public interface DBSyncInit {
		
		/**
		 * 游戏启动初始化
		 * @throws Exception
		 */
		abstract void onStartup(Config dataBase, Collection<GameObject> modules) throws Exception;
		
		/**
		 * 游戏销毁处理
		 * @throws Exception
		 */
		abstract void onDestroyed() throws Exception;
	}
}
