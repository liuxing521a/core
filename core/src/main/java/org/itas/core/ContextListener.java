package org.itas.core;


public interface ContextListener extends Ioc {
	    
  /**
   * 服务启动初始化
   * @throws Exception
   */
  abstract void startUP() throws Exception;
		
  /**
   * 服务销毁处理
   * @throws Exception
   */
  abstract void shutDown() throws Exception;
}
