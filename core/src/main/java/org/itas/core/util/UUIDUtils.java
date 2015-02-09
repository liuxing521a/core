package org.itas.core.util;

import java.util.concurrent.ThreadLocalRandom;

import org.itas.util.Utils.Objects;

public class UUIDUtils {

	
	public static String randomUUID() {
		return String.valueOf(ThreadLocalRandom.current().nextLong(1000000000000L, 9999999999999L));
	}
	
	public static String uuid(String uuid) {
		if (Objects.isEmpty(uuid)) {
			return randomUUID();
		} 
		
		return uuid;
	}
	
	private UUIDUtils() {
		
	}
}
