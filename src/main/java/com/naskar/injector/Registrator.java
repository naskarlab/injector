package com.naskar.injector;

public interface Registrator {
	
	void register(Injector injector, Class<?> clazz, Factory factory);
	
}
