package com.ecommerce.action.user;

import org.apache.log4j.Logger;

import com.ecommerce.model.User;

/**
 * Prepares a user for editing by
 * populating edit view with the user's information.
 */
public class LoadEdit extends AbstractUserAction {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7144377163954311095L;
	private User user;
	private String username;
	private static Logger logger = Logger.getLogger(LoadEdit.class);
	
	public String execute(){
		try{
			user = service.getUser(username);
			return SUCCESS;
		}catch(Exception e){
			logger.error("Error loading edit action for user " + username + "\n" +e);
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


}
