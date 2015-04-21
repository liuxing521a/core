package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import org.itas.core.CallBack;
import org.itas.util.ItasException;


/**
 *  select 预处理方法字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月28日上午10:13:29
 */
class MDDoSelectProvider extends AbstractMethodProvider {
	
	private CtField idField;
	
	private int index;
	
	MDDoSelectProvider() {
	}
	
	@Override
	public void startClass(CtClass clazz) throws Exception {
		super.startClass(clazz);

		this.index = 0;
		buffer.append("protected void doSelect(java.sql.PreparedStatement state) throws java.sql.SQLException {");
	}
	
	@Override
	public void processField(CtField field) throws Exception {
		if (!isProcesAble(field)) {
			return;
		}
		
		if ("Id".equals(field.getName())) {
			this.idField = field;
		}
	}
	
	@Override
	public void endClass() throws Exception {
		Type type = getType(idField);
		type.process(new CallBack<FieldProvider>() {
			@Override
			public void called(FieldProvider callback) {
				try {
					buffer.append(callback.setStatement((++ index), idField));
				} catch (Exception e) {
					throw new ItasException(e);
				}
			}
		});
		
		buffer.append(next(1, 0));
		buffer.append("}");
	}
	
	@Override
	public CtMethod[] toMethod() throws Exception {
		return new CtMethod[]{CtMethod.make(buffer.toString(), ctClass)};
	}
	
	@Override
	public String toString() {
		return buffer.toString();
	}

}