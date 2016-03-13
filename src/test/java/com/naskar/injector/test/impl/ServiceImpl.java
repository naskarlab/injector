package com.naskar.injector.test.impl;

import com.naskar.injector.test.Repository;
import com.naskar.injector.test.Service;

public class ServiceImpl implements Service {
	
	private Repository repository;
	
	@Override
	public Repository getRepository() {
		return this.repository;
	}

}
