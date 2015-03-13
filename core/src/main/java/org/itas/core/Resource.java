package org.itas.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

import org.itas.core.annotation.CanNull;
import org.itas.util.Utils.Objects;

public abstract class Resource extends AbstractXml implements HashId {
	
  /** 资源唯一Id*/
  private String Id;
	
  protected Resource(String Id)  {
	this.Id = Id;
  }
	
  @Override
  public String getId()  {
	return Id;
  }

  void load(Collection<Field> fields, Map<String, String> attributes) throws Exception {
	for (Field field : fields) {
	  if (Modifier.isFinal(field.getModifiers()) || 
	      Modifier.isStatic(field.getModifiers())) {
		continue;
	  }
				
	  String value = attributes.get(field.getName());
	  if (Objects.nonNull(value)) {
		fill(field, value);
		continue;
	  }
				
	  if (!field.isAnnotationPresent(CanNull.class)) {
		throw new FieldNotConfigException("class:" + getClass().getName() + 
		    "[Id=" + getId() + ",field=" + field.getName() + "]");
	  }
	}
  }
	
  @Override
  public boolean equals(Object o)  {
    if (this == o) {
      return true;
    }
    
    if (o instanceof Resource) {
      return getClass() == o.getClass() && Id.intern() == (((Resource)o).Id).intern();	
    }
		
	return false;
  }
	
  @Override
  public int hashCode() {
	return 31 + Id.hashCode();
  }
	
}
