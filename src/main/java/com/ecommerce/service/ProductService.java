package com.ecommerce.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.ecommerce.model.Product;
import com.ecommerce.model.Review;
import com.ecommerce.model.User;
/**
 * 
 * Service Layer interface providing
 * basic CRUD operations for Product objects
 * as well as admin actions.
 * Performs checks on method permissions <br/>
 * @see Product
 */
public interface ProductService {


	@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
	void createProduct(Product product, User user);

	Product getProduct(String productName);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#product, 'WRITE')")
	void editProduct(Product product);

	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#product, 'DELETE')")
	void delete(Product product_id);

	List<Product> searchProduct(String productName);

	List<Product> getFeatured();

	List<Product> getLatest();

	List<Product> getMostSeen();
	
	List<Product> getCategory(String category);
	
	List<Product> getMostSold();

	List<Review> getReviews(long id);

	@PreAuthorize("hasRole('ROLE_USER')")
	void addReview(User user, String message, int rating, String productName);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void addFeatured(String productName);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void removeFeatured(String productName);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void putOnSale(String productName);

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void removeSale(String productName);
}
