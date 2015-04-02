package org.itas.core.bytecode;

import java.util.Map;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;

import org.itas.core.UnDeFineException;
import org.itas.core.util.FirstChar;
import org.itas.util.Utils.Objects;

import com.google.common.collect.Maps;

/**
 *  select 预处理方法字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月28日上午10:13:29
 */
class MDGetterProvider extends AbstractMethodProvider implements FirstChar {
	
  private Map<String, CtMethod> getMethodMaps;
  
  MDGetterProvider() {
  }
	
  @Override
  public void startClass(CtClass clazz) throws Exception {
		super.startClass(clazz);
		this.getMethodMaps = Maps.newHashMap();
		
		final CtMethod[] ctMethods = clazz.getMethods();
		for (CtMethod ctMethod : ctMethods) {
		  if (ctMethod.getName().startsWith("get")) {
				getMethodMaps.put(compressSetName(ctMethod.getName()), ctMethod);
				continue;
		  }
		}
  }
  

  @Override
  public void processField(CtField field) throws Exception {

  }
	
  @Override
  public void endClass() throws Exception {
  	
  }
	
  @Override
  public CtMethod[] toMethod() throws Exception {
	return null;
  }
	
  @Override
  public String toString() {
  	return buffer.toString();
  }

  private String compressSetName(String name) {
    if (name == null || name.length() < 3) {
      throw new IllegalArgumentException("must get|set Name:" + name);
    }
    
    return lowerCase(name.substring(3, name.length()));
  }
  
  CtMethod checkgetMethodNull(CtField ctField) {
  	CtMethod ctMethod = getMethodMaps.get(ctField.getName());
  	if (Objects.isNull(ctMethod)) {
  		throw new UnDeFineException("class's field has not get method:[class:" + 
	      ctClass.getName() + ", Field:" + ctField.getName() + "]");
  	}
  
  	return ctMethod;
  }
  
}
