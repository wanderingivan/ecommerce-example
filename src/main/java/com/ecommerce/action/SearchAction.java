package com.ecommerce.action;

import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Provides methods for searching 
 * trough users and products based on query type.
 *
 */
public class SearchAction extends ActionSupport{


	/**
	 * 
	 */
	private static final long serialVersionUID = -3367089994085106440L;
	private static final Logger logger = Logger.getLogger(SearchAction.class);
	
	private static final String product = "product",
								user = "user";
	
	private UserService userService;
	
	private ProductService productService;
	
	private List<Product> products;
	private List<User> users;
	
	private String query,type;
	
	public String execute(){
		logger.debug("Searching database with params " + query + " " + type);
		try{
			if(type.equals(product)){
				products = productService.searchProduct(query);
				return product;
			}else if(type.equals(user)){
				users = userService.searchUser(query);
				return user;
			}else{
				throw new IllegalArgumentException("Unmapped query type: "+type);
			}
		}catch(Exception e){
			logger.error(String.format("Exception caught searching for query %s with type %s :\n%s",query,type,e));
		}
		return ERROR;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
}
