package com.naskar.injector;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.naskar.injector.test.Service;
import com.naskar.injector.test.impl.ServiceImpl;

public class InjectTest {
	
	private Injector injector;
	
	@Before
	public void setup() {
		this.injector = new Injector()
			.include(x -> x.getName().startsWith("com.naskar.injector.test"))
			.exclude(x -> 
				Arrays.asList(x.getMethods()).stream()
					.anyMatch(m -> m.getAnnotation(Test.class) != null))
			.register(If.implementsInterface(), Then.usingSingleton(), With.allInterfaces())
			.register(x -> x.getName().endsWith("Controller"), Then.usingPrototype(), With.asClass())
			.create()
		;
	}
	
	/*
	@Test
	public void testControllerInjectService() {
		Controller target = ctx.getInstance(Controller.class);
		
		Assert.assertTrue(target.getService() != null);
	}
	
	@Test
	public void testServiceInjectRepository() {
		Service target = ctx.getInstance(Service.class);
		
		Assert.assertTrue(target.getRepository() != null);
	}
	*/
	
	@Test
	public void testServiceInstance() {
		Service target = injector.resolve(Service.class);
		
		Assert.assertTrue(target instanceof ServiceImpl);
	}
	
	/*
	@Test
	public void testRepositoryInstance() {
		Repository target = ctx.getInstance(Repository.class);
		
		Assert.assertTrue(target instanceof RepositoryImpl);
	}
	*/

}
