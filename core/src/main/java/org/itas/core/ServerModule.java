package org.itas.core;

import org.itas.core.Pool.DBPool;
import org.itas.core.Pool.DataPool;
import org.itas.core.Pool.ResPool;
import org.itas.core.database.DataBaseManager;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

final class ServerModule implements Module {

  ServerModule() {
  }
  
  @Override
  public void configure(Binder binder) {
	final Config config = ConfigFactory.load("application.conf");
	
	final Config dbConfig = config.getConfig("mainDB");
	final DBPool dbPool  = DataBaseManager.makdDBPoolBuilder()
		.setConfig(dbConfig).builder();
		
	final DBSync dbSync = DataBaseManager.makeDBSyncBuilder()
		.setDBPool(dbPool).builder();
		
	final Config sharedConfig = config.getConfig("shared");
	final DBSyncService syncService = DataBaseManager.makdDBSyncThreadBuilder()
		.setSync(dbSync).setInterval(sharedConfig.getLong("interval"))
		.builder();
	
	final DataPool dataPool = DataPoolImpl.makeBuilder()
		.setShared(sharedConfig).setDbSync(dbSync).builder();
	
	final ResPool resPool = ResPoolImpl.makeBuilder().builder();
	
	binder.bind(DBSync.class).toInstance(dbSync);
    binder.bind(DBPool.class).toInstance(dbPool);
    binder.bind(DBSyncService.class).toInstance(syncService);

    binder.bind(DataPool.class).toInstance(dataPool);
    binder.bind(ResPool.class).toInstance(resPool);
    
    binder.bind(Config.class).toInstance(config);
  }
	
}
