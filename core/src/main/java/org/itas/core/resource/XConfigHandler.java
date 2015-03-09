package org.itas.core.resource;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.itas.core.FieldNotConfigException;
import org.itas.core.annotation.CanNull;
import org.itas.util.ItasException;
import org.itas.util.Utils.Objects;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


class XConfigHandler extends AbstractXmlHandler {
	
	/** 标签*/
	private String tag;
	/** 正则表达式去除换行符等特殊符号*/
	private Pattern pattern;
	/** 解析的内容*/
	private StringBuffer content;
	
	/** 当前解析的config文件*/
	private Conf config;
	
	public XConfigHandler() {
		super();
		this.pattern = Pattern.compile("\\s*|\t|\r|\n");
	}

	public void getXml(Class<? extends Conf> clazz, InputStream input) throws Exception {
		this.clazz = clazz;
		this.factory.newSAXParser().parse(input, this);
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		try  {
			Method method = clazz.getDeclaredMethod("get");
			
			boolean isAccess = method.isAccessible();
			method.setAccessible(true);
			config = (Conf)method.invoke(null);
			method.setAccessible(isAccess);
		} catch (Exception e) {
			throw new ItasException(e);
		} 
	}
	
	@Override
	public void endDocument() throws SAXException  {
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (!clazz.getSimpleName().equals(qName)) {
			tag = qName;
			content = new StringBuffer();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException  {
		try  {
			Field field = fieldMap.get(tag);
			if (field != null) {
				setValue(field, content.toString());
			}
		} catch (Exception e)  {
			throw new ItasException(e);
		} finally {
			tag = null;
			content = null;
		}
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException  {
		if (tag == null)  {
			return;
		}
		
		String value = new String(ch, start, length).trim();
		content.append(replace(value));
	}
	
	
	private void setValue(Field field, String value) throws Exception {
		if (value != null) {
			fillField(config, field, value);
			return;
		}
		
		if (!field.isAnnotationPresent(CanNull.class)) {
			throw new FieldNotConfigException("class=" + clazz.getName() + ",field=" + field.getName());
		} 
	}
	
	
	public String replace(String str) {
		if (Objects.nonEmpty(str)) {
			Matcher m = pattern.matcher(str);
			return m.replaceAll("");
		} else {
			return "";
		}
	}

}
