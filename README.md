# Web application demo with Undertow + Vuejs

**Inspired in https://github.com/jonashackt/spring-boot-vuejs**

If you are a java developer, sometimes you just need/want plain java and a frontend framework (or just plain JavaScript) to build
powerful and performance web applications. In order to do this you have two options:

- Pick a java framework. There are many of them.
- Use java and an embedded server such as: Netty, Jetty, Embedded Tomcat or in this case **Undertow**.

The problem with the second option is that you are asking yourself the following:

- How can I make a REST API?
- How can I make the controllers of such REST API?
- How can I make secure this REST API?
- It is possible to use HTTPs? And HTTP2?
- ... and many more

The intention of this repo is to give you a seed project (or example project?) to build single page web applications with undertow and vuejs.

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

1. Compile Vuejs project and generate static files.

`mvn -U clean install -pl :frontend`

2. Remove previous static files.

`rm -rf backend/src/main/resources/public`

3. Test migrations

`mvn -U clean -pl :backend -Dflyway.configFiles=src/main/resources/test_migrations.properties flyway:migrate`

3. Compile backend, move static files to public/ and generate jar file (deployable artifact)

`mvn -U clean package -pl :backend`

4. Run application (flag needed in order to avoid TLSv1.3 bug see more: https://issues.jboss.org/browse/UNDERTOW-1493)

`java -Djdk.tls.acknowledgeCloseNotify=true -jar backend/target/backend-0.0.1.jar`

## Remember to add to router the routes of vue-router in order to avoid 404.