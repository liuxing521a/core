package org.itas.core.bytecode;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

class MDTotolSizeProvider extends AbstractMethodProvider {
	
	MDTotolSizeProvider() {
		super();
	}
	
	@Override
	public void startClass(CtClass clazz) throws Exception {
		super.startClass(clazz);
		
		buffer.append("public int getCachedSize() throws CalculateSizeException");
		buffer.append(next(1, 2));
		buffer.append("int size = 0;");
	}

	@Override
	public void processField(CtField field) throws Exception {
		buffer.append(next(1, 2));
		if (Type.booleanType.isType(field.getType())) {
			buffer.append("size += 1;");
		} else if (Type.byteType.isType(field.getType())) {
			buffer.append("size += 1;");
		} else if (Type.charType.isType(field.getType())) {
			buffer.append("size += 2;");
		} else if (Type.shortType.isType(field.getType())) {
			buffer.append("size += 2;");
		} else if (Type.intType.isType(field.getType())) {
			buffer.append("size += 4;");
		} else if (Type.longType.isType(field.getType())) {
			buffer.append("size += 8;");
		} else if (Type.floatType.isType(field.getType())) {
			buffer.append("size += 4;");
		} else if (Type.doubleType.isType(field.getType())) {
			buffer.append("size += 4;");
		} else if (Type.stringType.isType(field.getType())) {
			buffer.append("if (field.getName() != null)");
			buffer.append("size += field.getName().length();");
		}
	}

	@Override
	public void endClass() throws ClassNotFoundException {
		buffer.append(next(1, 2));
		buffer.append("return size;");
		buffer.append(next(1, 1));
		buffer.append("}");
	}

	@Override
	public CtMethod[] toMethod() throws CannotCompileException {
		return new CtMethod[]{CtMethod.make(buffer.toString(), ctClass)};
	}
	
	@Override
	public String toString() {
		return buffer.toString();
	}
	
}
