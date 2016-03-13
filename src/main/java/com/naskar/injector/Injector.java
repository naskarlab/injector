package com.naskar.injector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.naskar.injector.impl.ClassFinder;

public class Injector {
	
	private class Entry {
		Filter filter;
		Factory factory;
		Registrator registrator;
		public Entry(Filter filter, Factory factory, Registrator registrator) {
			this.filter = filter;
			this.factory = factory;
			this.registrator = registrator;
		}
	};
	
	private Map<Class<?>, Factory> scope;
	
	private List<Filter> includes;
	private List<Filter> excludes;
	private List<Entry> registers;
	
	public Injector() {
		this.includes = new ArrayList<Filter>();
		this.excludes = new ArrayList<Filter>();
		this.registers = new ArrayList<Entry>();
		
		this.scope = new HashMap<Class<?>, Factory>();
	}

	public Injector include(Filter filter) {
		includes.add(filter);
		return this;
	}

	public Injector exclude(Filter filter) {
		excludes.add(filter);
		return this;
	}
	
	public Injector register(Filter filter, Factory factory, Registrator registrator) {
		registers.add(new Entry(filter, factory, registrator));
		return this;
	}
	
	public Injector create() {
		this.scope.clear();
		
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
		Factory f = this.scope.get(clazz);
		if(f != null) {
			o = (T)f.create(this, clazz);
		}
		return o;
	}

	public void register(Class<?> clazz, Factory factory) {
		this.scope.put(clazz, factory);
	}
	
}
