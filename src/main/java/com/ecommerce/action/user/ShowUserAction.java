package com.ecommerce.action.user;


import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.User;

/**
 * 
 * Loads an user from service by username
 * if called without parameters loads the authenticated user 
 *
 */
public class ShowUserAction extends AbstractUserAction implements AuthenticatedUserAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -883782165161143317L;

	private static Logger logger = Logger.getLogger(ShowUserAction.class);
	
	private UserDetails userDetails;
	private User user;
	private String username;

	
	public String show(){
		try{
			if(username == null){
				username = userDetails.getUsername();
			}
			if(logger.isDebugEnabled()){
				logger.debug("Loading user "+username);
			}
			user = service.getUser(username);
			return SUCCESS;
		}catch(Exception e){
			logger.error("Caught Exception while loading user with name " + username + "\n" + e);
		}
		return ERROR;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}	

}
