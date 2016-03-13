package com.naskar.injector;

public interface Factory {
	
	Object create(ApplicationContext ctx, Class<?> clazz);
	
}
