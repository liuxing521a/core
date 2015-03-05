package org.itas.core.util;

public interface AutoClose {

  default void close(AutoCloseable...autos) {
	for (AutoCloseable auto : autos) {
	  try {
		auto.close();
	  } catch (Exception e) {
				// do nothing
	  }
	}
  }
	
}
