package org.itas.core.bytecode;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.NotFoundException;
import javassist.bytecode.SignatureAttribute;
import javassist.bytecode.SignatureAttribute.ClassType;

import org.itas.core.annotation.Clazz;
import org.itas.core.annotation.Size;
import org.itas.util.Utils.Objects;

/**
 * list数据[field]类型字节码动态生成
 * @author liuzhen(liuxing521a@gmail.com)
 * @crateTime 2015年2月27日下午3:38:27
 */
class MapProvider extends ContainerProvider 
		implements FieldProvider, TypeProvider {

	private static final String STATEMENT_SET = 
			"\t\t" 
			+ "state.setString(%s, toString(get%s()));" ;


	private static final String RESULTSET_GET = 
			"\t\t"
			+ "{"
			+ "\n\t\t\t"
			+ "String datas = result.getString(\"%s\");"
			+ "\n\t\t\t"
			+ "org.itas.util.Pair[] dataArray = parsePair(datas);"
			+ "\n\t\t\t"
			+ "%s dataMap = new %s;"
			+ "\n\t\t\t"
			+ "org.itas.util.Pair pair;"
			+ "\n\t\t\t"
			+ "for (int i = 0; i < dataArray.length; i ++) {"
			+ "\n\t\t\t\t"
			+ "pair = dataArray[i];"
			+ "\n\t\t\t\t"
			+ "dataMap.put(%s, %s);"
			+ "\n\t\t\t"
			+ "}"
			+ "\n\t\t\t"
			+ "set%s(dataMap);"
			+ "\n\t\t"
			+ "}";
	
	public static final MapProvider PROVIDER = new MapProvider();
	
	private MapProvider() {
	}
	
	@Override
	public boolean isType(Class<?> clazz) {
		return javaType.map_.isAssignableFrom(clazz);
	}
	
	@Override
	public boolean isType(CtClass clazz)  throws Exception {
		return clazz.subtypeOf(javassistType.map_);
	}

	@Override
	public String sqlType(CtField field) throws Exception {
		return String.format("`%s` TEXT", field.getName());
	}
	
	@Override
	public String setStatement(int index, CtField field) throws Exception {
		return String.format(STATEMENT_SET, index, upCase(field.getName()));
	}

	@Override
	public String getResultSet(CtField field) throws Exception {
		ClassType definType = (ClassType)SignatureAttribute.toFieldSignature(field.getGenericSignature());
		
		ClassType keyType = (ClassType)(definType.getTypeArguments()[0].getType());
		ClassType valueType = (ClassType)(definType.getTypeArguments()[1].getType());
		
		CtClass keyCtClassType = toCtClassType(keyType);
		CtClass valueCtClassType = toCtClassType(valueType);;
		
		Object annotiation = field.getAnnotation(Clazz.class);
		String listClassName = Objects.nonNull(annotiation) ? 
			((Clazz)annotiation).value().getName() : "java.util.HashMap";
			
		annotiation = field.getAnnotation(Size.class);
		int size = Objects.nonNull(annotiation) ? ((Size)annotiation).value() : 8;
			
		CtClass listClass = ClassPool.getDefault().get(listClassName);
		try {
			listClass.getDeclaredConstructor(new CtClass[]{javassistType.int_});
			listClassName = String.format("%s(%s)", listClassName, size);
		} catch (NotFoundException e) {
			listClassName = String.format("%s()", listClassName);;
		}
		
		return String.format(RESULTSET_GET, field.getName(), definType.getName(), listClassName,
				toObjectCode(keyCtClassType, "(String)pair.getKey()"),
				toObjectCode(valueCtClassType, "(String)pair.getValue()"), upCase(field.getName()));
	}

	private CtClass toCtClassType(ClassType classType) throws NotFoundException {
		if (classType.getDeclaringClass() == null) {
			return ClassPool.getDefault().get(classType.getName());
		} 

		return ClassPool.getDefault().get(
				String.format("%s$%s", classType.getDeclaringClass().getName(), classType.getName()));
	}

}
