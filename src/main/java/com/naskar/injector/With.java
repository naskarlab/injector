package com.naskar.injector;

public abstract class With {

	public static Registrator allInterfaces() {
		return (ctx, clazz, factory) -> {
			
			for(Class<?> interfaceClazz : clazz.getInterfaces()) {
				ctx.register(interfaceClazz, (ct, c) -> {
					return factory.create(ct, clazz);	
				});
			}
			
		};
	}

	public static Registrator asClass() {
		return (ctx, clazz, factory) -> {
			ctx.register(clazz, factory);			
		};
	}

}
