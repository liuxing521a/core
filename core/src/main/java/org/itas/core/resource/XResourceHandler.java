package net.itas.core.resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;


public class XResourceHandler extends AbstractHandler {
	
	/** 解析后对象和属性*/
	private List<Map<String, String>> xmlList;
	
	public XResourceHandler() {
		super();
		this.xmlList = new ArrayList<>();
	}
	
	/**
	 * <p>获取模版解析后数据</p>
	 * @param clazz 要解析的类名
	 * @param input 要解析的数据流
	 * @return 解析后的数据对象
	 * @throws Exception 
	 */
    public List<Map<String, String>> getXml(Class<? extends Resource> clazz, InputStream input) throws Exception  {  
    	this.clazz = clazz;
        this.factory.newSAXParser().parse(input, this);  
        
        return xmlList;  
    }  
	
	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		xmlList.clear();
	}
	
	@Override
	public void endDocument() throws SAXException  {
		super.endDocument();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if (clazz.getSimpleName().equals(qName)) {
			Map<String, String> attributeMap = new HashMap<>();
			for (int i = 0; i < attributes.getLength(); i++) {
				attributeMap.put(attributes.getQName(i), attributes.getValue(i));
			}
			this.xmlList.add(attributeMap);
		}
	}
	
}
