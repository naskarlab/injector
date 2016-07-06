package com.naskar.injector;

public interface Resolver {
	
	<T> T resolve(Class<T> clazz);

}
