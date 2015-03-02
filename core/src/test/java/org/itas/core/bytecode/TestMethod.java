package org.itas.core.bytecode;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

public class TestMethod extends AbstractMethodProvider {

	@Override
	public int getAndIncIndex() {
		return 1;
	}

	@Override
	public void begin(CtClass clazz) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void append(CtField field) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public CtMethod toMethod() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
