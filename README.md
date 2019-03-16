# Web application demo with Undertow + Vuejs

### Compile Vuejs project and move source to backend

`mvn -U clean install -pl :frontend`

### Compile and generate jar file of backend (deployable artifact)

`mvn -U clean package -pl :backend`

### Run application

`java -jar backend/target/backend-0.0.1.jar`