package org.itas.core.util;

import java.util.ArrayList;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;

import org.itas.util.Utils.Objects;

public final class Utils {

	public static final class CtClassUtils {
		
		public static List<CtClass> getAllClass(CtClass clazz) throws Exception {
	        List<CtClass> clazzList = new ArrayList<CtClass>();
	        
	        CtClass objectClass = ClassPool.getDefault().get("java.lang.Object");
	        getSupperClass(clazzList, clazz, objectClass);
	        
	        return clazzList;
	    }

	    private static void getSupperClass(List<CtClass> clazzList, CtClass clazz, CtClass parentClass) throws Exception {
	        if (clazz.equals(parentClass)) {
	            return;
	        }
	        
	        clazzList.add(clazz);
	        getSupperClass(clazzList, clazz.getSuperclass(), parentClass);
	    }
	    
	    public static List<CtField> getAllField(CtClass childClass) throws Exception {
	        List<CtField> fieldList = new ArrayList<CtField>();
	        
	        List<CtClass> supperClazzs = getAllClass(childClass);
	        for (CtClass cls : supperClazzs) {
	        	Objects.addAll(fieldList, cls.getDeclaredFields());
			}

	        return fieldList;
	    }
	}
	
}
