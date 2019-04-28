#!/usr/bin/env bash

mvn -U clean install -pl :frontend
rm -rf backend/src/main/resources/public
mvn -U clean -pl :backend -Dflyway.configFiles=src/main/resources/database/test_migrations.properties flyway:migrate
export APP_ENV=test && mvn -U clean compile test package -pl :backend
export APP_ENV=test && java -jar backend/target/backend-0.0.1.jar