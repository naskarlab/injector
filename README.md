# Injector

Dependency Injection without dependency with Injector. 

## Features

* Configuration over code: independence business code of the infrastructure code;
* Intrusive-less: zero or less changes for your code;
* Glue code: it’s only a small and simple classes set;
* Fluent Builder: code complete is your friend!

Documentation: [naskar lab](http://lab.naskar.com.br/2016/03/injector-dependency-injection-without-dependency-with-injector/)

## Using

```
<dependencies>
	<dependency>
		<groupId>com.naskar</groupId>
		<artifactId>injector</artifactId>
		<version>1.0.0</version>
	</dependency>
</dependencies>
<repositories>
	<repository>
		<snapshots>
			<enabled>false</enabled>
		</snapshots>
		<id>central</id>
		<name>libs-release</name>
		<url>http://repo.naskar.com.br/dist/libs-release</url>
	</repository>
</repositories>
```	


## Examples

```

@Before
public void setup() {
	this.ctx = new ApplicationContext()
		.include(x -> x.getName().startsWith("com.naskar.injector.test"))
		.register(If.implementsInterface(), Then.usingSingleton(), With.allInterfaces())
		.register(x -> x.getName().endsWith("Controller"), Then.usingPrototype(), With.asClass())
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
 
```

 