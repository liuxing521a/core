package org.itas.core;

public interface Binding extends Ioc {

  abstract void bind(Called call) throws Exception;
  
  abstract void unBind() throws Exception;
  
  interface Called {
	  
	abstract <T> T callBack();
	
  }
	
}
