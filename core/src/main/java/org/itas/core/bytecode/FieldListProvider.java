package org.itas.core.bytecode;

import static org.itas.core.util.ByteCodeUtils.firstKeyUpCase;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.SignatureAttribute.ClassType;
import net.itas.core.annotation.Clazz;
import net.itas.core.annotation.Size;

import org.itas.core.bytecode.AbstractTypeProvider.javassistType;
import org.itas.util.Utils.Objects;

/**
 * list数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月27日下午3:38:27
 */
class FieldListProvider extends FieldContainerProvider {

	private static final String STATEMENT_SET = 
			"\t\t" 
			+ "state.setString(%s, org.itas.core.util.GameObjects.toString(get%s()));" ;


	private static final String RESULTSET_GET = 
			"\t\t"
			+ "{"
			+ "\n\t\t\t"
			+ "String data = result.getString(\"%s\");"
			+ "\n\t\t\t"
			+ "java.util.List dataStrList = org.itas.core.util.GameObjects.parseList(data);"
			+ "\n\t\t\t"
			+ "%s dataArray = new %s;"
			+ "\n\t\t\t"
			+ "for (int i = 0; i < dataStrList.size(); i ++) {"
			+ "\n\t\t\t\t"
			+ "dataArray.add(%s);"
			+ "\n\t\t\t"
			+ "}"
			+ "\n\t\t\t"
			+ "set%s(dataArray);"
			+ "\n\t\t"
			+ "}";
	
	public FieldListProvider() {

	}

	@Override
	public String setStatement(CtField field) throws Exception {
		return String.format(STATEMENT_SET, provider.getAndIncIndex(), firstKeyUpCase(field.getName()));
	}

	@Override
	public String getResultSet(CtField field) throws Exception {
		ClassType definType = (ClassType)SignatureAttribute.toFieldSignature(field.getGenericSignature());
		ClassType chirldType = (ClassType)(definType.getTypeArguments()[0].getType());
		
		CtClass genericType;
		if (chirldType.getDeclaringClass() == null) {
			genericType = ClassPool.getDefault().get(chirldType.getName());
		} else {
			genericType = ClassPool.getDefault().get(
				String.format("%s$%s", chirldType.getDeclaringClass().getName(), chirldType.getName()));
		}
		
		Object annotiation = field.getAnnotation(Clazz.class);
		String listClassName = Objects.nonNull(annotiation) ? 
			((Clazz)annotiation).value().getName() : "java.util.ArrayList";
			
		annotiation = field.getAnnotation(Size.class);
		int size = Objects.nonNull(annotiation) ? ((Size)annotiation).value() : 8;
			
		CtClass listClass = ClassPool.getDefault().get(listClassName);
		try {
			listClass.getDeclaredConstructor(new CtClass[]{javassistType.int_});
			listClassName = String.format("%s(%s)", listClassName, size);
		} catch (NotFoundException e) {
			listClassName = String.format("%s()", listClassName);;
		}
		
		return String.format(RESULTSET_GET, field.getName(), definType.getName(),
				listClassName, toObjectCode(genericType, "(String)dataStrList.get(i)"), firstKeyUpCase(field.getName()));
	}

}
