
DROP TABLE IF EXISTS `counties`;
CREATE TABLE `countries`(
    `id` INT AUTO_INCREMENT,
    `country_code` VARCHAR(5) NOT NULL,
    `phone_code` VARCHAR(5) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `timezones`;
CREATE TABLE `timezones`(
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `code` VARCHAR(5) NOT NULL,
    `offset` DOUBLE NOT NULL,
    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `employee_status`;
CREATE TABLE `employee_status`(
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `user_status`;
CREATE TABLE `user_status`(
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`(
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `travel_status`;
CREATE TABLE `travel_status`(
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `flight_status`;
CREATE TABLE `flight_status`(
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `booking_status`;
CREATE TABLE `booking_status`(
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `seat_classes`;
CREATE TABLE `seat_classes`(
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `airplane_size`;
CREATE TABLE `airplane_size`(
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `availability`;
CREATE TABLE  `availability`(
	`id` INT AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `seat_layouts`;
CREATE TABLE  `seat_layouts`(
	`id` INT AUTO_INCREMENT,
	`name` VARCHAR(255) NOT NULL,
	PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `payment_method`;
CREATE TABLE `payment_method`(
    `id` INt AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `payment_type`;
CREATE TABLE `payment_type`(
    `id` INt AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`id`)
);

DROP TABLE IF EXISTS `models`;
CREATE TABLE `models`(
    `id` INt AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY(`id`)
);
