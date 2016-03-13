package com.naskar.injector;

import java.lang.reflect.Field;

public abstract class By {
	
	public static Injector property(FieldFilter filter) {
		return (ctx, instance) -> {
			try {
				
				for(Field field : instance.getClass().getDeclaredFields()) {
					if(filter.filter(field)) {
						field.setAccessible(true);
						field.set(instance, ctx.resolve(field.getType()));
						field.setAccessible(false);
					}
				}
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

}
