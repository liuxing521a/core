package org.itas.core.bytecode;

import static org.itas.core.util.Utils.CtClassUtils.getAllField;

import java.util.List;

import org.itas.util.ItasException;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;
import net.itas.core.annotation.UnSave;

/**
 * 字节码操作
 * @author liuzhen
 */
public final class ByteCodes {
	
	public static Class<?> generate(CtClass clazz) throws Exception {
		
		ByteCodeMethods methods = new ByteCodeMethods();
		methods.begin(clazz);
		
		List<CtField> fields = getAllField(clazz);
		for (CtField field : fields) {
			if (field.hasAnnotation(UnSave.class) || 
				Modifier.isStatic(field.getModifiers())) {
				continue;
			} 
			
			methods.append(field);
		}
		
		methods.end();
		
		for (CtMethod ctMethod : methods.toMethods()) {
			clazz.addMethod(ctMethod);
		}
		
		clazz.writeFile("D:/");
//		return clazz.toClass();
		return null;
	}
	
	private ByteCodes() {
		throw new ItasException("can not new instance...");
	}
	
}
