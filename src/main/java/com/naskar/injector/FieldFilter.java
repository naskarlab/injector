package com.naskar.injector;

import java.lang.reflect.Field;

public interface FieldFilter {
	
	boolean filter(Field field);
	
}
