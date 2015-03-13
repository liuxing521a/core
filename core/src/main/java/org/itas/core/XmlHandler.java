package org.itas.core;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface XmlHandler {
  
  abstract Class<?> getClazz();

  abstract Collection<Field> getFields();

  abstract List<Map<String, String>> parse(String packName) throws Exception;
	
}
