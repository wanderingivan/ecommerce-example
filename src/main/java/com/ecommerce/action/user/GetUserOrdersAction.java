package com.ecommerce.action.user;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.Order;
import com.ecommerce.model.User;

/**
 * 
 * Populates order view from service 
 *
 */
public class GetUserOrdersAction extends AbstractUserAction implements AuthenticatedUserAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4177113866594908630L;

	private static final Logger logger = Logger.getLogger(GetUserOrdersAction.class);
	
	private List<Order> orders;
	
	private UserDetails user;
	
	public String execute(){
	    
	    if(logger.isInfoEnabled()){
	        logger.info("Fetching orders for user " + user.getUsername());
	    }
		try{
			orders = service.getOrders((User) user);
			if(logger.isDebugEnabled()){
			    logger.debug("Orders " + orders);
			}
			return SUCCESS;
		}catch(Exception e){
			logger.error("Error fetching orders for user " + user.getUsername() + "\n" + e);
		}
		return ERROR;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.user = userDetails;
	}
	
}
