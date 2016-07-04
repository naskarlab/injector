package com.naskar.injector.impl;

import com.naskar.injector.ClassFilter;
import com.naskar.injector.ClassNameFilter;

public class ClassLoaderNameFilter implements ClassNameFilter {
	
	private ClassFilter filter;
	
	public ClassLoaderNameFilter(ClassFilter filter) {
		this.filter = filter;
	}
	
	@Override
	public boolean filter(String className) {
		try {
			
			Class<?> clazz = this.getClass().getClassLoader().loadClass(className);
			return filter.filter(clazz);
			
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
