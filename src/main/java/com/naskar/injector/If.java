package com.naskar.injector;

import java.lang.annotation.Annotation;

public abstract class If {
	
	public static ClassFilter implementsInterface() {
		return (clazz) -> {
			return !clazz.isInterface() && clazz.getInterfaces().length > 0;
		};
	}
	
	public static ClassFilter implementsInterface(Class<?> interfaceClass) {
		return (clazz) -> {
			return interfaceClass.isAssignableFrom(clazz);
		};
	}
	
	public static ClassFilter withAnnotation(Class<? extends Annotation> clazz) {
		return (c) -> {
			return c.getAnnotation(clazz) != null;
		};
	}
	
	public static FieldFilter allFields() {
		return (field) -> {
			return true;
		};
	}
	
	public static FieldFilter hasAnnotation(Class<? extends Annotation> clazz) {
		return (field) -> {
			return field.getAnnotation(clazz) != null;
		};
	}

}
