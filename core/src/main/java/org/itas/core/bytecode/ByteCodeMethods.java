package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

public class ByteCodeMethods {
	
	private MethodProvider[] methods;

	public void begin(CtClass clazz) throws Exception {
		methods = new MethodProvider[]{
			new MethodDoFillProvider(),
			new MethodDoAlterProvider(),
			new MethodDoCreateProvider(),
			new MethodSQLDeleteProvider(),
			new MethodSQLSelectProvider(),
			new MethodSQLUpdateProvider(),
			new MethodSQLInsertProvider(),
			new MethodTableNameProvider(),
			new MethodCloneProvider(),
			new MethodDoDeleteProvider(),
			new MethodDoInsertProvider(),
			new MethodDoSelectProvider(),
			new MethodDoUpdateProvider()
		};
		
		for (MethodProvider method : methods) {
			method.begin(clazz);
		}
	}

	public void append(CtField field) throws Exception {
		for (MethodProvider method : methods) {
			method.append(field);
		}
	}

	public void end() throws Exception {
		for (MethodProvider method : methods) {
			method.end();
		}
	}

	public CtMethod[] toMethods() throws Exception {
		CtMethod[] ctMethods = new CtMethod[methods.length];
		
		for (int i = 0; i < methods.length; i ++) {
			ctMethods[i] = methods[i].toMethod();
		}
		
		return ctMethods;
	}

}
