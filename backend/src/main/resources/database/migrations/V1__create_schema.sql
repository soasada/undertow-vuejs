CREATE TABLE IF NOT EXISTS `production_db`.`user`
(
    `u_id`         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `u_username`   VARCHAR(255) NOT NULL,
    `u_password`   VARCHAR(255) NOT NULL,
    `u_created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `u_updated_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`u_id`),
    UNIQUE INDEX `u_username_UNIQUE` (`u_username` ASC)
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `production_db`.`house`
(
    `h_id`         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `h_name`       VARCHAR(45)  NOT NULL,
    `h_user_id`    INT UNSIGNED NOT NULL,
    `h_created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `h_updated_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`h_id`),
    UNIQUE INDEX `house_name_uq` (`h_name`),
    INDEX `fk_house_user_idx` (`h_user_id` ASC),
    CONSTRAINT `fk_house_user`
        FOREIGN KEY (`h_user_id`)
            REFERENCES `production_db`.`user` (`u_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;

CREATE TABLE IF NOT EXISTS `production_db`.`furniture`
(
    `f_id`         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    `f_name`       VARCHAR(255) NOT NULL,
    `f_type`       VARCHAR(255) NOT NULL,
    `f_house_id`   INT UNSIGNED NOT NULL,
    `f_created_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `f_updated_at` TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`f_id`),
    UNIQUE INDEX `furniture_name_uq` (`f_name`),
    INDEX `fk_furniture_house_idx` (`f_house_id` ASC),
    CONSTRAINT `fk_furniture_house`
        FOREIGN KEY (`f_house_id`)
            REFERENCES `production_db`.`house` (`h_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARSET = utf8;
