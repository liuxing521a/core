package org.itas.core.bytecode;

public abstract class Modify {

	public Modify() {
		this.index = 0;
		this.buffer = new StringBuilder();
	}
	
	/**
	 * 当前编号
	 */
	private int index;
	
	/**
	 * 存放函数体缓存
	 */
	private StringBuilder buffer;
	
	
	/**
	 * 转成要修改类的函数串
	 * @return
	 */
	protected abstract String toModify();

	String toModifyMethod() {
		try {
			return toModify();
		} finally {
			index = 1;
		}
	}
	
	/**
	 * 增加函数行
	 */
	void addLine(String line) {
		buffer.append(line).append('\n');
	}

	
	public int incIndex() {
		return (++ index);
	}
	
	public void resetIndex() {
		index = 1;
	}
	
	public int index() {
		return index;
	}
	
}
