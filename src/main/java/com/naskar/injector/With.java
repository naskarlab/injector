package com.naskar.injector;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.function.BiFunction;

public abstract class With {
	
	public static Registrator all() {
		return (ctx, clazz, factory) -> {

			ctx.register(clazz, factory);
			for(Class<?> interfaceClazz : clazz.getInterfaces()) {
				ctx.register(interfaceClazz, (ct, c) -> {
					return factory.create(ct, clazz);
				});
			}
			
		};
	}

	public static Registrator allInterfaces() {
		return (ctx, clazz, factory) -> {
			
			for(Class<?> interfaceClazz : clazz.getInterfaces()) {
				ctx.register(interfaceClazz, (ct, c) -> {
					return factory.create(ct, clazz);
				});
			}
			
		};
	}
	
	public static Registrator allInterfaces(BiFunction<ApplicationContext, Object, InvocationHandler> handlerFactory) {
		return (ctx, clazz, factory) -> {
			for(Class<?> interfaceClazz : clazz.getInterfaces()) {
				ctx.register(interfaceClazz, (ct, c) -> {
					
					Object instance = factory.create(ct, clazz);
					InvocationHandler handler = handlerFactory.apply(ct, instance);
					return Proxy.newProxyInstance(clazz.getClassLoader(), 
						new Class[] { interfaceClazz }, handler);
					
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
