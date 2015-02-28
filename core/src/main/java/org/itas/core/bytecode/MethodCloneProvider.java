package org.itas.core.bytecode;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import org.itas.core.util.Type;
import org.itas.util.ItasException;
import org.itas.util.Logger;

/**
 *  SQL操作方法字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月28日上午10:13:29
 */
class MethodCloneProvider extends AbstractMethodProvider {

	@Override
	public void begin(CtClass clazz) throws Exception {
		checkCloneAble(clazz);
		
		this.ctClass = clazz;
		this.buffer = new StringBuffer();
	}
	
	@Override
	public void append(CtField field) {
		
	}
	
	@Override
	public void end() throws Exception {
		buffer.append("protected org.itas.core.GameObject clone(java.lang.String oid) {");
		buffer.append("return new ").append(ctClass.getName()).append("(oid);");
//		buffer.append("return null;");
		buffer.append("}");
	}
	
	@Override
	public CtMethod toMethod() throws CannotCompileException {
		return CtMethod.make(buffer.toString(), ctClass);
	}
	
	@Override
	public String toString() {
		Logger.trace("\n{}", buffer.toString());
		return buffer.toString();
	}

	private void checkCloneAble(CtClass clazz) throws Exception {
		if (Type.gameObjectAutoIdType.is(clazz)) {
			return;
		} 

		if (Type.gameObjectType.is(clazz)) {
			return;
		} 
		
		throw new ItasException("un supported clone:" + clazz.getName());
	}
	
}
