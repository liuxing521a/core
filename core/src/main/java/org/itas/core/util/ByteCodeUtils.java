package org.itas.core.util;

import javassist.CtClass;
import net.itas.core.annotation.SQLEntity;

import org.itas.util.ItasException;

public interface ByteCodeUtils {

	public static String tableName(CtClass clazz) throws ClassNotFoundException {
		Object sqlEntity = clazz.getAnnotation(SQLEntity.class);
		if (sqlEntity == null) {
			throw new ItasException(clazz.getName() + " module must has annotation[SQLEntity|UnPersistence]");
		}

		return ((SQLEntity)sqlEntity).value();
	}
	
	public static String firstKeyUpCase(String str) {
		StringBuilder buffer = new StringBuilder(str.length());
		
		for (char ch : str.toCharArray()) {
			if (buffer.length() == 0 && (ch >= 'a' && ch <= 'z'))
				buffer.append((char)(ch - 32));
			else
				buffer.append(ch);
		}
		
		return buffer.toString();
	}

	public static String firstKeyLowerCase(String str) {
		StringBuilder buffer = new StringBuilder(str.length());
		
		for (char ch : str.toCharArray()) {
			if (buffer.length() == 0 && (ch >= 'A' && ch <= 'Z'))
				buffer.append((char)(ch + 32));
			else
				buffer.append(ch);
		}
		
		return buffer.toString();
	}
	
}
