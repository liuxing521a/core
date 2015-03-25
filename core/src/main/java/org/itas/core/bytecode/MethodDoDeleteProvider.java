//package org.itas.core.bytecode;
//
//import javassist.CtClass;
//import javassist.CtField;
//import javassist.CtMethod;
//
//import org.itas.core.CallBack;
//import org.itas.util.ItasException;
//
//
///**
// *  select 预处理方法字节码动态生成
// * @author liuzhen(liuxing521a@gmail.com)
// * @crateTime 2015年2月28日上午10:13:29
// */
//class MethodDoDeleteProvider extends AbstractMethodProvider {
//	
//	private CtField idField;
//	
//	MethodDoDeleteProvider() {
//		super();
//	}
//	
//	@Override
//	public void begin(CtClass clazz) throws Exception {
//		super.begin(clazz);
//	}
//
//	@Override
//	public void append(CtField field) throws Exception {
//		if ("Id".equals(field.getName())) {
//			this.idField = field;
//		}
//	}
//	
//	@Override
//	public void end() throws Exception {
//		Type type = getType(idField);
//		
//		buffer.append("protected void doDelete(java.sql.PreparedStatement state) throws java.sql.SQLException {");
//		
//		type.fieldProcessing(this, new CallBack<FieldProvider>() {
//			@Override
//			public void called(FieldProvider callback) {
//				try {
//					buffer.append(callback.setStatement(idField));
//				} catch (Exception e) {
//					throw new ItasException(e);
//				}
//			}
//		});
//		
//		buffer.append("}");
//	}
//	
//	@Override
//	public CtMethod toMethod() throws Exception {
//		return CtMethod.make(buffer.toString(), ctClass);
//	}
//	
//	@Override
//	public String toString() {
//		return buffer.toString();
//	}
//
//	@Override
//	public int getAndIncIndex() {
//		return 1;
//	}
//	
//}
