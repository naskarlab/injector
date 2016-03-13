package com.naskar.injector;

public class If {
	
	public static Filter implementsInterface() {
		return (clazz) -> {
			return !clazz.isInterface() && clazz.getInterfaces().length > 0;
		};
	}

}
