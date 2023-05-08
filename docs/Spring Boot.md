ORMs
--
### JPA
Java Persistence API. It is simply a specification / technique for ORM and does not do anything on its own.

### Hibernate
Implements the JPA implementation and does the heavy lifting. Is an open-source, lightweight Java framework.

Annotations
--
Are a replacement for XML configs.

### @Component
This class' lifecycle is managed by the Spring IoC container.
See also: ```@Controller @Service @Repository @RestController```
> applies to: class


### @Bean
Similar to ```@Component``` but for methods instead.
> applies to: methods


### @Autowired
This variable/method etc has a datatype that is also an existing component. Tells Spring Boot to load in the correct dependency when needed.

Note that if a class has only 1 constructor, all of its attributes will be implicitly @Autowired, and this is the recommended best practice. https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.spring-beans-and-dependency-injection
> Benefits:
> 
>> old way: ```private HiService service = new GoodMorningService;```
> 
>> new way: ```@Autowired | private HiService service;```

> applies to: member variables, constructors, or setter methods.

### @Entity
This class is an object that should be stored in the database.
> applies to: class

### @Table(name = [])
This class in the database should have a name of []. The default name used by ```@Entity``` is the class name, e.g. ConfirmationToken --> confirmation_token
Note this does not work without ```@Entity```
> applies to: class

### @Column(name = [])
This attribute in the database should have a name of []. The default name used by ```@Entity``` is the method name, e.g. UserId --> user_id
Note this does not work without ```@Entity```
> applies to: attribute

### @Transient
This attribute will not be a column in the db.
> applies to: attribute

### @Modifying
Queries that require a ```@Modifying``` annotation include INSERT, UPDATE, DELETE, and DDL statements.
> applies to: methods in repository

### @Transactional
Mark an operation as an ACID transaction. Note that by default, CRUD methods on repository instances inherited from SimpleJpaRepository are transactional. 
See also: https://stackoverflow.com/questions/1079114/where-does-the-transactional-annotation-belong
> applies to: usually, methods in service layer.

### @Configuration
This class contains info for configuring a context.
> applies to: class

### @ComponentScan(basePackages = "...")
Which packages Spring needs to scan to locate the components 
> applies to: testing vs prod

### @RunWith(SpringJUnit4ClassRunner.class)
### @ContextConfiguration(classes = a class with @Configuration)
