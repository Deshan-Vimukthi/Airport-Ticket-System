USE `airport_app`;
DROP TABLE IF EXISTS `banned_customers`;
DROP TABLE IF EXISTS `bookings`;
DROP TABLE IF EXISTS `payments`;
DROP TABLE IF EXISTS `invoices`;
DROP TABLE IF EXISTS `flight_ticket_price_allocation`;
DROP TABLE IF EXISTS `seat_class_allocation`;
DROP TABLE IF EXISTS `seats`;
DROP TABLE IF EXISTS `flights`;
DROP TABLE IF EXISTS `airplanes`;
DROP TABLE IF EXISTS `airports`;
DROP TABLE IF EXISTS `country_has_timezone`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `customers`;
DROP TABLE IF EXISTS `employees`;

CREATE TABLE `employees`(
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `emp_code` VARCHAR(10),
    `email` VARCHAR(255) UNIQUE NOT NULL,
    `phone` VARCHAR(15),
    `status_id` INT DEFAULT 1,
    `join_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `resign_date` DATETIME DEFAULT NULL,
    `updated_date` DATETIME DEFAULT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY (`status_id`) REFERENCES `employee_status`(`id`) ON DELETE CASCADE
);

CREATE TABLE `customers`(
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `code` VARCHAR(10),
    `passport_number` VARCHAR(12) NOT NULL,
    `email` VARCHAR(255) UNIQUE NOT NULL,
    `phone` VARCHAR(15),
    `status_id` INT DEFAULT 1,
    `join_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `resign_date` DATETIME DEFAULT NULL,
    `updated_date` DATETIME DEFAULT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY (`status_id`) REFERENCES `travel_status`(`id`) ON DELETE CASCADE
);

CREATE TABLE `users`(
    `id` INT AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    `role` INT NOT NULL,
    `customer_id` INT DEFAULT NULL,
    `employee_id` INt DEFAULT NULL,
    `user_status_id` INT NOT NULL,
    `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_date` DATETIME DEFAULT NULL,
    `deleted_date` DATETIME DEFAULT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY (`role`) REFERENCES `user_role`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`customer_id`) REFERENCES `customers`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`employee_id`) REFERENCES `employees`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`user_status_id`) REFERENCES `user_status`(`id`) ON DELETE CASCADE
);

CREATE TABLE `country_has_timezone`(
    `id` INT AUTO_INCREMENT,
    `country_id` INT NOT NULL,
    `timezone_id` INT NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`country_id`) REFERENCES `countries`(`id`) ON DELETE CASCADE,
    FOREIGN KEY(`timezone_id`) REFERENCES `timezones`(`id`) ON DELETE CASCADE,
    INDEX(`country_id`)
);

CREATE TABLE `airports`(
    `id` INT AUTO_INCREMENT,
    `code` VARCHAR(10) NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `country_and_time_id` INT NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY (`country_and_time_id`) REFERENCES `country_has_timezone`(`id`) ON DELETE CASCADE,
    INDEX(`country_and_time_id`)
);

