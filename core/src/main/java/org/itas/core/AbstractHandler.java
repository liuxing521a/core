package org.itas.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParserFactory;

import org.itas.core.util.ClassLoaders;
import org.xml.sax.helpers.DefaultHandler;

import com.typesafe.config.Config;

abstract class AbstractHandler extends DefaultHandler 
    implements XmlHandler, ClassLoaders, Ioc {
	
  /** 资源文件所在地址*/
  private static String dir;

  /** field缓存*/
  private static Map<String, Field> fieldMap;

  /** 要解析的xml类名*/
  protected Class<?> clazz;
  
  public AbstractHandler(Class<? extends AbstractXml> clazz) {
	initialized();
  }
  
  protected abstract String rootName();
  
  private void initialized() {
    synchronized (clazz) {
	  if (fieldMap == null) {
		fieldMap = Collections.unmodifiableMap(loadField(clazz));
	  }
		  
	  if (dir == null) {
		final Config config = Ioc.getInstance(Config.class);
	    dir = config.getConfig("shared").getString("dir");
	  }
	}
  }
  
  @Override
  public Class<?> getClazz() {
    return clazz;
  }
  
  @Override
  public Collection<Field> getFields() {
	return fieldMap.values();
  }
  
  protected Field getField(String name) {
	return fieldMap.get(name);
  }
  
  @Override
  public List<Map<String, String>> parse(String packName) throws Exception {
	final SAXParserFactory factory = SAXParserFactory.newInstance();
	final Path path = getPath(packName);
	factory.newSAXParser().parse(Files.newInputStream(path), this);;
	
	return null;
  }
  
  private Path getPath(String parentPack) {
	String suffix = clazz.getName().replace(parentPack, "");
	suffix = suffix.replace('.', '\\');
	suffix = String.format("%s.xml", suffix);
	
    return Paths.get(dir, rootName(), suffix);
  }
	
  private Map<String, Field> loadField(Class<?> clazz) {
	final List<Class<?>> clazzes = getAllClass(clazz);
	final Map<String, Field> fieldMap = new HashMap<>(clazzes.size()); 
		
	for (final Class<?> cls : clazzes) {
	  final Field[] fields = cls.getDeclaredFields();
	  for (Field field : fields)  {
		if (!Modifier.isFinal(field.getModifiers()) && 
			!Modifier.isStatic(field.getModifiers())) {
		  fieldMap.put(field.getName(), field);
		}
	  }
	}
	
	return fieldMap;
  }
	
}
