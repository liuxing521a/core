package org.itas.core.bytecode;

import static org.itas.core.util.Utils.CtClassUtils.getAllField;

import java.util.ArrayList;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.Modifier;

import org.itas.core.annotation.UnSave;
import org.itas.core.util.CtClassLoader;

/**
 * 字节码操作
 * @author liuzhen
 */
public final class ByteCodes implements CtClassLoader {
	
  private static final ByteCodes instance = new ByteCodes();
  
  public static List<Class<?>> loadClass(
	  Class<?> parent, String packageName) throws Exception {
	  return instance.loadClass0(parent, packageName);
  }
	
  private List<Class<?>> loadClass0(
      Class<?> parent, String packageName) throws Exception {
	final CtClass[] ctClassArray = loadCtClass(packageName);
	final List<Class<?>> classList = 
	    new ArrayList<Class<?>>(ctClassArray.length);
	
	final CtClass parentCt = ClassPool.getDefault().get(parent.getName());
	for (CtClass ctClass : ctClassArray) {
	  if (ctClass.subclassOf(parentCt)) {
		classList.add(toClass(ctClass));
	  }
	}
	
	return classList;
  }
	
  private Class<?> toClass(CtClass ctClass) throws Exception {
	  ByteCodeMethods methods = new ByteCodeMethods();
	  methods.begin(ctClass);
	  
	  List<CtField> fields = getAllField(ctClass);
	  for (CtField field : fields) {
		  if (field.hasAnnotation(UnSave.class) || 
				  Modifier.isStatic(field.getModifiers())) {
			  continue;
		  } 
		  
		  methods.append(field);
	  }
	  methods.end();
	  
	  for (CtMethod ctMethod : methods.toMethods()) {
		  if (ctMethod != null) {
			  ctClass.addMethod(ctMethod);
		  }
	  }
	  
//		clazz.writeFile("D:/");
	  return ctClass.toClass();
  }
  
  static Class<?> testToClass(CtClass ctClass) throws Exception {
	ByteCodeMethods methods = new ByteCodeMethods();
	methods.begin(ctClass);
		
	List<CtField> fields = getAllField(ctClass);
	for (CtField field : fields) {
      if (field.hasAnnotation(UnSave.class) || 
    	   Modifier.isStatic(field.getModifiers())) {
		  continue;
      } 
			
      methods.append(field);
	}
	methods.end();
		
	for (CtMethod ctMethod : methods.toMethods()) {
	  if (ctMethod != null) {
	    ctClass.addMethod(ctMethod);
	  }
	}
		
//	clazz.writeFile("D:/");
	return null;
  }
	
  private ByteCodes() {
  }
	
}
