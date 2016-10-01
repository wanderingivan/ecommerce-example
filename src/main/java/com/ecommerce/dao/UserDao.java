package com.ecommerce.dao;

import java.util.List;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;


/**
 * 
 * DataAccesObject interface 
 * Provides API for CRUD operations
 * in the database for User,
 * administrative methods for users
 * and operations on user Carts and Orders
 * @see User
 * @see Cart
 * @see Order
 */
public interface UserDao {

	/**
	 * Returns all orders of as user
	 * @param userId the user id in database.
	 * @return a list of orders.
	 * @see Order
	 */
	List<Order> getOrders(long userId);

	/**
	 * Returns a users cart with all Products loaded 
	 * @param userId the user id in database.
	 * @return Cart
	 * @see Cart
	 */
	Cart getCart(long userId);

	/**
	 * Searches for users matching username
	 * @param username the username to match for.
	 * @return a list of users
	 */
	List<User> searchUser(String username);

	/**
	 * Adds and item to a users cart
	 * @param userId the user to add to 
	 * @param productId the product to add to the cart
	 */
	void addCartItem(long userId, long productId);
	
	/**
	 * Removes an item from a users cart
	 * @param userId the id of the user 
	 * @param productId the product to remove from the cart
	 */
	void removeCartItem(long userId, long produtcId);

	/**
	 * Creates an order from a users cart items 
	 * Empties the cart of the user
	 * @param user the user to add the order to
	 */
	void addOrder(User user);
	
	/**
	 * Retrieves and user with the provided username
	 * @param username 
	 * @return User
	 * @see User
	 */
	User getUser(String username);

	/**
	 * Adds a user to the database
	 * @param user the user to save
	 * @return the newly created users id
	 */
	long saveUser(User user);

	/**
	 * Updates a user
	 * @param user
	 */
	void editUser(User user);

	void deleteUser(long userId);

	void toggleEnabled(boolean enabled, String username);
	
	/**
	 * Changes an user's assigned role in the database
	 * @param username the username of the user
	 * @param role the users new role
	 * @throws IllegalArgumentException when the supplied role is unmapped
	 */
	void changeRole(String username,String role);

	/**
	 * 
	 * @return a list of users ordered by creation date
	 * @see Order
	 */
	List<User> getLatest();

	/**
	 * Returns a list of Order ordered by sent
	 * @return
	 * @see Order
	 */
	List<Order> latestOrders();

	Order getOrder(long orderId);

	void changePassword(String user, String encode);

	String getPassword(String principal);
}
