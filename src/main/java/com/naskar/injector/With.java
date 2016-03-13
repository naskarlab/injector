package com.naskar.injector;

public abstract class With {

	public static Registrator allInterfaces() {
		return (injector, clazz, factory) -> {
			
			for(Class<?> interfaceClazz : clazz.getInterfaces()) {
				injector.register(interfaceClazz, (i, c) -> {
					return factory.create(i, clazz);	
				});
			}
			
		};
	}

	public static Registrator asClass() {
		return (injector, clazz, factory) -> {
			injector.register(clazz, factory);			
		};
	}

}
