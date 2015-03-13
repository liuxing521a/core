//package org.itas.core;
//
//import java.lang.reflect.Modifier;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.itas.util.ItasException;
//import org.itas.util.Logger;
//import org.itas.util.Utils.ClassUtils;
//import org.itas.util.Utils.Objects;
//import org.itas.util.XResources;
//
//import com.typesafe.config.Config;
//
//final class ConfigPoolImpl {
//
//	/** 资源目录*/
//	private String dir;
//	
//	/** 所有对象集合*/
//	private final Map<String, Resource> allResources;
//	
//	/** 分类列表*/
//	private final Map<Class<?>, List<Resource>> childResources;
//
//	private ConfigPoolImpl() {
//		this.childResources =new HashMap<>();
//		this.allResources = new HashMap<>(2048);
//	}
//	
//
//	public final Resource get(String Id) {
//		if (Objects.isEmpty(Id)) {
//			return null;
//		}
//		
//		return allResources.get(Id);
//	}
//
//	public final List<Resource> get(Class<? extends Resource> clazz) {
//        return Collections.unmodifiableList(childResources.get(clazz));
//	}
//
//	@SuppressWarnings("unchecked")
//	public void bingResource(String pack, Config share) throws Exception {
//		dir = share.getString("dir");
//
//		ResourceHandler handler = new ResourceHandler();
//		List<Map<String, String>> attrs = new ArrayList<>(512);
//		
//		List<Class<?>> classList = ClassUtils.loadClazz(Resource.class, pack);
//		for (Class<?> clazz : classList) {
//			try {
//				if (Modifier.isAbstract(clazz.getModifiers())) {
//					continue;
//				}
//				String path = String.join("", dir, "/source/", clazz.getSimpleName() + ".xml");
//				
//				List<Map<String, String>> attributeList = handler.getXml((Class<? extends Resource>)clazz, XResources.getResourceAsInputStream(path));
//				List<Resource> childList =  getSourceList(clazz, attributeList.size());
//				attrs.addAll(attributeList);
//				
//				for (Map<String, String> attribute : attributeList) {
//					Resource source = ClassUtils.newInstance(clazz, new String[]{attribute.get("Id")}, new Class[]{String.class});
//					childList.add(source);
//					
//					Resource tmpsource = allResources.put(source.getId(), source);
//					if (Objects.nonNull(tmpsource)) {
//						throw new DoubleException("class:" + tmpsource.getClass().getSimpleName() + " and class:" + clazz.getSimpleName() + " with same id=" + source.getId());
//					}
//				}
//			} catch (Exception e) {
//				throw new ItasException(e);
//			}
//		}
//		
//		for (Map<String, String> attribute : attrs) {
//			allResources.get(attribute.get("Id")).load(null, attribute);
//		}
//		Logger.trace("res size is :{}", allResources.size());
//	}
//	
//	public void reload() throws Exception {
//		ResourceHandler handler = new ResourceHandler();
//
//		for (Resource res : allResources.values()) {
//			try {
//				if (Modifier.isAbstract(res.getClass().getModifiers())) {
//					continue;
//				}
//				
//				String path = String.join("", dir, "/source/", res.getClass().getSimpleName() + ".xml");
//				
//				List<Map<String, String>> attributeList = handler.getXml((Class<? extends Resource>)res.getClass(), XResources.getResourceAsInputStream(path));
//				for (Map<String, String> attribute : attributeList) {
//					allResources.get(attribute.get("Id")).load(null, attribute);
//				}
//			} catch (Exception e) {
//				throw new ItasException(e);
//			}
//		}
//	}
//	
//	@SuppressWarnings("unchecked")
//	public void loadConfig(String pack, Config share) throws Exception {
//		ConfigHandler handler = new ConfigHandler();
//		List<Class<?>> clazzes = ClassUtils.loadClazz(Config.class, pack);
//		
//		String path;
//		String dir = share.getString("dir");
//		for (final Class<?> clazz : clazzes) {
//			if (!Modifier.isAbstract(clazz.getModifiers())) {
//				path = String.join("", dir, "/config/", clazz.getSimpleName() + ".xml");
//				handler.getXml((Class<? extends Config>)clazz, XResources.getResourceAsInputStream(path));
//			}
//		}
//	}
//	
//	private List<Resource> getSourceList(Class<?> clazz, int size) {
//		List<Resource> childList =  childResources.get(clazz);
//		
//		if (Objects.isNull(childList)) {
//			childList = new ArrayList<>(size);
//			childResources.put(clazz, childList);
//		}
//		
//		return childList;
//	}
//}
