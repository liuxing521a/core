package org.itas.core.util;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import javassist.ClassPool;
import javassist.CtClass;

public interface CtClassLoader {

  default List<Path> loadFile(String packageName, String suffix) throws Exception {
	final Path rootPath = Paths.get(ClassLoader.class.getResource("/").toURI());
	final PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:*" + suffix);
	final Path path = Paths.get(rootPath.toString(), packageName.replace(".", File.separator));
		
	final List<Path> pathList = new ArrayList<Path>(path.getNameCount());
	Files.walkFileTree(path, new SimpleFileVisitor<Path>(){
	  public FileVisitResult visitFile(Path file, 
	      BasicFileAttributes attrs) throws java.io.IOException {
	    if (matcher.matches(file.getFileName())) {
	    	pathList.add(file);
		}
	    
	    return super.visitFile(path,attrs);
	  };
	});

	return pathList;
  }
	
  default CtClass[] loadCtClass(String packageName) throws Exception {
	final List<Path> fileList =  loadFile(packageName, ".class"); 
	final CtClass[] ctClasss = new CtClass[fileList.size()];
	
	final ClassPool classPool = ClassPool.getDefault();
	for (int i = 0; i < fileList.size(); i ++) {
	  ctClasss[i] = classPool.get(
	      String.format("%s.%s", packageName, fileList.get(i).getFileName()));
	}
	
	return ctClasss;
  }
}
