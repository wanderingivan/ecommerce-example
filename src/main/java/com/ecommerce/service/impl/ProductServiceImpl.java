package com.ecommerce.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Component;

import com.ecommerce.dao.ProductDao;
import com.ecommerce.model.Product;
import com.ecommerce.model.Review;
import com.ecommerce.model.User;
import com.ecommerce.service.ProductService;


/**
 * Implementation of ProductService that delegates
 * transaction management to Spring <br/>
 * database access to dao
 * handles caching, <br/>
 * and creates ACLs for new products.
 * @see ProductService
 */
@Component
public class ProductServiceImpl implements ProductService {

	
	
	private ProductDao dao;
	private MutableAclService aclService;
	private Map<String,String> defaultPaths;
	
	@Autowired
	public ProductServiceImpl(ProductDao dao,MutableAclService aclService) {
		super();
		this.dao = dao;
		this.aclService = aclService;
	}

	@Override
	@Transactional
	@Cacheable(value="product_short", key="#root.methodName")
	public List<Product> getFeatured() {
		return dao.getFeatured();
	}

	@Override
	@Cacheable(value="product_short", key="#root.methodName")
	@Transactional
	public List<Product> getLatest() {
		return dao.getLatest();
	}

	@Override
	@Transactional
	@Cacheable(value="product_short", key="#root.methodName")
	public List<Product> getMostSeen() {
		return dao.getMostSeen();
	}

	@Override
	@Transactional
	@Cacheable(value="product_short", key="#root.methodName")
	public List<Product> getMostSold() {
		return dao.getMostSold();
	}

	@Override
	@Cacheable(value="product", key="#productName")
	@Transactional
	public Product getProduct(String productName) {
		return dao.getProduct(productName);
	}

	@Override
	@Transactional
	@Cacheable(value="product_short", key="#category")
	public List<Product> getCategory(String category) {
		return dao.getCategory(category);
	}

	@Override
	@Caching(evict={@CacheEvict(value="product", key="#product.productName"),
				    @CacheEvict(value="product_short", allEntries=true)})
	@Transactional
	public void delete(Product product) {
		dao.deleteProduct(product.getId());
		deleteAcl(product.getId());
	}

	@Override
	@Cacheable(value="product_short", key="#productName")
	public List<Product> searchProduct(String productName) {
		return dao.search(productName);
	}

	@Override
	@Caching(evict={@CacheEvict(value="product", key="#product.productName")})
	@Transactional
	public void editProduct(Product product) {
		dao.editProduct(product);
	}

	/**
	 * Creates a new product trough the dao.
	 * If the product has no picture a default will be set.
	 */
	@Override
	@Transactional
	public void createProduct(Product product,User user) {
		if(product.getImagePath() == null){
			product.setImagePath(defaultPaths.get("productPic"));
		}
		long id = dao.createProduct(product,user.getId());
		createAcl(new PrincipalSid(user.getUsername()),id);
	}
	
	@Override
	@Transactional
	public void addReview(User user, String message, int rating,
			String productName) {
		dao.addReview(user.getId(),message,rating,productName);
	}
	
	@Override
	@Transactional
	public List<Review> getReviews(long id){
		return dao.getReviews(id);
	}
	
	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value="product", key="#productName"),
				    @CacheEvict(value="product_short", key="'getFeatured'")})
	public void addFeatured(String productName) {
		dao.toggleFeatured(productName, true);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value="product", key="#productName"),
				    @CacheEvict(value="product_short", key="'getFeatured'")})
	public void removeFeatured(String productName) {
		dao.toggleFeatured(productName, false);
		
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value="product", key="#productName")})
	public void putOnSale(String productName) {
		dao.toggleSale(productName, true);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value="product", key="#productName")})
	public void removeSale(String productName) {
		dao.toggleSale(productName, false);
	}
	
	
	
	@Resource
	public void setDefaultPaths(Map<String, String> defaultPaths) {
		this.defaultPaths = defaultPaths;
	}

	/** 
	 * Create and Access Control List that allows the user to edit and delete his product 
	 * @param sid the security id
	 * @param id - the id of the product to create the acl for
	 */ 
	private void createAcl(Sid sid,long id){
		MutableAcl acl= aclService.createAcl(new ObjectIdentityImpl(Product.class,Long.valueOf(id)));
		acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, sid, true);
		acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, sid, true);
		aclService.updateAcl(acl);
		
	}
	
	/**
	 * Deletes and Access Control List by it's id
	 * @param id
	 */
	private void deleteAcl(long id){
		aclService.deleteAcl(new ObjectIdentityImpl(Product.class,Long.valueOf(id)), true);
	}




}
