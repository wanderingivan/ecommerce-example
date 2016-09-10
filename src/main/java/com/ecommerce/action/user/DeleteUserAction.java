package com.ecommerce.action.user;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;

public class DeleteUserAction extends AbstractUserAction implements AuthenticatedUserAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3970178174439812336L;

	private static Logger logger =  Logger.getLogger(DeleteUserAction.class);

	private long user_id;

	private UserDetails userDetails;
	public String execute(){
		try {
			logger.info(String.format("User %s deleting user with id %d",userDetails.getUsername(),user_id));
			service.delete(user_id);
			return SUCCESS;
		}catch(Exception e){
			logger.error("Error deleting user with id " + user_id + "\n" +e);
		}
		return ERROR;
	}
	public long getUser_id() {
		return user_id;
	}
	public void setUser_id(long user_id) {
		this.user_id = user_id;
	}
	
	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
	
}
