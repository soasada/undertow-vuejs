#!/usr/bin/env bash

mvn -U clean install -pl :frontend
rm -rf backend/src/main/resources/public
docker exec -i webappdemo_db_1 mysql -uroot -proot <<< "DROP DATABASE production_db; CREATE DATABASE production_db;"
mvn -U clean -pl :backend -Dflyway.configFiles=src/main/resources/database/test_migrations.properties flyway:migrate
export APP_ENV=test && mvn -U clean compile -Dmaven.test.skip=true package -pl :backend
export APP_ENV=test && java -jar backend/target/backend-0.0.1.jar
docker exec -i webappdemo_db_1 mysql -uroot -proot <<< "DROP DATABASE production_db; CREATE DATABASE production_db;"