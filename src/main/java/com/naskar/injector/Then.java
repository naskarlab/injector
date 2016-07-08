package com.naskar.injector;

import java.util.HashMap;
import java.util.Map;

public abstract class Then {
	
	private static Map<Class<?>, Object> scope = new HashMap<Class<?>, Object>();

	public static Factory usingSingleton() {
		return new Factory() {
			
			@Override
			public Object create(ApplicationContext ctx, Class<?> clazz) {
				
				Object instance = scope.get(clazz);
				if(instance == null) {
					try {		
						instance = clazz.newInstance();
						scope.put(clazz, instance);
							
					} catch(Exception e) {
						throw new RuntimeException(e);
					}	
				}
				
				return instance;
			}
		};		
	}
	
	public static Factory usingPrototype() {
		return (ctx, clazz) -> {
			try {
				
				return (Object)clazz.newInstance();
						
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
			
		};
	}

}
