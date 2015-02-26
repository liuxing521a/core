package org.itas.core.resource;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;

import org.itas.util.ItasException;
import org.itas.util.Utils.ClassUtils;
import org.itas.util.Utils.Objects;

import net.itas.core.annotation.CanNull;
import net.itas.core.exception.FieldNotConfigException;




public abstract class Resource extends AbstractXml {
	
	/** 资源唯一Id*/
	private String Id;
	
	protected Resource(String Id)  {
		this.Id = Id;
	}
	
	public String getId()  {
		return Id;
	}

	void load(Map<String, String> attributes) {
		List<Field> fieldList = ClassUtils.getAllField(this.getClass());
		
		for (Field field : fieldList) {
			try {
				if (Modifier.isFinal(field.getModifiers()) || 
					Modifier.isStatic(field.getModifiers())) {
					continue;
				}
				
				String value = attributes.get(field.getName());
				if (Objects.nonNull(value)) {
					boolean access = field.isAccessible();
					field.setAccessible(true);
					fillField(field, value, null);
					field.setAccessible(access);
					continue;
				}
				
				if (!field.isAnnotationPresent(CanNull.class)) {
					throw new FieldNotConfigException("class:" + getClass().getName() + "[Id=" + getId() + ",field=" + field.getName() + "]");
				}
			} catch (Exception e) {
				throw new ItasException(e);
			} 
		}
	}
	
	@Override
	public boolean equals(Object o)  {
		if (o instanceof Resource)  {
			return  getClass() == o.getClass() && Id.equals(((Resource)o).Id);
		}
		
		return false;
	}
	
	@Override
	public int hashCode() {
		return Id.hashCode();
	}
	
}
