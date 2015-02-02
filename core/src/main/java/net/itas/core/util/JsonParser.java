package net.itas.core.util;

import java.util.HashMap;
import java.util.Map;


public final class JsonParser {

	private Map<String, Element> json_map;
	
	
	public JsonParser() {
		this.json_map = new HashMap<String, Element>();
	}
	
	public void addAttribute(String name, Object value) {
		Element element = new Element();
		element.setName(name);
		element.setValue(value);
		
		json_map.put(name, element);
	}
	
	public String getAttributeValue(String name) {
		Element elment = json_map.get(name);
		if (elment == null) {
			return null;
		}
		
		return elment.getValue();
	}
	
	public String toJson() {
		StringBuffer buffer = new StringBuffer();
		buffer.append('{');
		
		for (Element element : json_map.values()) {
			buffer.append('"');
			buffer.append(element.getName());
			buffer.append('"');
			buffer.append(':');
			
			buffer.append('"');
			buffer.append(element.getValue());
			buffer.append('"');
			buffer.append(',');
			
		}
		
		buffer.deleteCharAt(buffer.length() - 1);
		buffer.append('}');
		return buffer.toString();
	}
	
	public static JsonParser parser(String jsonStr) {
		return new JsonParser().parerJson(jsonStr);
	}
	
	private JsonParser parerJson(String jsonStr) {
		char[] charArray = jsonStr.toCharArray();
		
		
		JsonParser json = new JsonParser();

		Element element = null;
		StringBuffer jsonBuffer = null;
		for (char ch : charArray) {
			if (ch == '{') {
				element = new Element();
				jsonBuffer = new StringBuffer();
			} else if (ch == '"') {
				continue;
			} else if (ch == ':') {
				element.setName(jsonBuffer.toString());
				jsonBuffer = new StringBuffer();
			} else if (ch == ',') {
				element.setValue(jsonBuffer.toString());
				json.json_map.put(element.getName(), element);

				element = new Element();
				jsonBuffer = new StringBuffer();
			} else if (ch == '}') {
				element.setValue(jsonBuffer.toString());
				json.json_map.put(element.getName(), element);
			} else {
				jsonBuffer.append(ch);
			}
		}
		
		return json;
	}
	
	
	private static class Element {
		
		private String name;
		
		private Object value;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getValue() {
			return value.toString();
		}

		public void setValue(Object value) {
			this.value = value;
		}
	}
	
}
