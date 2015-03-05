package org.itas.core;

import java.util.List;

import org.itas.core.bytecode.ByteCodes;
import org.itas.core.database.DataBases;
import org.itas.util.ItasException;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Module;
import com.typesafe.config.Config;

public abstract class ServerInit implements Service {

  protected enum ConfigType { dataBase, shared }
	
  protected ServerInit() {
  }
	
  @Override
  public final synchronized void startUP() throws Exception {
	final DBPool dbPool  = DataBases.makdDBPoolBuilder()
		.setConfig(config(ConfigType.dataBase)).builder();
		
	final DBSync dbSync = DataBases.makeDBSyncBuilder()
		.setDBPool(dbPool).builder();
		
	final Service service = DataBases.makdDBSyncThreadBuilder()
		.setSync(dbSync).setInterval(config(ConfigType.shared)
		.getLong("interval")).builder();
	service.startUP();
	
	final DataPool dataPool = DataPool.makeBuilder()
		.setShared(config(ConfigType.shared)).builder();
	
	final Module module = new Module() {
	  @Override
	  public void configure(Binder binder) {
		try {
		  binder.bind(DBPool.class).toInstance(dbPool);
		  binder.bind(DBSync.class).toInstance(dbSync);
		  binder.bind(DataPool.class).toInstance(dataPool);
		  onStartup(binder);
		} catch (Exception e) {
		  throw new ItasException(e);
		}
	  }
	};
	Guice.createInjector(module);
	
	final List<Class<?>> classList = 
	    ByteCodes.loadClass(GameObject.class, "com.godwan.duobao.model");
	
	GameObject gameObject;
	for (final Class<?> clazz : classList) {
	  gameObject = dataPool.bind(clazz);
	  dbSync.bind(gameObject);
	  dbSync.createTable(gameObject);
	  dbSync.alterTable(gameObject);
	}
  }
	
  @Override
  public final synchronized void shutDown() throws Exception {
	onDestroyed();
  }
	
  /**
   * 服务启动初始化
   * @throws Exception
   */
  protected abstract void onStartup(Binder binder) throws Exception;
	
  /**
   * 服务销毁处理
   * @throws Exception
   */
  protected abstract void onDestroyed() throws Exception;
	
  /**
   * 服务初始化设置
   * @return
   */
  protected abstract Config config(ConfigType type);
	
}
