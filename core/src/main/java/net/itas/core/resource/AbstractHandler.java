package net.itas.core.resource;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParserFactory;

import net.itas.util.Utils.ClassUtils;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class AbstractHandler extends DefaultHandler {

	
	/** 要解析的xml类名*/
	protected Class<?> clazz;
	/** SAX解析器创建工厂*/
	protected SAXParserFactory factory;
	/** field缓存*/
	protected Map<String, Field> fieldMap;
	/** 脚本文件*/
	protected Map<String, Method> scripts;
	
	protected AbstractHandler() {
		this.fieldMap = new HashMap<String, Field>();
		this.factory = SAXParserFactory.newInstance();
	}
	
	@Override
	public void startDocument() throws SAXException {
		this.fieldMap.clear();
		loadField(clazz);
	}
	
	@Override
	public void endDocument() throws SAXException  {
		this.fieldMap.clear();
		this.clazz = null;
	}
	
	protected void fillField(AbstractXml source, Map<String, Method> scripts, Field field, String value) throws Exception {
		boolean isAcess = field.isAccessible();
		try  {
			field.setAccessible(true);
			source.fillField(field, value, scripts);
		} finally  {
			field.setAccessible(isAcess);
		}
	}
	
	private void loadField(Class<?> clazz) {
		List<Class<?>> clazzes = ClassUtils.getAllClass(clazz);
		
		for (Class<?> cls : clazzes) {
			Field[] fields = cls.getDeclaredFields();
			for (Field field : fields)  {
				if (!Modifier.isFinal(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())) {
					fieldMap.put(field.getName(), field);
				}
			}
		}
	}
	
}
