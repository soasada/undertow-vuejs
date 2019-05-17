#!/usr/bin/env bash

mysql -uroot -proot <<< "DELETE FROM production_db.furniture; ALTER TABLE production_db.furniture AUTO_INCREMENT = 1;"
mysql -uroot -proot <<< "DELETE FROM production_db.house; ALTER TABLE production_db.house AUTO_INCREMENT = 1;"
mysql -uroot -proot <<< "DELETE FROM production_db.user; ALTER TABLE production_db.user AUTO_INCREMENT = 1;"