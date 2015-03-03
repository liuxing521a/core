package org.itas.core.util;

public class SQLUtils {

	public static void close(AutoCloseable...autos) {
		for (AutoCloseable auto : autos) {
			try {
				auto.close();
			} catch (Exception e) {
				// do nothing
			}
		}
	}
	
	private SQLUtils(){
		
	}
}
