package org.itas.core.bytecode;

import org.itas.core.util.FirstChar;

/**
 * 属性[field]字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月28日上午10:13:48
 */
abstract class AbstractFieldProvider implements FieldProvider, FirstChar {
	
  protected MethodProvider provider;
	
  @Override
  public void setMethodProvider(MethodProvider provider) {
    this.provider = provider;
  }
	
}
