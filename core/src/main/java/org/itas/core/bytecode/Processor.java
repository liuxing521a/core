package org.itas.core.bytecode;

import org.itas.core.CallBack;

public interface Processor {

	/**
	 * field支持类型处理
	 * @return
	 */
	public void process(CallBack<FieldProvider> called) throws Exception;
	
}
