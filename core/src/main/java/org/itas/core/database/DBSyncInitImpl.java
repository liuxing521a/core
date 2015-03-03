package org.itas.core.database;


import java.util.Collection;

import org.itas.core.DBSync;
import org.itas.core.GameObject;
import org.itas.core.GameServerInit.DBSyncInit;

import com.typesafe.config.Config;

/**
 * <p>脏数据</p>
 * 需要同步数据库的数据堆积
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014-3-17
 */
public final class DBSyncInitImpl implements DBSyncInit {
	
	public DBSyncInitImpl() {
	}
	
	@Override
	public void onStartup(Config dataBase, Collection<GameObject> modules) throws Exception {
		DBPool.PRIVODER.initialized(dataBase);
		
		DBSync sync = new DBSyncImpl();
		for (GameObject gameObject : modules) {
			sync.createTable(gameObject);
			sync.alterTable(gameObject);
			sync.bind(gameObject);
		}
		
		DBSyncThread.PRIVODER.setSync(sync);
		DBSyncThread.PRIVODER.startThread();
	}

	@Override
	public void onDestroyed() throws Exception {
		DBSyncThread.PRIVODER.stopThread();
	}
}
