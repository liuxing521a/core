package org.itas.core.resources;

import static org.itas.core.bytecode.Type.resourceType;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.itas.core.Builder;
import org.itas.core.Config;
import org.itas.core.DoubleException;
import org.itas.core.Parser;
import org.itas.core.Pool.ResPool;
import org.itas.core.Resource;
import org.itas.core.bytecode.ByteCodes.ClassType;
import org.itas.core.resources.Attribute.AttributeBuilder;
import org.itas.core.util.Constructors;
import org.itas.util.Utils.ClassUtils;


abstract class XmlLoader implements Constructors {
	
	interface Back<T> {
		
		Resource called(Attribute attribute) throws Exception;
		
	}
	
	/** 工作目录*/
	protected final String workDir;
  /** 所在包名 */
	protected final String packageName;
  /** 绑定顶级类 */
  protected final Class<?> parentClass;
	
	private XmlLoader(String workDir, String packageName, Class<?> parentClass) {
		this.workDir = workDir;
		this.packageName = packageName;
		this.parentClass = parentClass;
	}
	
	protected abstract List<Resource> fileMapXml(Params params) throws Exception;
	
	protected void loadXml(List<XmlInfo> xmlInfos) throws Exception {
		for (XmlInfo info : xmlInfos) {
			info.load();
		}
	}
	
	static class ResourceLoader extends XmlLoader implements OnLoader {
		
		private ResourceLoader(String workDir, String packageName, Class<?> parentClass) {
			super(workDir, packageName, parentClass);
		}
		
		@Override
		public ResPool loading() throws Exception {
			Parser parser = XmlParser.newInstance();
			final List<Class<?>> classList = ClassType.CLASS.loadClass(parentClass, packageName);
			
			Map<String, Resource> resMap = new HashMap<>();
		  Map<Class<?>, List<Resource>> typeResMap = new HashMap<>();
			List<XmlInfo> xmlInfos = new LinkedList<>();
		  for (final Class<?> clazz : classList) {
		    if (Modifier.isAbstract(clazz.getModifiers())) {
		    	continue;
		    }

		    Back<Attribute> called = new Back<Attribute>() {
		    	@Override
		    	public Resource called(Attribute attr) throws Exception {
		    		Resource temp = newInstance(clazz, new Object[]{attr.getValue("Id")});
		    		
		    		Resource old;
		    		if ((old = resMap.put(temp.getId(), temp)) != null) {
		    			throw new DoubleException(String.format("class:[%s]\nclass:[%s] with same id[%s]", 
		    					temp.getClass().getName(), old.getClass().getName(), old.getId()));
		    		}
		    		
		    		return temp;
		    	}
		    };
		    
		    List<Resource> resList = fileMapXml(
		    		Params.newBuilder()
			    		.setInfos(xmlInfos)
			    		.setClazz(clazz)
			    		.setParser(parser)
			    		.setCalled(called)
			    		.builder());
		    
		    typeResMap.put(clazz, Collections.unmodifiableList(resList));
		  }
		  
		  ResPool resPool = ResourcePoolImpl.newBuilder()
		  	.setResMap(resMap)
		  	.setTypeResMap(typeResMap)
		  	.builder();
		  
			loadXml(xmlInfos);
			
			return resPool;
		}

		@Override
		public void reLoading() throws Exception {
			Parser parser = XmlParser.newInstance();
			final List<Class<?>> classList = ClassType.CLASS.loadClass(parentClass, packageName);
			
			List<XmlInfo> xmlInfos = new LinkedList<>();
			for (final Class<?> clazz : classList) {
		    if (Modifier.isAbstract(clazz.getModifiers())) {
		    	continue;
		    }

		    Back<Attribute> called = new Back<Attribute>() {
		    	@Override
		    	public Resource called(Attribute attr) throws Exception {
		    		return ResourcePoolImpl.RES_MAP.get(attr.getValue("Id"));
		    	}
		    };

		   fileMapXml(Params.newBuilder()
			    		.setInfos(xmlInfos)
			    		.setClazz(clazz)
			    		.setParser(parser)
			    		.setCalled(called)
			    		.builder());
		  }
			
			loadXml(xmlInfos);
		}
		
		@Override
		protected List<Resource> fileMapXml(Params param) throws Exception {
			List<Resource> childList = new LinkedList<>();

			if (resourceType.isType(param.getClazz())) {
				Element root = param.parser(null);
	    	List<Field> fields = ClassUtils.getAllField(param.getClass());
			    	
	    	Resource temp;
	    	for (Element element : root.getElements()) {
	    		temp = (Resource)param.called(element.getAttributes());
	    		
	    		childList.add(temp);
	    		param.addXmlInfo(XmlInfo.newBuilder()
		    			.setSource(temp)
		    			.setAttribute(element.getAttributes())
		    			.setFields(fields)
		    			.builder());
	    	}
	    } 
			
			return childList;
		}
		
		public static ResourceLoaderBuilder newBuilder() {
			return new ResourceLoaderBuilder();
		}
		
		public static class ResourceLoaderBuilder extends XmlLoaderBuilder {
			
			private ResourceLoaderBuilder() {
			}
			
			@Override
			public ResourceLoader builder() {
				return new ResourceLoader(workDir, packageName, parentClass);
			}
		}
		
	}
	
	static class ConfigLoader extends XmlLoader implements OnLoader {

		private ConfigLoader(String workDir, String packageName, Class<?> parentClass) {
			super(workDir, packageName, parentClass);
		}
		
