#!/usr/bin/env bash

CONTAINER_NAME="$(docker ps --format '{{.Names}} {{.Ports}}' | grep '0.0.0.0:3329->' | awk '{ print $1 }' || true)"

mvn -U clean install -pl :frontend
rm -rf backend/src/main/resources/public
docker exec -i $CONTAINER_NAME mysql -uroot -proot <<< "DROP DATABASE production_db; CREATE DATABASE production_db;"
mvn -U clean -pl :backend -Dflyway.configFiles=src/main/resources/database/test_migrations.properties flyway:migrate
export APP_ENV=test && mvn -U clean compile -Dmaven.test.skip=true package -pl :backend
export APP_ENV=test && java -jar backend/target/backend-0.0.1.jar
docker exec -i $CONTAINER_NAME mysql -uroot -proot <<< "DROP DATABASE production_db; CREATE DATABASE production_db;"