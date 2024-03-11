USE `shopqa1`;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `user`;
DROP TABLE IF EXISTS `role`;
DROP TABLE IF EXISTS `users_roles`;
DROP TABLE IF EXISTS `shopqa1`.`user_info`;
drop table if exists `shopqa1`.`verification_token`;
DROP TABLE IF EXISTS `shopqa1`.`address`;

DROP TABLE IF EXISTS `shopqa1`.`order_item`;
DROP TABLE IF EXISTS `shopqa1`.`orders`;
DROP TABLE IF EXISTS `shopqa1`.`contact_info`;
SET foreign_key_checks = 1;
CREATE TABLE `user` (
  `user_id` bigint not null auto_increment,
  `username` varchar(50) NOT NULL,
  `password` char(80) NOT NULL,
  `enabled` tinyint NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `user` (user_id,`username`,`password`,`enabled`)
VALUES 
(-1, 'default','default',0);
/*update user u set u.username = 'shop-admin' where u.user_id = 1;*/
INSERT INTO `user` (`username`,`password`,`enabled`)
VALUES 
('shop-admin','$2a$10$uq0k4lwQ8n.ay63cdWkrGOSWkVZSO7CMpr6jMyejYlpPlCbTyiVpq',1);

CREATE TABLE `role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

INSERT INTO `role` (name)
VALUES 
('ROLE_USER'),('ROLE_MANAGER'),('ROLE_ADMIN');



CREATE TABLE `users_roles` (
  `user_id` bigint NOT NULL,
  `role_id` bigint NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `user` (`user_id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `role` (`role_id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `users_roles` (`user_id`,`role_id`)
VALUES 
(1,3);


CREATE TABLE `shopqa1`.`verification_token` (
  `token_id` bigint NOT NULL AUTO_INCREMENT,
  `token` VARCHAR(100) NULL,
  `user_id` bigint NOT NULL,
  `expiry_date` DATETIME NULL,
  PRIMARY KEY (`token_id`),
  INDEX `TOKEN_USER_FK_idx` (`user_id` ASC) VISIBLE,
  CONSTRAINT `TOKEN_USER_FK`
    FOREIGN KEY (`user_id`)
    REFERENCES `shopqa1`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    

CREATE TABLE `shopqa1`.`user_info` (
  `user_info_id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NULL,
  `last_name` VARCHAR(50) NULL,
  `email` VARCHAR(50) NULL,
  `phone` VARCHAR(30) NULL,
  `birth_date` DATE NULL,
  `user_id` bigint NULL,
  PRIMARY KEY (`user_info_id`),
  INDEX `USER_INFO_IDX` (`user_id` ASC) INVISIBLE,
  CONSTRAINT `USER_INFO_FK`
    FOREIGN KEY (`user_id`)
    REFERENCES `shopqa1`.`user` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
insert into `shopqa1`.`user_info`(first_name,last_name,email,phone,user_id)
values
('admin', 'admin','admin@onlineshop.com','+401234567', 1);


CREATE TABLE `shopqa1`.`address` (
  `address_id` bigint NOT NULL AUTO_INCREMENT,
  `country` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `county` varchar(100) DEFAULT NULL,
  `phone_number` varchar(100) DEFAULT NULL,
  `zip_code` varchar(100) DEFAULT NULL,
  `Street_and_number` varchar(100) DEFAULT NULL,
  `additional_address` varchar(100) DEFAULT NULL,
  `observations` varchar(4000) DEFAULT NULL,
  `user_id` bigint NOT NULL,
 -- `address_type` varchar(150) not null,
  `is_primary` bool not null default false,
  PRIMARY KEY (`address_id`),
  KEY `USER_FK` (`user_id`),
  CONSTRAINT `USER_FK` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- delete from `shopqa1`.`address`;
-- select * from `shopqa1`.`address` a where a.user_id = -1;
/*
insert into `shopqa1`.`address`(country,city,county,phone_number,zip_code,Street_and_number,additional_address,observations,user_id, is_primary)
values
('Romania','Craiova','Dolj','+40765812400','200345','Strada Sararilor, nr. 74','Bl. K23, Sc.1, Ap. 13', '', -1, true);

insert into `shopqa1`.`address`(country,city,county,phone_number,zip_code,Street_and_number,additional_address,observations,user_id, address_type, is_primary)
values
('Romania','Craiova','Dolj','+40765812400','200345','Strada Sararilor, nr. 74','Bl. K23, Sc.1, Ap. 13', '', -1, true);
*/
insert into `shopqa1`.`address`(country,city,county,phone_number,zip_code,Street_and_number,additional_address,observations,user_id, is_primary)
values
('Romania','Craiova','Dolj','+40765812400','200345','Strada Sararilor, nr. 74','Bl. K23, Sc.1, Ap. 13', '', 1, true);

insert into `shopqa1`.`address`(country,city,county,phone_number,zip_code,Street_and_number,additional_address,observations,user_id, is_primary)
values
('Romania','Craiova','Dolj','+401222233','200345','aaa, nr. 74','Bl. 1, Sc.1, Ap. 1', '', 1, false);

CREATE TABLE `shopqa1`.`contact_info` (
  `contact_info_id` bigint NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NULL,
  `last_name` VARCHAR(50) NULL,
  `email` VARCHAR(50) NULL,
  `phone_number` VARCHAR(30) NULL,
  `country` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `county` varchar(100) DEFAULT NULL,
  `zip_code` varchar(100) DEFAULT NULL,
  `Street_and_number` varchar(100) DEFAULT NULL,
  `additional_address` varchar(100) DEFAULT NULL,
  `observations` varchar(4000) DEFAULT NULL,
  `info_type` varchar(150) not null, /*shipping or billing*/
  PRIMARY KEY (`contact_info_id`)
);
CREATE TABLE `shopqa1`.`orders` (
  `order_id` bigint NOT NULL AUTO_INCREMENT,
  `order_tracking_number` varchar(255) DEFAULT NULL,
  `total_price` decimal(19,2) DEFAULT NULL,
  `total_quantity` int DEFAULT NULL,
  `billing_contact_info_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `shipping_contact_info_id` bigint DEFAULT NULL,
  `status` varchar(128) DEFAULT NULL,
  `pay_type` varchar(30) DEFAULT NULL,
  `is_paid` boolean NOT NULL DEFAULT false,
  `date_created` datetime DEFAULT NULL,
  `last_updated` datetime DEFAULT NULL,
  PRIMARY KEY (`order_id`),
--  UNIQUE KEY `UK_billing_address_id` (`billing_address_id`),
--  UNIQUE KEY `UK_shipping_address_id` (`shipping_address_id`),
  index `IDX_user_id` (`user_id`),
  CONSTRAINT `FK_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FK_billing_contact_info_id` FOREIGN KEY (`billing_contact_info_id`) REFERENCES `contact_info` (`contact_info_id`),
  CONSTRAINT `FK_shipping_contact_info_id` FOREIGN KEY (`shipping_contact_info_id`) REFERENCES `contact_info` (`contact_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `order_items`
--
CREATE TABLE `shopqa1`.`order_item` (
  `order_item_id` bigint NOT NULL AUTO_INCREMENT,
  `image_url` varchar(255) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit_price` decimal(19,2) DEFAULT NULL,
  `order_id` bigint DEFAULT NULL,
  `product_id` bigint DEFAULT NULL,
  PRIMARY KEY (`order_item_id`),
  index `IDX_order_id` (`order_id`),
  CONSTRAINT `FK_order_id` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  CONSTRAINT `FK_product_id` FOREIGN KEY (`product_id`) REFERENCES `product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


