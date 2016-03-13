package com.naskar.injector;

public interface Registrator {
	
	void register(ApplicationContext ctx, Class<?> clazz, Factory factory);
	
}
