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
class MethodDoFillProvider extends AbstractMethodProvider {
	
	MethodDoFillProvider() {
		super();
	}
	
	@Override
	public void begin(CtClass clazz) throws Exception {
		super.begin(clazz);

		buffer.append("protected void doFill(java.sql.ResultSet result) throws java.sql.SQLException {");
	}

	@Override
	public void append(final CtField field) throws Exception {
		if ("Id".equals(field.getName())) {
			return;
		}
		
		Type type = getType(field);
		type.fieldProcessing(this, new CallBack<FieldProvider>() {
			@Override
			public void called(FieldProvider provider) {
				try {
					buffer.append("\n");
					buffer.append(provider.getResultSet(field));
				} catch (Exception e) {
					throw new ItasException(e);
				}
			}
		});
	}
	
	@Override
	public void end() throws Exception {
		buffer.append("\n\t}");
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
}
