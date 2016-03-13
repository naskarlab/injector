package com.naskar.injector.impl;

import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.function.Consumer;

public class ClassFinder {
	
	public static void forEachClass(ClassLoader loader, final Consumer<Class<?>> call) {
		try {
			
			Enumeration<URL> urls = loader.getResources("");
			
			while(urls.hasMoreElements()) {
				URL url = urls.nextElement();
				Path urlPath = Paths.get(url.toURI());
				String root = urlPath.toFile().getAbsolutePath();
				
				Files.find(urlPath, Integer.MAX_VALUE, (path, options) -> 
					String.valueOf(path).endsWith(".class")).forEach((path) -> {
						
						String file = path.toFile().getAbsolutePath();
						
						String className = file
							.replace(root + File.separator, "")
							.replace(".class", "")
							.replace(File.separator, ".");
						
						try {
							
							call.accept(loader.loadClass(className));
							
						} catch (ClassNotFoundException e) {
							// TODO: logger
							System.out.println("Classe não encontrada: " + className);
							
						}
						
					});
				
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
