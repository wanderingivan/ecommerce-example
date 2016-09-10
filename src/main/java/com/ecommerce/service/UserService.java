package com.ecommerce.service;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.security.access.prepost.PreAuthorize;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;

/**
 * Service layer interface
 * Provides methods for CRUD actions on Users,
 * Carts and Orders 
 * as well as administrative methods for locking and
 * changing a users authority role.
 * Defines method security checks
 * @see User
 * @see Cart
 * @see Order
 */
public interface UserService {


	List<User> searchUser(String username);

	User getUser(String username);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void delete(long user_id);
	
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#user, 'READ')")
	Cart getCart(User user);
	
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#user, 'READ')")
	List<Order> getOrders(User user);

	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#user, 'WRITE')")
	void addCartItem(User user, long product_id);
	
	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#user, 'WRITE')" )
	void removeCartItem(User user, long product_id);

	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#user, 'WRITE')" )
	void addOrder(User user);

	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#user, 'WRITE')")
	void editUser(User user);

	@PreAuthorize("isAnonymous()")
	void saveUser(User user);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void enableUser(String username);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void disableUser(String username);
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void changeRole(String username,String role);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	List<User> getLatest();
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	List<Order> latestOrders();

	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#user, 'WRITE')")
	Workbook getOrderAsXLSX(long orderId, User user);

	@PreAuthorize("hasRole('ROLE_ADMIN') OR hasPermission(#user, 'WRITE')")
	Workbook getOrdersAsXLSX(User user); 
}
