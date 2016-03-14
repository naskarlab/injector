package com.naskar.injector;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.naskar.injector.test.repository.Repository;
import com.naskar.injector.test.repository.impl.RepositoryImpl;
import com.naskar.injector.test.service.Service;
import com.naskar.injector.test.service.impl.ServiceImpl;
import com.naskar.injector.test.ui.Controller;

public class InjectAnnotationTest {
	
	private ApplicationContext ctx;
	
	@Before
	public void setup() {
		this.ctx = new ApplicationContext()
			.include(x -> x.getName().startsWith("com.naskar.injector.test"))
			.register(If.withAnnotation(Reference.class), Then.usingSingleton(), With.allInterfaces())
			.register(x -> 
				x.getAnnotation(Reference.class) != null 
				&& x.getName().endsWith("Controller"), Then.usingPrototype(), With.asClass())
			.inject(By.property(If.allFields()))
			.create()
		;
	}
	
	@Test
	public void testControllerInjectService() {
		Controller target = ctx.resolve(Controller.class);
		
		Assert.assertTrue(target.getService() != null);
	}
	
	@Test
	public void testServiceInjectRepository() {
		Service target = ctx.resolve(Service.class);
		
		Assert.assertTrue(target.getRepository() != null);
	}
	
	@Test
	public void testServiceInstance() {
		Service target = ctx.resolve(Service.class);
		
		Assert.assertTrue(target instanceof ServiceImpl);
	}
	
	@Test
	public void testRepositoryInstance() {
		Repository target = ctx.resolve(Repository.class);
		
		Assert.assertTrue(target instanceof RepositoryImpl);
	}

}
