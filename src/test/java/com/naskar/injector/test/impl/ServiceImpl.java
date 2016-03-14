package com.naskar.injector.test.impl;

import com.naskar.injector.Inject;
import com.naskar.injector.Reference;
import com.naskar.injector.test.Repository;
import com.naskar.injector.test.Service;

@Reference
public class ServiceImpl implements Service {
	
	@Inject
	private Repository repository;
	
	@Override
	public Repository getRepository() {
		return this.repository;
	}

}
