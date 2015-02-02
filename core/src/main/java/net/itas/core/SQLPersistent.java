//package net.itas.core;
//
//import java.sql.SQLException;
//import java.util.Collection;
//
//import net.itas.util.Logger;
//import net.itas.util.Utils.TimeUtil;
//
//import com.typesafe.config.Config;
//
///**
// * <p>环写数据库</p>
// * @author liuzhen<liuxing521a@163.com>
// * @date 2014-3-17
// */
//public abstract class SQLPersistent extends Thread {
//	
//	/**
//	 * 单例
//	 */
//	private static SQLPersistent instance = new SQLPersistent(){};
//	
//	/** 对象池*/
//	private final DataPool dataPool;
//	
//	/** sql工具*/
//	private final SQLDirtys sQLTools;
//	
//	/**上次同步数据库时间*/
//	private long updateAt;
//	/**线程运行标记*/
//	private boolean isFlag;
//	/**同步间隔时间*/
//	private long interval;
//
//	private SQLPersistent() {
//        super("persistent DB");
//        this.setDaemon(true);
//        this.dataPool = Factory.getInstance(DataPool.class);
//        this.sQLTools = Factory.getInstance(SQLDirtys.class);
//	}
//	
//	public static SQLPersistent getInstance() {
//		return instance;
//	}
//	
//	public void initialize(Config config) {
//		this.interval = config.getLong("interval")*1000L;
//		startThread();
//	}
//	
//	public void bindChirld(Config config, String pack) throws Exception {
//		dataPool.bind(config, pack);
//	}
//	
//	public void createTables() throws Exception {
//		Collection<GameBase> games = dataPool.getModelList();
//		for (GameBase model : games) {
//			sQLTools.createTable(model);
//		}
//	}
//	
//	public void alterTables() throws Exception {
//		Collection<GameBase> games = dataPool.getModelList();
//		for (GameBase model : games) {
//			sQLTools.alterTable(model);
//		}
//	}
//	
//	public void destoryed() {
//		dataPool.destoryed();
//		stopThread();
//	}
//
//    void startThread() {
//    	if (this.isAlive()) {
//    		throw new RuntimeException( "thread is alive" );
//    	}
//    	
//        this.isFlag = false;
//    	this.start();
//    }
//
//    void stopThread() {
//        this.isFlag = true;
//    }
//
//	@Override
//	public void run() {
//		
//        while (!isFlag) {
//            try {
//				if (isPersistent()) {
//					doPersistent();
//				}
//			} catch (Throwable e) {
//				Logger.error("", e);
//			}
//            
//            synchronized (this) {
//            	try {
//					Thread.sleep(interval);
//				} catch (InterruptedException e) {
//				}
//			}
//        }
//	}
//	
//	public void doPersistent() throws SQLException {
//		sQLTools.doPersistent();
//		updateAt = TimeUtil.systemTime();
//	}
//	
//	private boolean isPersistent() {
//		return TimeUtil.systemTime() - updateAt > interval;
//	}
//
//}
