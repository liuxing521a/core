package org.itas.core.resources;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.itas.core.Builder;
import org.itas.core.Resource;

import com.google.common.collect.Lists;

public final class Element {

	private final String name;
	
	private final String content;
	
	private final Attribute attributes;
	
	private final List<Element> elements;
	
	private Element(String name, String content, 
		Attribute attributes, List<Element> elements) {
		this.name = name;
		this.content = content;
		this.attributes = attributes;
		this.elements = Collections.unmodifiableList(elements);
	}

	public String getName() {
		return name;
	}

	public String getContent() {
		return content;
	}

	public Attribute getAttributes() {
		return attributes;
	}

	public List<Element> getElements() {
		return elements;
	}
	
	public Element getElement(String name) {
		for (Element el : elements) {
			if (name == el.name || name.equals(el.name)) {
				return el;
			}
		}
		
		throw new NullPointerException("["  + name + "] element not exists");
	}
	
	public String getAttrValue(String name) {
		return attributes.getValue(name);
	}
	
	public boolean getAttrBoolean(Field field) {
		return attributes.getBoolean(field);
	}

	public byte getAttrByte(Field field) {
		return attributes.getByte(field);
	}
	
	public char getAttrChar(Field field) {
		return attributes.getChar(field);
	}
	
	public short getAttrShort(Field field) {
		return attributes.getShort(field);
	}
	
	public int getAttrInt(Field field) {
		return attributes.getInt(field);
	}

	public long getAttrLong(Field field) {
		return attributes.getLong(field);
	}
	
	public float getAttrFloat(Field field) {
		return attributes.getFloat(field);
	}
	
	public double getAttrDouble(Field field) {
		return attributes.getDouble(field);
	}
	
	public String getAttrString(Field field) {
		return attributes.getString(field);
	}
	
	public Resource getAttrResource(Field field) {
		return attributes.getResource(field);
	}
	
	public Timestamp getAttrTimestamp(Field field) {
		return attributes.getTimestamp(field);
	}
	
	public List<Object> getAttrList(Field field) throws Exception {
		return attributes.getList(field);
	}
	
	public Set<Object> getAttrSet(Field field) throws Exception {
		return attributes.getSet(field);
	}
	
	public Map<Object, Object> getAttrMap(Field field) throws Exception {
		return attributes.getMap(field);
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("<").append(name);
		if (attributes != null) {
			buffer.append(attributes.toString());
		}
		buffer.append(">");
		
		if (elements != null) {
			for (Element el : elements) {
				buffer.append("\n\t").append(el);
			}
		}
		
		if (content != null) {
			buffer.append(content);
		}
		
		buffer.append("</").append(name).append(">");
		
		return buffer.toString();
	}
	
	public static ElementBuilder newBuilder() {
		return new ElementBuilder();
	}
	
	public static class ElementBuilder implements Builder {

		private String name;
		
		private String content;
		
		private Attribute.AttributeBuilder attributes;
		
		private List<Element> elements;
		
		private ElementBuilder prev;
		
		private ElementBuilder() {
		}
		
		public String getName() {
			return name;
		}
		
		public ElementBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public ElementBuilder getPrev() {
			return prev;
		}

		public ElementBuilder setPrev(ElementBuilder prev) {
			this.prev = prev;
			return this;
		}

		public ElementBuilder setContent(String content) {
			this.content = content;
			return this;
		}

		public ElementBuilder addAttribute(String name, String value) {
			if (attributes == null) {
				attributes = Attribute.newBuilder();
			}
			
			this.attributes.addAttribute(name, value);
			return this;
		}
		
		public ElementBuilder addElement(Element element) {
			if (elements == null) {
				elements = Lists.newArrayList();
			}
			
			elements.add(element);
			return this;
		}

		@Override
		public Element builder() {
			final Attribute attr = 
					(attributes == null) ? null : attributes.builder(); 
			final List<Element> els = 
					(elements == null) ? Collections.emptyList() : elements;
			return new Element(name, content, attr, els);
		}
		
	}
	
}
