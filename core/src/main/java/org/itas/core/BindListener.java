package org.itas.core;

import java.util.EventListener;

import com.typesafe.config.Config;

public interface BindListener extends EventListener {
	
	/**
	 * <p>绑定</p>
	 * @param share
	 * @param pack 绑定的包名
	 * @throws Exception
	 */
	abstract void bind(Config share, String pack) throws Exception;

}
