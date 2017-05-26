CREATE ALIAS IF NOT EXISTS addOrder AS $$
void addOrder(Connection conn, long userId, String address)  throws SQLException {
	conn.setAutoCommit(false);
	
	PreparedStatement getCart = conn.prepareStatement("SELECT cart_id as cartId FROM carts WHERE user_id = ?");
	getCart.setLong(1,userId);
	ResultSet rs = getCart.executeQuery();
	rs.next();
	long cartId = rs.getLong("cartId");
	
	PreparedStatement sumTotal = conn.prepareStatement("SELECT sum(price) as total FROM products p INNER JOIN carts_products cp ON cp.product_id =p.product_id AND cp.cart_id = ?");
	sumTotal.setLong(1,cartId);
	rs = sumTotal.executeQuery();
	rs.next();
	BigDecimal total = rs.getBigDecimal("total");
	PreparedStatement insertOrder = conn.prepareStatement("INSERT INTO orders(user_id,sent,total,address) VALUES(?,1,?,?)");
	insertOrder.setLong(1,userId);
	insertOrder.setBigDecimal(2,total);
	insertOrder.setString(3,address);
	insertOrder.executeUpdate();
	
	PreparedStatement getOrderId  = conn.prepareStatement("SELECT LAST_INSERT_ID() as orderId");
	rs = getOrderId.executeQuery();
	rs.next();
	long orderId = rs.getLong("orderId");
	
	PreparedStatement orderProducts  = conn.prepareStatement("SELECT product_id as productId FROM carts_products cp WHERE cp.cart_id=?");
	orderProducts.setLong(1,cartId);
	rs = orderProducts.executeQuery();
	
	PreparedStatement insertOrderProducts  = conn.prepareStatement("INSERT INTO orders_products(order_id,product_id) VALUES(?, ?)");
	while(rs.next()){
		insertOrderProducts.setLong(1,orderId);
		insertOrderProducts.setLong(2,rs.getLong("productId"));
		insertOrderProducts.executeUpdate();
	}
	
	PreparedStatement countOrderProducts = conn.prepareStatement("SELECT count(*) as count, p.product_id as productId FROM products p INNER JOIN carts_products cp ON cp.product_id =p.product_id AND cp.cart_id = ? GROUP BY p.product_id");
	countOrderProducts.setLong(1,cartId);
	rs = countOrderProducts.executeQuery();
	
	PreparedStatement updateProductCount = conn.prepareStatement("UPDATE products p SET p.sold = p.sold + ? WHERE p.product_id = ?");
	while(rs.next()){
		updateProductCount.setLong(1,rs.getLong("count"));
		updateProductCount.setLong(2,rs.getLong("productId"));
		updateProductCount.executeUpdate();
	}
	
	PreparedStatement emptyCart = conn.prepareStatement("DELETE FROM carts_products WHERE cart_id = ?");
	emptyCart.setLong(1,cartId);
	emptyCart.executeUpdate();
	
	conn.commit();
}
$$;

CREATE ALIAS IF NOT EXISTS setFeatured AS $$
void setFeatured(Connection conn, String productName, boolean featured) throws SQLException  {
	conn.setAutoCommit(false);
	if(featured){
		PreparedStatement addToFeatured = conn.prepareStatement("INSERT INTO featured(product_id) VALUES((SELECT p.product_id FROM products p WHERE p.name = ?))");
		addToFeatured.setString(1,productName);
		addToFeatured.executeUpdate();
		PreparedStatement updateFeatured = conn.prepareStatement("UPDATE products p SET p.featured = 1 WHERE p.name = ?");
		updateFeatured.setString(1,productName);
		updateFeatured.executeUpdate();
	}else{
		PreparedStatement removeFeatured = conn.prepareStatement("DELETE FROM featured WHERE product_id = (SELECT p.product_id FROM products p WHERE p.name = ?)");
		removeFeatured.setString(1,productName);
		removeFeatured.executeUpdate();
		PreparedStatement updateFeatured = conn.prepareStatement("UPDATE products p SET p.featured = 0 WHERE p.name = ?");
		updateFeatured.setString(1,productName);
		updateFeatured.executeUpdate();
	}
	conn.commit();
}
$$;

