package org.itas.core.util;

public interface Next {

	static final String NEXT_LINE = System.getProperty("line.separator");
	
	public static String line(int num) {
		final StringBuffer buffer = new StringBuffer();
		
		for (int i = 0; i < num; i++) {
			buffer.append(NEXT_LINE);
		}
		
		return buffer.toString();
	}
	
	public static String table(int num) {
		final StringBuffer buffer = new StringBuffer();
		
		for (int i = 0; i < num; i++) {
			buffer.append('\t');
		}
		
		return buffer.toString();
	}
	
	public static String next(int line, int table) {
		final StringBuffer buffer = new StringBuffer();
		
		for (int i = 0; i < line; i++) {
			buffer.append(NEXT_LINE);
		}

		for (int i = 0; i < table; i++) {
			buffer.append('\t');
		}
		
		return buffer.toString();
	}
	
}