		@Override
		public Object loading() throws Exception {
			Parser parser = XmlParser.newInstance();
			final List<Class<?>> classList = ClassType.CLASS.loadClass(parentClass, packageName);
			
			List<XmlInfo> xmlInfos = new LinkedList<>();
			for (final Class<?> clazz : classList) {
		    if (Modifier.isAbstract(clazz.getModifiers())) {
		    	continue;
		    }

		    fileMapXml(Params.newBuilder()
		    		.setClazz(clazz)
		    		.setParser(parser)
		    		.setInfos(xmlInfos)
		    		.builder());
			}
			
			loadXml(xmlInfos);
			return null;
		}

		@Override
		public void reLoading() throws Exception {
			Parser parser = XmlParser.newInstance();
			final List<Class<?>> classList = ClassType.CLASS.loadClass(parentClass, packageName);
			
			List<XmlInfo> xmlInfos = new LinkedList<>();
			for (final Class<?> clazz : classList) {
		    if (Modifier.isAbstract(clazz.getModifiers())) {
		    	continue;
		    }

		    fileMapXml(Params.newBuilder()
		    		.setClazz(clazz)
		    		.setParser(parser)
		    		.setInfos(xmlInfos)
		    		.builder());
			}
			
			loadXml(xmlInfos);
		}
		
		@Override
		protected List<Resource> fileMapXml(Params param) throws Exception {
			if (Config.class.isAssignableFrom(param.getClazz())) {
				
				List<Field> fields = ClassUtils.getAllField(param.getClazz());
			  Element root = param.parser(null);
			    	
			  AttributeBuilder builder = Attribute.newBuilder();
	    	for (Element element : root.getElements()) {
	    		builder.addAttribute(element.getName(), element.getContent());
	    	}
	    	
	    	Method method = param.getClazz().getDeclaredMethod("getInstance");
	    	method.setAccessible(true);
	    	Config config = (Config)method.invoke(null);
	    	
	    	param.addXmlInfo(XmlInfo.newBuilder()
	    			.setSource(config)
	    			.setAttribute(builder.builder())
	    			.setFields(fields)
	    			.builder());
	    }
			
			return Collections.emptyList();
		}
		
		public static ConfigLoaderBuilder newBuilder() {
			return new ConfigLoaderBuilder();
		}
		
		public static class ConfigLoaderBuilder extends XmlLoaderBuilder {
			
			private ConfigLoaderBuilder() {
			}
			
			@Override
			public ConfigLoader builder() {
				return new ConfigLoader(workDir, packageName, parentClass);
			}
		}
		
	}
	
	private static class XmlInfo {
		
		private final AbstractXml source;

		private final Attribute attribute;
		
		private final List<Field> fields;
		
		private XmlInfo(AbstractXml source, Attribute attribute, List<Field> fields) {
			this.source = source;
			this.attribute = attribute;
			this.fields = fields;
		}

		public void load() throws Exception {
			source.load(fields, attribute);
		}
		
		public static XmlInfiBuilder newBuilder() {
			return new XmlInfiBuilder();
		}
		
		private static class XmlInfiBuilder implements Builder {

			private AbstractXml source;
			private Attribute attribute;
			private List<Field> fields;
			
			private XmlInfiBuilder() {
			}
				
			public XmlInfiBuilder setSource(AbstractXml source) {
				this.source = source;
				return this;
			}

			public XmlInfiBuilder setAttribute(Attribute attribute) {
				this.attribute = attribute;
				return this;
			}

			public XmlInfiBuilder setFields(List<Field> fields) {
				this.fields = fields;
				return this;
			}

			@Override
			public XmlInfo builder() {
				return new XmlInfo(source, attribute, fields);
			}
		}
	}
	
	static abstract class XmlLoaderBuilder implements Builder {

		protected String workDir;
		protected String packageName;
		protected Class<?> parentClass;
		
		private XmlLoaderBuilder() {
		}
		
		public XmlLoaderBuilder setWorkDir(String workDir) {
			this.workDir = workDir;
			return this;
		}

		public XmlLoaderBuilder setPackageName(String packageName) {
			this.packageName = packageName;
			return this;
		}

		public XmlLoaderBuilder setParentClass(Class<?> parentClass) {
			this.parentClass = parentClass;
			return this;
		}
		
	}
	
	static class Params {
		
		private final List<XmlInfo> infos;
		private final Class<?> clazz;
		private final Parser parser;
		private final Back<Attribute> called;
		
		private Params(List<XmlInfo> infos, Class<?> clazz, Parser parser, Back<Attribute> called) {
			this.infos = infos;
			this.clazz = clazz;
			this.parser = parser;
			this.called = called;
		}
		
		public List<XmlInfo> getInfos() {
			return infos;
		}
		
		public void addXmlInfo(XmlInfo info) {
			infos.add(info);
		}
		
		public Class<?> getClazz() {
			return clazz;
		}
		
		public Element parser(Path file) throws Exception {
			return parser.parse(file);
		}
		
		public Resource called(Attribute attribute) throws Exception {
			return called.called(attribute);
		}
		
		public static ParamsBuilder newBuilder() {
			return new ParamsBuilder();
		}
		
		public static class ParamsBuilder implements Builder {
			
			private Class<?> clazz;
			private Parser parser;
			private Back<Attribute> called;
			private List<XmlInfo> infos;
			
			public ParamsBuilder setInfos(List<XmlInfo> infos) {
				this.infos = infos;
				return this;
			}

			public ParamsBuilder setClazz(Class<?> clazz) {
				this.clazz = clazz;
				return this;
			}

			public ParamsBuilder setParser(Parser parser) {
				this.parser = parser;
				return this;
			}

			public ParamsBuilder setCalled(Back<Attribute> called) {
				this.called = called;
				return this;
			}

			@Override
			public Params builder() {
				return new Params(infos, clazz, parser, called);
			}
		}
	}
}
