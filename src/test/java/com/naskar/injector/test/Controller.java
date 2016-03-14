package com.naskar.injector.test;

import com.naskar.injector.Inject;
import com.naskar.injector.Reference;

@Reference
public class Controller {
	
	private String tt;
	
	@Inject
	private Service service;
	
	public Service getService() {
		return service;
	}

}
