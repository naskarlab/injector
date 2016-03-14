package com.naskar.injector.test.service.impl;

import com.naskar.injector.Inject;
import com.naskar.injector.Reference;
import com.naskar.injector.test.repository.Repository;
import com.naskar.injector.test.service.Service;

@Reference
public class ServiceImpl implements Service {
	
	@Inject
	private Repository repository;
	
	@Override
	public Repository getRepository() {
		return this.repository;
	}

}
