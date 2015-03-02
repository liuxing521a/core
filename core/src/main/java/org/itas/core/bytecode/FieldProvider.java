package org.itas.core.bytecode;

import javassist.CtField;

public interface FieldProvider extends Provider {

	abstract String setStatement(CtField field) throws Exception;
	
	abstract String getResultSet(CtField field) throws Exception;

	abstract void setMethodProvider(MethodProvider provider);
}
