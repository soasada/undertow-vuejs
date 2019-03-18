# Web application demo with Undertow + Vuejs

## Steps to deploy

1. Compile Vuejs project and generate static files.

`mvn -U clean install -pl :frontend`

2. Remove previous static files.

`rm -rf backend/src/main/resources/public`

3. Compile backend, move static files to public/ and generate jar file (deployable artifact)

`mvn -U clean package -pl :backend`

4. Run application (flag needed in order to avoid TLSv1.3 bug see more: https://issues.jboss.org/browse/UNDERTOW-1493)

`java -Djdk.tls.acknowledgeCloseNotify=true -jar backend/target/backend-0.0.1.jar`

## Remember to add to router the routes of vue-router in order to avoid 404.