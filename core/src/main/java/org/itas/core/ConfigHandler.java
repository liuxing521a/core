package org.itas.core;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


class ConfigHandler extends AbstractHandler {
	
  /** 正则表达式去除换行符等特殊符号*/
  private static final Pattern pattern = Pattern.compile("\\s*|\t|\r|\n");
  
  private static final String FILE_ROOT = "resource";
	
  /** 标签*/
  private String tag;
	
  /** 解析的内容*/
  private StringBuffer content;
  
  private Map<String, String> attributes;
	
  public ConfigHandler(Class<? extends Config> clazz) {
	super(clazz);
  }

  @Override
  public List<Map<String, String>> parse(String packName) throws Exception {
	super.parse(packName);
	
	return Arrays.asList(attributes);
  }
  
  @Override
  public void startDocument() throws SAXException {
	attributes = new HashMap<>();
  }
  
  @Override
  public void endDocument() throws SAXException {
	attributes = Collections.unmodifiableMap(attributes);
  }

  @Override
  public void startElement(String uri, String localName, String qName, 
      Attributes attributes) throws SAXException {
	if (!clazz.getSimpleName().equals(qName)) {
	  tag = qName;
	  content = new StringBuffer();
	}
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException  {
	if (tag == null) {
	  return;
	}
	
	try {
	  attributes.put(tag, content.toString());
	} finally {
	  tag = null;
	  content.setLength(0);
	}
  }

  @Override
  public void characters(char[] ch, int start, int length) throws SAXException  {
	if (tag == null)  {
	  return;
	}

	content.append(replace(new String(ch, start, length).trim()));
  }
	
  private String replace(String content) {
	if (content == null || content.length() == 0) {
	  return "";
	}
	
	final Matcher m = pattern.matcher(content);
	return m.replaceAll("");
  }

  @Override
  protected String rootName() {
    return FILE_ROOT;
  }

}
