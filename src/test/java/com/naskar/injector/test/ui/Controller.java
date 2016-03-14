package com.naskar.injector.test.ui;

import com.naskar.injector.Inject;
import com.naskar.injector.Reference;
import com.naskar.injector.test.service.Service;

@Reference
public class Controller {
	
	private String tt;
	
	@Inject
	private Service service;
	
	public Service getService() {
		return service;
	}

}
