-- Procedures :

DROP PROCEDURE IF EXISTS unread//
DROP PROCEDURE IF EXISTS updateSold//
DROP PROCEDURE IF EXISTS addMessage//
DROP PROCEDURE IF EXISTS addOrder//
DROP PROCEDURE IF EXISTS sendMessage//
DROP PROCEDURE IF EXISTS setFeatured//


CREATE PROCEDURE `addOrder`(IN userId BIGINT,IN address VARCHAR(250))
BEGIN 
	DECLARE total DECIMAL(14,2);
	DECLARE orderId BIGINT;
	DECLARE cartId BIGINT;
	SELECT cart_id FROM carts WHERE user_id = userId INTO cartId;
	SELECT sum(price) FROM products p INNER JOIN carts_products cp ON cp.product_id =p.product_id AND cp.cart_id = cartId INTO total;
	INSERT INTO orders(user_id,sent,total,address) VALUES(userId,1,total,address);
	SELECT LAST_INSERT_ID() INTO orderId; 
	INSERT INTO orders_products SELECT orderId, product_id FROM carts_products cp WHERE cp.cart_id=cartId;
	CALL updateSold(cartId);
	DELETE FROM carts_products WHERE cart_id = cartId;
END//

CREATE PROCEDURE `updateSold`(IN cart_id BIGINT)
BEGIN
	DECLARE counter INT;
	DECLARE productId BIGINT;
	DECLARE finished INT DEFAULT 0;
	DECLARE cur CURSOR FOR SELECT count(*),p.product_id FROM products p INNER JOIN carts_products cp ON cp.product_id =p.product_id AND cp.cart_id =cart_id GROUP BY p.product_id;
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET finished = 1;
	OPEN cur;
	WHILE finished != 1 DO
		FETCH cur INTO counter,productId;
		IF finished !=1 THEN
			UPDATE products p SET p.sold = p.sold + counter WHERE p.product_id = productId;
		END IF;
	END WHILE;
	CLOSE cur;
END//


CREATE PROCEDURE `unread` (IN uid BIGINT)
BEGIN 
    SELECT cm.message_id,cm.message,cm.sent,cm.read,u.username,u.imagePath FROM chat_messages cm INNER JOIN users u ON u.user_id = cm.sender_id WHERE cm.chat_id IN (SELECT chat_id from chats_join_table WHERE user_id=uid) AND cm.sender_id !=uid AND cm.read=0;
    UPDATE chat_messages cm SET cm.read = 1 WHERE cm.chat_id IN (SELECT chat_id from chats_join_table WHERE user_id=uid) AND cm.sender_id !=uid AND cm.read=0;
END//

CREATE PROCEDURE `setFeatured` (IN product_name VARCHAR(250),IN isFeatured TINYINT)
BEGIN
IF isFeatured = 1 THEN
    INSERT INTO featured(product_id) VALUES((SELECT p.product_id FROM products p WHERE p.name = product_name));
    UPDATE products p SET p.featured = 1 WHERE p.name = product_name;
ELSE
    DELETE FROM featured WHERE product_id = (SELECT p.product_id FROM products p WHERE p.name = product_name);
    UPDATE products p SET p.featured = 0 WHERE p.name = product_name;
END IF;
END//

CREATE PROCEDURE `addMessage` (IN sender VARCHAR(50), IN chatId BIGINT, IN msg VARCHAR(250))
BEGIN
INSERT INTO chat_messages(message,chat_id,sender_id) VALUES(msg,chatId,(SELECT user_id from users WHERE username = sender));
UPDATE chats SET lastUpdate = NOW() WHERE chat_id = chatId;
END//

CREATE PROCEDURE `sendMessage` (IN msg VARCHAR(250),IN sender VARCHAR(50),IN receiver VARCHAR(50))
BEGIN
DECLARE chatId BIGINT;
SELECT chat_id FROM chats_join_table 
              WHERE user_id IN (SELECT user_id FROM users where username = sender or username = receiver) 
              GROUP BY chat_id HAVING count(*) > 1 INTO chatId;

IF chatId IS NULL THEN
    INSERT INTO chats(lastUpdate) VALUES(NOW());
    SELECT LAST_INSERT_ID() INTO chatId;
    INSERT INTO chats_join_table(chat_id,user_id) VALUES(chatId,(SELECT user_id FROM users WHERE username =sender));
    INSERT INTO chats_join_table(chat_id,user_id) VALUES(chatId,(SELECT user_id FROM users WHERE username =receiver));	
END IF;
CALL addMessage(sender,chatId,msg); 
END//