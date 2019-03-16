# Web application demo with Undertow + Vuejs

## Steps to deploy

1. Compile Vuejs project and generate static files.

`mvn -U clean install -pl :frontend`

2. Remove previous static files.

`rm -rf backend/src/main/resources/public`

3. Compile backend, move static files to public/ and generate jar file (deployable artifact)

`mvn -U clean package -pl :backend`

4. Run application

`java -jar backend/target/backend-0.0.1.jar`