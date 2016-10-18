package com.ecommerce.action.admin;

import org.apache.log4j.Logger;


public class UserLockAction extends AbstractUserAdminAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 553970952357685987L;
	private static final Logger logger = Logger.getLogger(UserLockAction.class);
	private String username;
	
	public String enable(){
		try{
            if(logger.isInfoEnabled()){
			logger.info(String.format("User %s enabling user %s",actingUser.getUsername(),username));
            }
			service.enableUser(username);
			return SUCCESS;
		}catch(Exception e){
			logger.error(String.format("Exception caught enabling user %s by %s \n %s",username,actingUser.getUsername(),e));
		}
		return ERROR;
	}
	
	public String disable(){
		try{
            if(logger.isInfoEnabled()){
                logger.info(String.format("User %s disabling user %s",actingUser.getUsername(),username));
            }		   
			service.disableUser(username);
			return SUCCESS;
		}catch(Exception e){
			logger.error(String.format("Exception caught disabling user %s by %s \n %s",username,actingUser.getUsername(),e));
		}
		return ERROR;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
