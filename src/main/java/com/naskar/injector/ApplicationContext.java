package com.naskar.injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.naskar.injector.impl.ClassFinder;

public class ApplicationContext {
	
	private class Entry {
		ClassFilter filter;
		Factory factory;
		Registrator registrator;
		public Entry(ClassFilter filter, Factory factory, Registrator registrator) {
			this.filter = filter;
			this.factory = factory;
			this.registrator = registrator;
		}
	};
	
	private Map<String, Factory> factories;
	
	private List<ClassFilter> includes;
	private List<ClassFilter> excludes;
	private List<Entry> registers;
	private List<Injector> injectors;
	
	public ApplicationContext() {
		this.includes = new ArrayList<ClassFilter>();
		this.excludes = new ArrayList<ClassFilter>();
		this.registers = new ArrayList<Entry>();
		this.injectors = new ArrayList<Injector>();
		
		this.factories = new HashMap<String, Factory>();
	}

	public ApplicationContext include(ClassFilter filter) {
		includes.add(filter);
		return this;
	}

	public ApplicationContext exclude(ClassFilter filter) {
		excludes.add(filter);
		return this;
	}
	
	public ApplicationContext register(ClassFilter filter, Factory factory, Registrator registrator) {
		registers.add(new Entry(filter, factory, registrator));
		return this;
	}
	
	public ApplicationContext create() {
		this.factories.clear();
		
		ClassFinder.forEachClass(this.getClass().getClassLoader(), (c) -> {
			
			if(includes.stream().allMatch(i -> i.filter(c))
				&& !excludes.stream().anyMatch(i -> i.filter(c))) {
				
				registers.forEach((e) -> {
					
					if(e.filter.filter(c)) {
						
						e.registrator.register(this, c, e.factory);
						
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
