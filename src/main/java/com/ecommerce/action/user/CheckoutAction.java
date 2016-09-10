package com.ecommerce.action.user;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.Cart;
import com.ecommerce.model.User;
/**
 *	Prepares a users cart for placing orders and 
 *  populates checkout view.
 *
 */
public class CheckoutAction extends AbstractUserAction  implements AuthenticatedUserAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6232254684718095203L;

	private static final Logger logger = Logger.getLogger(CheckoutAction.class);
	
	private Cart cart;
	
	private User user;
	private UserDetails userDetails;
	
	public String execute(){
	
		try{
			logger.info("Preparing checkout for user " + userDetails.getUsername());
			cart = service.getCart((User)userDetails);
			user = service.getUser(userDetails.getUsername());
			return SUCCESS;
		}catch(Exception e){
			logger.error("Exception caught when preparing checkout for user " + userDetails.getUsername() + "\n" + e);
		}
		return ERROR;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
}
