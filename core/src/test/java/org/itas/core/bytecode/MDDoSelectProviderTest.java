package org.itas.core.bytecode;

import java.util.List;

import javassist.ClassPool;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;
import junit.framework.Assert;

import org.itas.core.util.ClassLoaders;
import org.itas.core.util.Next;

public class MDDoSelectProviderTest extends 
	AbstreactMethodProvider implements ClassLoaders, Next {

	@Override
	public void setUP() throws NotFoundException {
		ctClazz = ClassPool.getDefault().get(Item.class.getName());
		provider =  new MDDoSelectProvider();
	}
	
	@Override
	public void methodTest() throws Exception {
		provider.startClass(ctClazz);
		
		final List<CtField> fields = getAllField(ctClazz);
		for (CtField field : fields) {
			provider.processField(field);
		}
		
		provider.endClass();
	
		String expected = 
				"protected void doSelect(java.sql.PreparedStatement state) throws java.sql.SQLException {"
						+ "\n\t\tstate.setString(1, getId());" 
						+ "\n}";
		
		Assert.assertEquals(expected, provider.toString());
		
		for (CtMethod ctMethod : provider.toMethod()) {
			ctClazz.addMethod(ctMethod);
		}
	}
}
