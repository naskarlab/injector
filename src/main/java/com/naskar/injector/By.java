package com.naskar.injector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.function.BiConsumer;

public abstract class By {
	
	public static Injector property(FieldFilter filter) {
		return (ctx, instance) -> {
			
			forEachField(instance, (ri, field) -> {
				try {
					if(filter.filter(field)) {
						Object i = ctx.resolve(field.getType());
						if(i != null) {
							field.setAccessible(true);
							field.set(ri, i);
							field.setAccessible(false);
						}
					}
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			});
			
		};
	}
	
	private static void forEachField(Object instance, BiConsumer<Object, Field> action) {
		try {
			Object realInstance = instance;
			
			if(Proxy.isProxyClass(instance.getClass())) {
				InvocationHandler ih = Proxy.getInvocationHandler(instance);
				if(ih != null) {
					Method m = ih.getClass().getMethod("getTarget", new Class[] {});
					if(m != null) {
						realInstance = m.invoke(ih);
					}
				}
			}
			
			for(Field field : realInstance.getClass().getDeclaredFields()) {
				action.accept(realInstance, field);
			}
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
