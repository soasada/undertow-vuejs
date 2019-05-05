# Web application demo with Undertow + Vuejs

**Inspired in https://github.com/jonashackt/spring-boot-vuejs**

The intention of this repo is to give you a seed project (or example project?) to build single page web applications with undertow and vuejs.

The project consists in two modules: **backend** and **frontend**.

## Backend

In the backend module we have the following dependencies:

* WEB SERVER: **Undertow**.
* RDBMS: **MySQL** (provided in a docker image).
* DB CONNECTION POOL: **HikariCP**.
* IMMUTABLE MODELS: **Lombok**.
* JSON: **Jackson**.
* HTTP AUTHORIZATION: **JWT**.
* TESTING: **JUnit 5**.

**Spring JDBC** is optional, in this project you can remove it, but you are going to have problems with aliases in the SQL queries.
One of the solutions is to put prefixes to the column names of the database tables. More info: https://stackoverflow.com/questions/15184709/cachedrowsetimpl-getstring-based-on-column-label-throws-invalid-column-name

### 1. Web server

The main class (Application.java) has all the logic to run undertow over HTTPs and the redirection from HTTP to HTTPs.
It is a good idea to have all configurations in separate files (inside resources folder) and within the version control.
In the main class we have a class that loads the server configuration from the `app.properties` file.

### 2. Database

Sometimes we need persistent storage, in this example project I used MySQL as RDBMS and HikariCP. HikariCP support configuration
files, **db_prod_pool.properties** and **db_test_pool.properties** are used for HikariCP configuration for the production and
testing database.

## Features

* HTTPs/HTTP2.
* Redirection from HTTP to HTTPs.
* Gzip compression for static files.
* Authorization via JWT.
* Proxy all frontend requests to our backend.
* Single artifact deploy.
* Minimal JDBC abstraction.
* Migrations.
* Database testing with docker.
* Immutable JSON messages.
* Application configuration.
* OpenJDK 12 support.
* Easy backend and frontend testing.

## Steps to deploy

0. Download JDK12 from: https://jdk.java.net/12/ and put in your machine at:

`/opt/prod_jdk`

Maven will use this folder in order to compile the project, this allow us to update easily the JDK version.
If you want to have the `java` as a command without typing `/opt/prod_jdk/bin/java` you can do this:

`sudo ln -s /opt/prod_jdk/bin/java /usr/local/bin/java`

or (ubuntu based) add this line at the end of your ~/.bashrc:

`export PATH=/opt/apache-maven-3.6.1/bin:$PATH` (for maven)\
`export JAVA_HOME=/opt/prod_jdk`\
`export PATH=$PATH:$JAVA_HOME/bin`

1. Compile Vuejs project and generate static files.

`mvn -U clean install -pl :frontend`

2. Remove previous static files.

`rm -rf backend/src/main/resources/public`

3. Test migrations.

`mvn -U clean -pl :backend -Dflyway.configFiles=src/main/resources/database/test_migrations.properties flyway:migrate`

4. Compile backend, move static files to public/ and generate jar file (deployable artifact).

`export APP_ENV=test && mvn -U clean compile test package -pl :backend`

5. Run application (change APP_ENV with `prod` or `test`).

`export APP_ENV=test && java -jar backend/target/backend-0.0.1.jar`

**NOTE:** There is a script to deploy locally (_deploy.sh_), **use it for development only!**.

## Remember to add to router the routes of vue-router in order to avoid 404.

## Manifesto

You don't need a backend framework or an ORM to be able to make web applications, **why not plain java?**.
_"Don't reinvent the wheel"_ they say, and you pick a framework, now you have a **bicycle** with: a washing machine, an embedded
microwave and a neon light beacon, a very cool bicycle!.

**Wait a moment... why are you saying this about frameworks?**

The problem isn't frameworks, it's us (developers). **Try to learn the basics first, later learn a framework**.
Learn things like OOP, FP, HTTP, client-server model, plain SQL, VMs, containers... and then, when you know these things, when you are tired 
of doing these things without no help, when you are tired of doing repetitive things only then, take a framework.

Many of the problems that you are going to have with a framework, is due to the lack of knowledge of the framework itself or
**the basics**. Soon you will have a good amount of spaghetti code unmaintainable, with some modules of your project that 
fits the _"framework boundaries"_ and others that use hacks to fit these boundaries with your knowledge of the language,
resulting in performance issues, cryptic errors, updating problems, slow development and more... 