package org.itas.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


class ResourceHandler extends AbstractHandler {
	
  private static final String FILE_ROOT = "resource";
	 
  /** 解析后对象和属性*/
  private List<Map<String, String>> xmlList;
	
  public ResourceHandler(Class<? extends Resource> clazz) {
	super(clazz);
  }
  
  @Override
  public List<Map<String, String>> parse(String packName) throws Exception {
	super.parse(packName);
	
  	return xmlList;
  }
	
  @Override
  public void startDocument() throws SAXException {
    this.xmlList = new ArrayList<>();
  }
	
  @Override
  public void endDocument() throws SAXException  {
	this.xmlList = Collections.unmodifiableList(xmlList);
  }

  @Override
  public void startElement(String uri, String localName, 
	  String qName, Attributes attributes) throws SAXException {
    if (clazz.getSimpleName().equals(qName)) {
	  final Map<String, String> attributeMap = new HashMap<>();
	  for (int i = 0; i < attributes.getLength(); i++) {
		attributeMap.put(attributes.getQName(i), attributes.getValue(i));
	  }
	  
	  this.xmlList.add(Collections.unmodifiableMap(attributeMap));
	}
  }
	
  @Override
  public void endElement(String uri, String localName, 
      String qName) throws SAXException {
  }

  @Override
  protected String rootName() {
    return FILE_ROOT;
  }
	
}
