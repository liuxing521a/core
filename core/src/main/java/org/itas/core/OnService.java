package org.itas.core;

public interface OnService {

  /**
   * 初始化
   * @param bind 绑定参数
   * @throws Exception
   */
  abstract void setUP(Called...back);
  
  /**
   * 销毁
   * @throws Exception
   */
  abstract void destoried();
  
	
  interface Called {
	  
	abstract <T> T callBack();
	
  }
}
