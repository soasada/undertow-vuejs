#!/usr/bin/env bash

mvn -U clean install -pl :frontend
rm -rf backend/src/main/resources/public
mysql -uroot -proot <<< "DROP DATABASE production_db; CREATE DATABASE production_db;"
mvn -U clean -pl :backend flyway:migrate
export APP_ENV=prod && mvn -U clean compile test package -pl :backend
export APP_ENV=prod && java -Xms64M -Xmx512M -Dlogfilename=web -Dlog4j2.contextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector -jar backend/target/backend-0.0.1.jar &