DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS acl_entry;
DROP TABLE IF EXISTS acl_object_identity;
DROP TABLE IF EXISTS acl_class;
DROP TABLE IF EXISTS acl_sid;
DROP TABLE IF EXISTS tasks;
DROP TABLE IF EXISTS chats_join_table;
DROP TABLE IF EXISTS chat_messages;
DROP TABLE IF EXISTS chats;
DROP TABLE IF EXISTS orders_products;
DROP TABLE IF EXISTS carts_products;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS carts;
DROP TABLE IF EXISTS details;
DROP TABLE IF EXISTS reviews;
DROP TABLE IF EXISTS featured;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
	user_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
	password VARCHAR(125) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
	address VARCHAR(250) NOT NULL,
    details VARCHAR(250),
	imagePath VARCHAR(250),
	createdOn TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
    enabled TINYINT NOT NULL DEFAULT 1,
	locked TINYINT NOT NULL DEFAULT 1,
	PRIMARY KEY(user_id)
)ENGINE=InnoDB;

CREATE TABLE products (
	product_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	name VARCHAR(50) NOT NULL UNIQUE,
    category VARCHAR(50) NOT NULL,
	description VARCHAR(1000) NOT NULL,
	price DECIMAL(14,2) NOT NULL,
	imagePath VARCHAR(250) NOT NULL, 
	hits INT NOT NULL DEFAULT 0,
	sold INT NOT NULL DEFAULT 0,
	sale TINYINT NOT NULL DEFAULT 0,
    featured TINYINT NOT NULL DEFAULT 0,
	created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, 
	user_id BIGINT UNSIGNED NOT NULL,
	CONSTRAINT user_id_fk FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE,
	PRIMARY KEY(product_id)
);

CREATE TABLE reviews (
	review_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	message VARCHAR(500) NOT NULL,
	rating SMALLINT(10) NOT NULL DEFAULT 5,
	user_id BIGINT UNSIGNED NOT NULL,
	posted TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	product_id BIGINT UNSIGNED NOT NULL,
	CONSTRAINT user_id_review_fk FOREIGN KEY(user_id) REFERENCES users(user_id),
	CONSTRAINT product_id_review_fk FOREIGN KEY(product_id) REFERENCES products(product_id) ON DELETE CASCADE,
	PRIMARY KEY(review_id)
);	

CREATE TABLE details(
	detail_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
        message VARCHAR(200),
	product_id BIGINT UNSIGNED NOT NULL,
	CONSTRAINT product_id_detail_fk FOREIGN KEY(product_id) REFERENCES products(product_id) ON DELETE CASCADE,
	PRIMARY KEY(detail_id)
);

CREATE TABLE carts (
	cart_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	user_id BIGINT UNSIGNED NOT NULL UNIQUE,
	CONSTRAINT user_id_cart_fk FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE,	
	PRIMARY KEY(cart_id)
);

CREATE TABLE carts_products (
	cart_id BIGINT UNSIGNED NOT NULL,
	product_id BIGINT UNSIGNED NOT NULL,
	CONSTRAINT product_id_carts_fk FOREIGN KEY(product_id) REFERENCES products(product_id) ON DELETE CASCADE,
	CONSTRAINT cart_id_fk FOREIGN KEY(cart_id) REFERENCES carts(cart_id) ON DELETE CASCADE

);

CREATE TABLE orders (
	order_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
	user_id BIGINT UNSIGNED NOT NULL,
	address VARCHAR(250),
	sent TINYINT,
	total DECIMAL(14,2),
	sold TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT user_id_order_fk FOREIGN KEY(user_id) REFERENCES users(user_id) ON DELETE CASCADE,
	PRIMARY KEY(order_id)
);

CREATE TABLE orders_products (
	order_id BIGINT UNSIGNED NOT NULL,
	product_id BIGINT UNSIGNED NOT NULL,
	CONSTRAINT product_id_orders_fk FOREIGN KEY(product_id) REFERENCES products(product_id) ON DELETE CASCADE,
	CONSTRAINT order_id_fk FOREIGN KEY(order_id) REFERENCES orders(order_id) ON DELETE CASCADE	
);

