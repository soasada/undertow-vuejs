# Web application demo with Undertow + Vue.js

**Inspired in https://github.com/jonashackt/spring-boot-vuejs**

Example project to build single page web applications with [Undertow](http://undertow.io) and [Vue.js](https://vuejs.org).

The project consists in two modules: **backend** and **frontend**.

## Backend

In the backend module we have the following dependencies:

* WEB SERVER: **Undertow**.
* RDBMS: **MySQL** (provided in a docker image).
* DB CONNECTION POOL: **HikariCP**.
* IMMUTABLE MODELS: **Lombok**.
* JSON: **Jackson**.
* HTTP AUTHORIZATION: **JWT**.
* LOGGING: **Log4j2**.
* TESTING: **JUnit 5**.

**Spring JDBC** is optional, in this project you can remove it, but you are going to have problems with aliases in the SQL queries.
One of the solutions is to put prefixes to the column names of the database tables. More info: https://stackoverflow.com/questions/15184709/cachedrowsetimpl-getstring-based-on-column-label-throws-invalid-column-name

**Guava** is optional, is part of a proof of concept with [RequestHandler.java](/backend/src/main/java/com/popokis/undertow_vuejs/http/server/RequestHandler.java) and [ResponseHandler.java](/backend/src/main/java/com/popokis/undertow_vuejs/http/server/ResponseHandler.java).

### 1. Web server

The web server is configured via [app.properties](/backend/src/main/resources/app.properties) and
certificates (key-store and trust-store) inside `certificate` folder.

The router of the server is an Undertow HttpHandler you can see it in [Router.java](/backend/src/main/java/com/popokis/undertow_vuejs/http/server/Router.java).

### 2. Database

The database is configured via `.properties` files (thanks to HikariCP that supports them) via [db_prod_pool.properties](/backend/src/main/resources/database/db_prod_pool.properties)
for production configuration and [db_test_pool.properties](/backend/src/main/resources/database/db_test_pool.properties) for testing.
The class responsible of load this config files is: [HikariConnectionPool.java](/backend/src/main/java/com/popokis/undertow_vuejs/db/HikariConnectionPool.java).

## Frontend

TODO

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

## Try it!

[Demo here](http://popokis.com:8080), remember to accept risks (self-signed cert), for login use:\
`admin`\
`admin`

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

1. Compile Vue.js project and generate static files.

`mvn -U clean install -pl :frontend`

2. Remove previous static files.

`rm -rf backend/src/main/resources/public`

3. Test migrations (require a running docker).

`mvn -U clean -pl :backend -Dflyway.configFiles=src/main/resources/database/test_migrations.properties flyway:migrate`

4. Compile backend, move static files to public/ and generate jar file (deployable artifact).

`export APP_ENV=test && mvn -U clean compile test package -pl :backend`

5. Run application (change APP_ENV with `prod` or `test`).

`export APP_ENV=test && java -Xms64M -Xmx512M -Dlogfilename=web -Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector -jar backend/target/backend-0.0.1.jar`

**NOTE:** There is a script to deploy locally (_local_deploy.sh_), **use it for development only!**.

## Manifesto

You don't need a backend framework or an ORM to be able to make web applications, **why not plain java?**.
_"Don't reinvent the wheel"_ they say, and you pick a framework, now you have a **bicycle** with: a washing machine, an embedded
microwave and a neon light beacon... Do you really need all of this features?

**Wait a moment... why are you saying this about frameworks?**

The problem isn't frameworks, it's us (developers). **Try to learn the basics first, later learn a framework**.
Learn things like OOP, FP, HTTP, client-server model, plain SQL, VMs... and then, when you know these things, when you are tired
of doing these things without no help, when you are tired of doing repetitive things only then, take a framework.

Many of the problems that you are going to have with a framework, is due to the lack of knowledge of the framework itself or
**the basics**. Soon you will have a good amount of spaghetti code unmaintainable, with some modules of your project that 
fits the _"framework boundaries"_ and others that use hacks to fit these boundaries with your knowledge of the language,
resulting in **performance issues**, **cryptic errors**, **updating problems**, **development delays (slow cycles)** and more...