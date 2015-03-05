package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import org.itas.core.CallBack;
import org.itas.util.ItasException;
import org.itas.util.Logger;


/**
 *  select 预处理方法字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月28日上午10:13:29
 */
class MethodDoInsertProvider extends AbstractMethodProvider {
	
	private int index;
	
	MethodDoInsertProvider() {
		super();
	}
	
	@Override
	public void begin(CtClass clazz) throws Exception {
		super.begin(clazz);

		this.index = 0;
		buffer.append("protected void doInsert(java.sql.PreparedStatement state) throws java.sql.SQLException {");
	}

	@Override
	public void append(final CtField field) throws Exception {
		Type type = getType(field);
		type.fieldProcessing(this, new CallBack<FieldProvider>() {
			@Override
			public void called(FieldProvider callback) {
				try {
					buffer.append(callback.setStatement(field));
				} catch (Exception e) {
					throw new ItasException(e);
				}
			}
		});
	}
	
	@Override
	public void end() throws Exception {
		buffer.append("}");
	}
	
	@Override
	public CtMethod toMethod() throws Exception {
		return CtMethod.make(buffer.toString(), ctClass);
	}
	
	@Override
	public String toString() {
		Logger.trace("\n{}", buffer.toString());
		return buffer.toString();
	}

	@Override
	public int getAndIncIndex() {
		return ++ index;
	}
	
}
