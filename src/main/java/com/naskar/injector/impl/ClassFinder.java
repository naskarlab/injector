package com.naskar.injector.impl;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.function.BiConsumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassFinder {
	
	public static void forEachClass(ClassLoader loader, final BiConsumer<ClassLoader, String> call) {
		try {
			
			Enumeration<URL> urls = loader.getResources("");
			
			while(urls.hasMoreElements()) {
				URL url = urls.nextElement();
				URI uri = url.toURI();
				if("jar".equalsIgnoreCase(uri.getScheme())) {
					findClassInJar(loader, call, uri);
					
				} else {
					findClass(loader, call, uri);
					
				}
				
			}
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static void findClassInJar(ClassLoader loader, BiConsumer<ClassLoader, String> call, URI uri) throws IOException {
		
		String path = uri.getRawSchemeSpecificPart().replace("!/","").replace("file:/","");
		
		JarFile file = new JarFile(path);
		Enumeration<JarEntry> entries = file.entries();
		while(entries.hasMoreElements()) {
			JarEntry entry = entries.nextElement();
			
			String name = entry.getName();
			if(name.endsWith(".class")) {
				
				String className = 
					name.replace("/", ".").replace(".class", "");
				
				try {
					
					call.accept(loader, className);
					
				} catch (Exception e) {
					// TODO: logger
					System.out.println("Classe não encontrada: " + className);
					
				}
			}
		}
	}

	private static void findClass(ClassLoader loader, final BiConsumer<ClassLoader, String> call, URI uri) throws IOException {
		Path urlPath = Paths.get(uri);
		String root = urlPath.toFile().getAbsolutePath();
		
		Files.find(urlPath, Integer.MAX_VALUE, (path, options) -> 
			String.valueOf(path).endsWith(".class")).forEach((path) -> {
				
				String file = path.toFile().getAbsolutePath();
				
				String className = file
					.replace(root + File.separator, "")
					.replace(".class", "")
					.replace(File.separator, ".");
				
				try {
					
					call.accept(loader, className);
					
				} catch (Exception e) {
					// TODO: logger
					System.out.println("Classe não encontrada: " + className);
					
				}
				
			});
	}

}