CREATE TABLE `airplanes`(
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `model_id` INT NOT NULL,
    `manufacturer` VARCHAR(50),
    `seat_row_capacity` INT NOT NULL,
    `country_id` INT NOT NULL,
    `airline_name` VARCHAR(100),
    `registration_number` VARCHAR(20) UNIQUE NOT NULL,
    `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    `updated_date` DATETIME DEFAULT NULL,
    `deleted_date` DATETIME DEFAULT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY (`model_id`) REFERENCES `models`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`country_id`) REFERENCES `countries`(`id`) ON DELETE CASCADE
);

CREATE TABLE `flights`(
    `id` INT AUTO_INCREMENT,
    `arrive_date` DATE,
    `arrive_time` TIME,
    `arrive_airport_id` INT,
    `departure_date` DATE,
    `departure_time` TIME,
    `departure_airport_id` INT,
    `airplane_id` INT NOT NULL,
    `flight_status_id` INT NOT NULL DEFAULT 1,
    `flight_status_reason` VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY (`arrive_airport_id`) REFERENCES `airports`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`departure_airport_id`) REFERENCES `airports`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`airplane_id`) REFERENCES `airplanes`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`flight_status_id`) REFERENCES `flight_status`(`id`) ON DELETE CASCADE
);

CREATE TABLE `seats`(
    `id` INT AUTO_INCREMENT,
    `name` VARCHAR(255),
    `column_no` INT NOT NULL,
    `row_id` INT NOT NULL,
    `class_id` INT NOT NULL,
    `flight_id` INT NOT NULL,
    `availability_id` INT NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY (`class_id`) REFERENCES `seat_classes`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`flight_id`) REFERENCES `flights`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`availability_id`) REFERENCES `availability`(`id`) ON DELETE CASCADE
);

CREATE TABLE `seat_class_allocation`(
    `id` INT AUTO_INCREMENT,
    `airplane_id` INT NOT NULL,
    `class_id` INT NOT NULL,
    `first_seat_no` INT,
    `last_seat_no` INT,
    `seat_layout_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`airplane_id`) REFERENCES `airplanes`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`seat_layout_id`) REFERENCES `seat_layouts`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`class_id`) REFERENCES `seat_classes`(`id`) ON DELETE CASCADE
);

CREATE TABLE `flight_ticket_price_allocation`(
	`id` INT AUTO_INCREMENT,
	`flight_id` INT NOT NULL,
	`class_id` INT NOT NULL,
	`price` INT NOT NULL,
	PRIMARY KEY(`id`),
	FOREIGN KEY(`flight_id`) REFERENCES `flights`(`id`) ON DELETE CASCADE,
	FOREIGN KEY(`class_id`) REFERENCES `seat_classes`(`id`) ON DELETE CASCADE,
	INDEX(`flight_id`)
);

CREATE TABLE `invoices`(
    `id` INT AUTO_INCREMENT,
    `payment_method_id` INT NOT NULL,
    `payed_amount` INT DEFAULT 0,
    `payment_date_time` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`payment_method_id`) REFERENCES `payment_method`(`id`) ON DELETE CASCADE
);

CREATE TABLE `payments`(
    `id` INT AUTO_INCREMENT,
    `amount` INT DEFAULT 0,
    `payment_method_id` INT NOT NULL,
    `payment_type_id` INT NOT NULL,
    `reason` VARCHAR(255),
    `invoice_id` INT NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`payment_method_id`) REFERENCES `payment_method`(`id`) ON DELETE CASCADE,
    FOREIGN KEY(`payment_type_id`) REFERENCES `payment_type`(`id`) ON DELETE CASCADE,
    FOREIGN KEY(`invoice_id`) REFERENCES `invoices`(`id`) ON DELETE CASCADE
);

CREATE TABLE `bookings`(
    `id` INT AUTO_INCREMENT,
    `customer_id` INT NOT NULL,
    `seat_id` INT NOT NULL,
    `user_id` INT,
    `booking_status_id` INT NOT NULL,
    `invoice_id` INT NOT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`customer_id`) REFERENCES `customers`(`id`) ON DELETE CASCADE,
    FOREIGN KEY(`seat_id`) REFERENCES `seats`(`id`) ON DELETE CASCADE,
    FOREIGN KEY(`user_id`) REFERENCES `users`(`id`) ON DELETE CASCADE,
    FOREIGN KEY(`booking_status_id`) REFERENCES `booking_status`(`id`) ON DELETE CASCADE,
    FOREIGN KEY(`invoice_id`) REFERENCES `invoices`(`id`) ON DELETE CASCADE
);

CREATE TABLE `banned_customers`(
    `id` INT AUTO_INCREMENT,
    `customer_id` INT NOT NULL,
    `country_id` INT NOT NULL,
    `reason` VARCHAR(255),
    `created_date` DATE NOT NULL DEFAULT CURRENT_DATE,
    `last_date` DATE DEFAULT NULL,
    PRIMARY KEY(`id`),
    FOREIGN KEY(`customer_id`) REFERENCES `customers`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`country_id`) REFERENCES `countries`(`id`) ON DELETE CASCADE
);