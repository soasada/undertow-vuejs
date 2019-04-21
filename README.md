# Web application demo with Undertow + Vuejs

**Inspired in https://github.com/jonashackt/spring-boot-vuejs**

If you are a java developer, sometimes you just need/want plain java and a frontend framework (or just plain JavaScript) to build
powerful and performance web applications. In order to do this you have two options:

- Pick a java framework. There are many of them.
- Use java and an embedded server such as: Netty, Jetty, Embedded Tomcat or in this case **Undertow** (to mention the most famous).

The problem with the second option is that you are asking yourself the following:

- How can I make a REST API?
- How can I make the controllers of such REST API?
- How can I make secure this REST API?
- It is possible to use HTTPs? And HTTP2?
- ... and many more

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

5. Run application.

`export APP_ENV=prod && java -jar backend/target/backend-0.0.1.jar`

## Remember to add to router the routes of vue-router in order to avoid 404.