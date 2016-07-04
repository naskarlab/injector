package com.naskar.injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.naskar.injector.impl.ClassFinder;
import com.naskar.injector.impl.ClassLoaderNameFilter;

public class ApplicationContext {
	
	private class Entry {
		ClassNameFilter filter;
		Factory factory;
		Registrator registrator;
		public Entry(ClassNameFilter filter, Factory factory, Registrator registrator) {
			this.filter = filter;
			this.factory = factory;
			this.registrator = registrator;
		}
	};
	
	private Map<String, Factory> factories;
	
	private List<ClassNameFilter> includes;
	private List<ClassNameFilter> excludes;
	private List<Entry> registers;
	private List<Injector> injectors;
	
	public ApplicationContext() {
		this.includes = new ArrayList<ClassNameFilter>();
		this.excludes = new ArrayList<ClassNameFilter>();
		this.registers = new ArrayList<Entry>();
		this.injectors = new ArrayList<Injector>();
		
		this.factories = new HashMap<String, Factory>();
	}
	
	public ApplicationContext add(ClassNameFilter filter) {
		includes.add(filter);
		return this;
	}
	
	public ApplicationContext remove(ClassNameFilter filter) {
		excludes.add(filter);
		return this;
	}

	public ApplicationContext include(ClassFilter filter) {
		includes.add(new ClassLoaderNameFilter(filter));
		return this;
	}

	public ApplicationContext exclude(ClassFilter filter) {
		excludes.add(new ClassLoaderNameFilter(filter));
		return this;
	}
	
	public ApplicationContext register(ClassFilter filter, Factory factory, Registrator registrator) {
		registers.add(new Entry(new ClassLoaderNameFilter(filter), factory, registrator));
		return this;
	}
	
	public ApplicationContext create() {
		this.factories.clear();
		
		ClassFinder.forEachClass(this.getClass().getClassLoader(), (loader, className) -> {
			
			if(includes.stream().allMatch(i -> i.filter(className))
				&& !excludes.stream().anyMatch(i -> i.filter(className))) {
				
				registers.forEach((e) -> {
					
					if(e.filter.filter(className)) {
						
						try {
							e.registrator.register(this, loader.loadClass(className), e.factory);
						} catch (Exception e1) {
							// TODO: logger
							throw new RuntimeException(e1);
						}
						
					}
					
				});
						
				
			}
			
		});
				
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T resolve(Class<T> clazz) {
		T o = null;
		Factory f = this.factories.get(clazz.getName());
		if(f != null) {
			o = (T)f.create(this, clazz);
			
			for(Injector i : this.injectors) {
				i.inject(this, o);
			}
		}
		
		return o;
	}

	public void register(Class<?> clazz, Factory factory) {
		this.factories.put(clazz.getName(), factory);
	}

	public ApplicationContext inject(Injector injector) {
		this.injectors.add(injector);
		return this;
	}
	
}