CREATE TABLE `chats`(
	`chat_id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
    `lastUpdate` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	PRIMARY KEY(`chat_id`)
)ENGINE=InnoDB;


CREATE TABLE `chat_messages`(
	`message_id` BIGINT UNSIGNED  NOT NULL AUTO_INCREMENT,
    `message` VARCHAR(180) NOT NULL,
	`sent` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`read` TINYINT NOT NULL DEFAULT 0,
	`chat_id` BIGINT UNSIGNED NOT NULL,
	`sender_id` BIGINT UNSIGNED NOT NULL,
	CONSTRAINT m_sender_id_fk FOREIGN KEY(`sender_id`) REFERENCES users(`user_id`) ON DELETE CASCADE,
	CONSTRAINT conversation_id_fk FOREIGN KEY(`chat_id`) REFERENCES chats(`chat_id`) ON DELETE CASCADE,
	PRIMARY KEY(`message_id`)
)ENGINE=InnoDB;

CREATE TABLE `chats_join_table`(
	`chat_id`  BIGINT UNSIGNED NOT NULL,
	`user_id`  BIGINT UNSIGNED NOT NULL,
	PRIMARY KEY(`user_id`,`chat_id`),
	CONSTRAINT chat_user_id_fk FOREIGN KEY(`user_id`) REFERENCES users(`user_id`) ON DELETE CASCADE,
	CONSTRAINT chat_id_fk FOREIGN KEY(`chat_id`) REFERENCES chats(`chat_id`) ON DELETE CASCADE
)ENGINE=InnoDB;

CREATE TABLE featured (
	product_id BIGINT UNSIGNED NOT NULL,
	added TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	CONSTRAINT feat_product_id_fk FOREIGN KEY(product_id) REFERENCES products(product_id) ON DELETE CASCADE,
	PRIMARY KEY(product_id,added)
);

CREATE TABLE `tasks` (
	task_id BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  	name VARCHAR(250) NOT NULL,
  	description VARCHAR(500) NOT NULL,
  	message VARCHAR(500),
	assigner VARCHAR(50) NOT NULL,
    assignee VARCHAR(50) NOT NULL,
	created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
	completed TIMESTAMP NULL DEFAULT NULL,
	complete TINYINT NOT NULL DEFAULT 0,
	CONSTRAINT assigner_fk FOREIGN KEY(assigner) REFERENCES users(username) ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT assignee_fk FOREIGN KEY(assignee) REFERENCES users(username) ON UPDATE CASCADE ON DELETE CASCADE,
    PRIMARY KEY(task_id)
);

CREATE TABLE authorities (
      username VARCHAR(50) NOT NULL,
      authority VARCHAR(50) NOT NULL,
      CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE UNIQUE INDEX ix_auth_username ON authorities (username,authority);

CREATE TABLE `acl_sid` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `principal` tinyint(1) NOT NULL,
  `sid` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_uk_1` (`sid`,`principal`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

CREATE TABLE `acl_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `class` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_uk_2` (`class`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

CREATE TABLE `acl_object_identity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `object_id_class` bigint(20) NOT NULL,
  `object_id_identity` bigint(20) NOT NULL,
  `parent_object` bigint(20) DEFAULT NULL,
  `owner_sid` bigint(20) DEFAULT NULL,
  `entries_inheriting` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_uk_3` (`object_id_class`,`object_id_identity`),
  KEY `foreign_fk_1` (`parent_object`),
  KEY `foreign_fk_3` (`owner_sid`),
  CONSTRAINT `foreign_fk_1` FOREIGN KEY (`parent_object`) REFERENCES `acl_object_identity` (`id`),
  CONSTRAINT `foreign_fk_2` FOREIGN KEY (`object_id_class`) REFERENCES `acl_class` (`id`),
  CONSTRAINT `foreign_fk_3` FOREIGN KEY (`owner_sid`) REFERENCES `acl_sid` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `acl_entry` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `acl_object_identity` bigint(20) NOT NULL,
  `ace_order` int(11) NOT NULL,
  `sid` bigint(20) NOT NULL,
  `mask` int(11) NOT NULL,
  `granting` tinyint(1) NOT NULL,
  `audit_success` tinyint(1) NOT NULL,
  `audit_failure` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_uk_4` (`acl_object_identity`,`ace_order`),
  KEY `foreign_fk_5` (`sid`),
  CONSTRAINT `foreign_fk_4` FOREIGN KEY (`acl_object_identity`) REFERENCES `acl_object_identity` (`id`),
  CONSTRAINT `foreign_fk_5` FOREIGN KEY (`sid`) REFERENCES `acl_sid` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;	

