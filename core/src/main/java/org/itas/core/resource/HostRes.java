package org.itas.core.resource;

public abstract class HostRes extends Resource {

	protected HostRes(String Id) {
		super(Id);
	}

	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 逻辑服务器端口号
	 */
	private int port;
	
	/**
	 * 绑定服务器地址
	 */
	private String host;
	
	/**
	 * 公网地址
	 */
	private String netHost;
	
	
	public String getName() {
		return name;
	}

	public int getPort() {
		return port;
	}

	public String getHost() {
		return host;
	}
	
	public String getNetHost() {
		return netHost;
	}
	
	@Override
	public final String toString() {
		return String.format("host:%s, port:%s, netHost:%s", host, port, netHost);
	}
}
