// [variable] means you should be inputting whatever that variable is for your scenario

Compile-Time
--

### 'psql' is not recognized as an internal or external command, operable program or batch file.
> #### ❓ Why: 
> > psql (or whichever relational database you are using) is not installed properly.

> #### ✅ Fix:
> ```aidl
> 1) Install psql (remember the [username] & [password] you set here! It is for the superuser)
> 2) Make sure psql exists in your PATH environment variable
> ```


### psql: FATAL:  role [your name] does not exist
> #### ❓ Why:
> > you are trying to connect to the database using credentials that don't exist.

> #### ✅ Fix:
> ```aidl
> 1) Open application.yml
> 2) Check spring: > datasource: > [username] & [password]
> 3) Make sure [username] & [password] here matches what you input during psql installation
> ```

### org.postgresql.util.PSQLException: FATAL: database "db" does not exist
> #### ❓ Why:
> > your postgresql does not have a database already created.

> #### ✅ Fix:
> ```aidl
> 1) Open application.yml
> 2) Check spring: > datasource: > url: jdbc:postgresql://localhost:5432/[SOMETHING_HERE]
> 3) That [SOMETHING_HERE], e.g. db, is your database name. This needs to exist in postgresql
> 4) In cmd, type 'psql -U [username] -h localhost'
> 5) Type [password]
> ```

### [] is not a managed type.
> #### ❓ Why:
> > Making use of ```@Table``` but not ```@Entity``` for an entity. Note ```@Entity``` is a prerequisite for ```@Table``` to work. See ```Spring Boot.md```

> #### ✅ Fix:
> ```aidl
> 1) Put @Entity on top of the class causing the error.
> ```

### Validation failed for query for method public abstract []
> #### ❓ Why:
> > The PSQL statement written is wrong.

> #### ✅ Fix:
> ```aidl
> Double check names of tables (should be name of class) and columns (should be name of attributes)
> ```

### Executing an update/delete query; nested exception is javax.persistence.TransactionRequiredException: Executing an update/delete query
> #### ❓ Why:
> > You cannot run update/delete query in that file, e.g. in a seeder file that implements CommandLineRunner

> #### ✅ Fix:
> ```aidl
> 1) Locate the source file and exact line causing the error (go down the stack trace).
> 2) Refactor your code to not call update/delete statements there.
> ```

### Could not determine type for java.util.list
> #### ❓ Why:
> You cannot store a list in a database column because it likely breaks normalisation (multi-valued attribute), so Spring Boot tells you it is an error.

> #### ✅ Fix:
> ```aidl
> 1) If the list refers to child entities, add @OneToMany to the list
> 2) If the list is intentional, all @ElementCollection to tell Spring Boot it's okay
> ```

Run-Time (Postman)
--
### "message": "No default constructor for entity:  : com.frailty.backend ...
> #### ❓ Why:
> > When Hibernate (see ```Spring Boot.md```) tries to create a bean, it does so via reflection:
> > 1. Call the no-arg constructor to create the object
> > 2. Use setter methods to set the properties.
> >
> > Without them, Hibernate cannot perform read operations on the db since it cannot create the classes from the data.

> #### ✅ Fix:
> ```aidl
> 1) Define the no-arg constructor. This can be done using Lombok's @NoArgConstructor.
> 2) Define all setters. This can be done using Lombok's @Setters.
> ```