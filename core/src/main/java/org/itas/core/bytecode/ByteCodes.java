package org.itas.core.bytecode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;

import org.itas.core.util.ClassLoaders;

/**
 * 字节码操作
 * @author liuzhen
 */
public final class ByteCodes implements ClassLoaders {

  public enum ClassType { CTCLASS, CLASS }
	
  static final ByteCodes instance = new ByteCodes();
  
  public static List<Class<?>> loadClass(Class<?> parent, 
      String packageName, ClassType classType) throws Exception {
	if (ClassType.CTCLASS == classType) {
	  return instance.loadFromCtClass(parent, packageName);
	} else if (ClassType.CLASS == classType) {
	  return instance.loadFromJavaClass(parent, packageName);
	} else {
	  return Collections.emptyList();
	}
  }
	
  private List<Class<?>> loadFromJavaClass (
	  Class<?> parent, String packageName) throws Exception {
	final Class<?>[] classArray = loadClass(packageName);
    final List<Class<?>> classList = 
		new ArrayList<Class<?>>(classArray.length);
    
    for (Class<?> clazz : classArray) {
      if (parent.isAssignableFrom(clazz)) {
    	  classList.add(clazz);
      }
    }
    
    return classList;
  }
  
  private List<Class<?>> loadFromCtClass(
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
//	ByteCodeMethods methods = new ByteCodeMethods();
//	methods.begin(ctClass);
//	  
//	List<CtField> fields = getAllField(ctClass);
//	for (CtField field : fields) {
//	  if (field.hasAnnotation(UnSave.class) || 
//	      Modifier.isStatic(field.getModifiers())) {
//			continue;
//	  } 
//		  
//	  methods.append(field);
//	}
//	methods.end();
//	for (CtMethod ctMethod : methods.toMethods()) {
//	  if (ctMethod != null) {
//	    ctClass.addMethod(ctMethod);
//	  }
//	}
//	  
//	ctClass.writeFile("D:/");
//	return ctClass.toClass();
  	return null;
  }
  
  Class<?> testToClass(CtClass ctClass) throws Exception {
//	ByteCodeMethods methods = new ByteCodeMethods();
//	methods.begin(ctClass);
//		
//	List<CtField> fields = getAllField(ctClass);
//	for (CtField field : fields) {
//      if (field.hasAnnotation(UnSave.class) || 
//    	   Modifier.isStatic(field.getModifiers())) {
//		  continue;
//      } 
//			
//      methods.append(field);
//	}
//	methods.end();
//		
//	for (CtMethod ctMethod : methods.toMethods()) {
//	  if (ctMethod != null) {
//	    ctClass.addMethod(ctMethod);
//	  }
//	}
//		
//	clazz.writeFile("D:/");
	return null;
  }
	
  private ByteCodes() {
  }
	
}
