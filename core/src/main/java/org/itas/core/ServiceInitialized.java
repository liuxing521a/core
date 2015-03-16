package org.itas.core;

import org.itas.core.Pool.DBPool;
import org.itas.util.Logger;

public abstract class ServiceInitialized implements Ioc, Runnable {

  public void startUP() throws Exception {
//    final DBPool dbPool = Ioc.getInstance(DBPool.class);
//    dbPool.onInitialized();
    
    onInitilaized();

    final DBSyncService dbService = Ioc.getInstance(DBSyncService.class);
    dbService.onInitialized();
    
    Runtime.getRuntime().addShutdownHook(new Thread(this));
  }
  
  void shutdown() throws Exception {
    final DBPool dbPool = Ioc.getInstance(DBPool.class);
    dbPool.shutdown();
    
    final DBSyncService dbService = Ioc.getInstance(DBSyncService.class);
    dbService.onDestroyed();
	    
    onDestory(); 
  }
	
  
  public abstract void onInitilaized() throws Exception;
  
  public abstract void onDestory() throws Exception;
  
  @Override
  public final void run() {
	try {
	  shutdown();
	} catch (Exception e) {
	  Logger.error("", e);
	}
  }
  
}
