package org.itas.core.database;

import java.sql.SQLException;

import org.itas.core.DBSync;
import org.itas.util.Logger;
import org.itas.util.Utils.TimeUtil;

/**
 * <p>环写数据库线程</p>
 * @author liuzhen<liuxing521a@163.com>
 * @date 2014-3-17
 */
final class DBSyncThread extends Thread {
	
	static DBSyncThread PRIVODER = new DBSyncThread();
	
	private DBSyncThread() {
        super("persistent DB");
        this.setDaemon(true);
	}
	
	/**
	 * 数据库同步者
	 */
	private DBSync sync;
	
	/**
	 * 线程运行标记
	 */
	private boolean isFlag;
	
	/**
	 * 同步间隔时间
	 */
	private long interval;

	/**
	 * 上次同步数据库时间
	 */
	private long lastUpdateTime;
	
	
	public void setSync(DBSync sync) {
		this.sync = sync;
		this.interval = Long.parseLong(System.getProperty("interval"));
	}

    void startThread() {
    	if (this.isAlive()) {
    		throw new RuntimeException( "thread is alive" );
    	}
    	
        this.isFlag = false;
    	this.start();
    }

    void stopThread() {
        this.isFlag = true;
    }

	@Override
	public void run() {
		
        while (!isFlag) {
            try {
				if (isPersistent()) {
					doPersistent();
				}
			} catch (Throwable e) {
				Logger.error("", e);
			}
            
            synchronized (this) {
            	try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
				}
			}
        }
	}
	
	public void doPersistent() throws SQLException {
		sync.doPersistent();
		lastUpdateTime = TimeUtil.systemTime();
	}
	
	private boolean isPersistent() {
		return TimeUtil.systemTime() - lastUpdateTime > interval;
	}

	DBSync sync() {
		return sync;
	}
}
