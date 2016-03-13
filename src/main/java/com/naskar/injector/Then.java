package com.naskar.injector;

public abstract class Then {

	public static Factory usingSingleton() {
		return new Factory() {
			
			private Object instance = null;
			
			@Override
			public Object create(Injector injector, Class<?> clazz) {
				
				if(instance == null) {
					try {		
						instance = clazz.newInstance();
							
					} catch(Exception e) {
						throw new RuntimeException(e);
					}	
				}
				
				return instance;
			}
		};		
	}
	
	public static Factory usingPrototype() {
		return (injector, clazz) -> {
			try {
				
				return clazz.newInstance();
						
			} catch(Exception e) {
				throw new RuntimeException(e);
			}
			
		};
	}

}
