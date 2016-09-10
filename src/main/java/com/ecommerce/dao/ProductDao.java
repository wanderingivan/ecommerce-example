package com.ecommerce.dao;

import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.ecommerce.model.Product;
import com.ecommerce.model.Review;


/**
 * 
 * DataAccesObject interface 
 * Provides API for CRUD operations
 * in the database for Product.
 * @see Product
 */
public interface ProductDao {

	/**
	 * Retrieves a product by its name
	 * @param productName
	 * @return Product
	 * @see Product
	 */
	Product getProduct(String productName);

	/**
	 * Inserts a Product into the database
	 * by a particular user
	 * @param p the Product to add to the database
	 * @param user_id the user that will be set as the products owner
	 * @return the id of the inserted product
	 */
	long createProduct(Product p, long user_id);
	
	/**
	 * Updates an existing Product 
	 * @param p the product to update with the desired changes set.
	 */
	void editProduct(Product p);

	/**
	 * Removes a product from the database
	 * @param productId the id of the Product to remove
	 */
	void deleteProduct(long productId);

	/**
	 * Retrieves a list of Products with matching names. 
	 * @param productName the name to match to
	 * @return a collection of Products
	 * @see Product
	 */
	List<Product> search(String productName);

	/**
	 * Retrieves a list of Products with highest hit count. 
	 * @return a collection of Products
	 * @see Product
	 */
	List<Product> getMostSeen();

	/**
	 * Retrieves a list of Products with highest sold count. 
	 * @return a collection of Products
	 * @see Product
	 */
	List<Product> getMostSold();

	/**
	 * Retrieves a list of 4 Products that are in the featured category ordered by addition to
	 * said category. 
	 * @return a collection of Products
	 * @see Product
	 */
	List<Product> getFeatured();

	/**
	 * Retrieves a list of Products ordered by creation date. 
	 * @return a collection of Products
	 * @see Product
	 */
	List<Product> getLatest();
	
	/**
	 * Retrieves a list of Products that have matching category ordered by 
	 * creation date. 
	 * @param category the category to match products by
	 * @return a collection of Products
	 * @see Product
	 */
	List<Product> getCategory(String category);

	/**
	 * Returns the reviews for a particular Product
	 * ordered by creation date.
	 * @param pId the products id in the database
	 * @return a collection of Reviews
	 * @see Product
	 * @see Review
	 */
	List<Review> getReviews(long pId);

	/**
	 * Adds a review to a product
	 * @param user_id the id of  the user that sent the review 
	 * @param message the content of the review
	 * @param rating the rating of the review
	 * @param productName the product to assign the review to
	 */
	void addReview(long user_id, String message, int rating, String productName);

	/**
	 * Adds or removes a Prodcut to the featured category
	 * @param productName the product to move or remove to featured
	 * @param on whether to move or remove from the category
	 */
	void toggleFeatured(String productName, boolean on);

	/**
	 * Toggles whether a product appears on sale or not
	 * @param productName the product to move or remove to featured
	 * @param on whether to put or remove from sale
	 */ 
	void toggleSale(String productName, boolean on);

	void updateHits(Set<Entry<String, Object[]>> set);

}