CREATE ALIAS IF NOT EXISTS unread AS $$
ResultSet unread(Connection conn, long userId) throws SQLException {
	conn.setAutoCommit(false);
	
	PreparedStatement countStmt = conn.prepareStatement("SELECT * FROM chat_messages cm INNER JOIN users u ON u.user_id = cm.sender_id WHERE cm.chat_id IN (SELECT cj.chat_id FROM chats_join_table cj WHERE cj.user_id=?) AND cm.sender_id != ? AND cm.read=0");
	countStmt.setLong(1,userId);
	countStmt.setLong(2,userId);
	ResultSet rs = countStmt.executeQuery();
	
	PreparedStatement updateStmt =conn.prepareStatement("UPDATE chat_messages cm SET cm.read = 1 WHERE cm.chat_id IN (SELECT chat_id from chats_join_table WHERE user_id=?) AND cm.sender_id !=? AND cm.read=0");
	updateStmt.setLong(1,userId);
	updateStmt.setLong(2,userId);
	updateStmt.executeUpdate();
	conn.commit();
	return rs;
}
$$;
	
CREATE ALIAS IF NOT EXISTS addmessage AS $$ 
ResultSet addMessage(Connection conn,String username, long chatId,String message) throws SQLException {
	PreparedStatement addMessageStmt = conn.prepareStatement("INSERT INTO chat_messages(sender_id,chat_id,message) VALUES((SELECT u.user_id FROM users u WHERE u.username= ?),?,?)");
	addMessageStmt.setString(1,username);
	addMessageStmt.setLong(2,chatId);
	addMessageStmt.setString(3,message);
	addMessageStmt.executeUpdate();
	
	PreparedStatement updateChatsMessage = conn.prepareStatement("UPDATE chats SET lastUpdate = NOW() WHERE chat_id = ?");
	updateChatsMessage.setLong(1,chatId);
	updateChatsMessage.executeUpdate();
	
	PreparedStatement selectLastMessage = conn.prepareStatement("SELECT * FROM chat_messages WHERE message_id = LAST_INSERT_ID()");
	ResultSet rs = selectLastMessage.executeQuery();
	conn.commit();
	return rs;
}
$$;


CREATE ALIAS IF NOT EXISTS sendmessage AS $$
void sendMessage(Connection conn,String message, String sender, String receiver) throws SQLException {
	conn.setAutoCommit(false);
	Long chatId = null;
	PreparedStatement getChatStmt = conn.prepareStatement("SELECT chat_id as chatId FROM chats_join_table WHERE user_id IN (SELECT user_id FROM users WHERE username = ? OR username = ?) GROUP BY chat_id HAVING count(*) > 1");
	getChatStmt.setString(1,sender);
	getChatStmt.setString(2,receiver);
	ResultSet rs = getChatStmt.executeQuery();
	
	if(!rs.next()){
		conn.prepareStatement("INSERT INTO chats(lastUpdate) VALUES(NOW())")
		    .executeUpdate();
		rs = conn.prepareStatement("SELECT LAST_INSERT_ID() as newCId")
		         .executeQuery();
		rs.next();
		chatId = rs.getLong("newCId");
		System.out.println("ChatId: " + chatId);
		PreparedStatement addUserToChatStmt = conn.prepareStatement("INSERT INTO chats_join_table(chat_id,user_id) VALUES(?,(SELECT user_id FROM users WHERE username =?))");
		addUserToChatStmt.setLong(1,chatId);
		addUserToChatStmt.setString(2,sender);
		addUserToChatStmt.executeUpdate();
		addUserToChatStmt.setString(2,receiver);
		addUserToChatStmt.executeUpdate();
	}else{
		chatId = rs.getLong("chatId");
	}
	
	PreparedStatement addMessageStmt = conn.prepareStatement("INSERT INTO chat_messages(sender_id,chat_id,message) VALUES((SELECT u.user_id FROM users u WHERE u.username= ?),?,?)");
	addMessageStmt.setString(1,sender);
	addMessageStmt.setLong(2,chatId);
	addMessageStmt.setString(3,message);
	addMessageStmt.executeUpdate();
	
	PreparedStatement updateChatsMessage = conn.prepareStatement("UPDATE chats SET lastUpdate = NOW() WHERE chat_id = ?");
	updateChatsMessage.setLong(1,chatId);
	updateChatsMessage.executeUpdate();

	conn.commit();
}
$$;