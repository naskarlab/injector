package com.naskar.injector;

public interface Factory {
	
	Object create(Injector injector, Class<?> clazz);
	
}
