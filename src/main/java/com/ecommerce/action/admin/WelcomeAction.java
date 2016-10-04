package com.ecommerce.action.admin;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.Chat;
import com.ecommerce.model.Order;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.service.MessageService;
import com.ecommerce.service.ProductService;
import com.ecommerce.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class WelcomeAction extends ActionSupport implements AuthenticatedUserAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3558342050605792824L;

	private static Logger logger = Logger.getLogger(WelcomeAction.class);
	
	private UserService userService;
	private ProductService productService;
	private MessageService messageService;
	private List<User> latestUsers;
	private List<Product> latestProducts;
	private List<Order> latestOrders;
	private List<Chat> userChats;
	private UserDetails user;
	
	public String execute(){
		try{
			latestUsers = userService.getLatest();
			latestProducts = productService.getLatest();
			latestOrders = userService.latestOrders();
			userChats = messageService.retrieveUserChats(((User) user).getId());
			return SUCCESS;
		}catch(Exception e){
			logger.error("Exception caught loading admin welcome action \n" +e);
		}
		return ERROR;
	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	
	@Autowired
	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public List<User> getLatestUsers() {
		return latestUsers;
	}

	public void setLatestUsers(List<User> latestUsers) {
		this.latestUsers = latestUsers;
	}

	public List<Product> getLatestProducts() {
		return latestProducts;
	}

	public void setLatestProducts(List<Product> latestProducts) {
		this.latestProducts = latestProducts;
	}

	public List<Order> getLatestOrders() {
		return latestOrders;
	}

	public void setLatestOrders(List<Order> latestOrders) {
		this.latestOrders = latestOrders;
	}

	public List<Chat> getUserChats() {
		return userChats;
	}

	public void setUserChats(List<Chat> userChats) {
		this.userChats = userChats;
	}

	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.user = userDetails;
	}
	
}
