package com.ecommerce.action.admin;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

public class ChangeUserRoleAction extends AbstractUserAdminAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2117428442704072651L;
	private static final Logger logger = Logger.getLogger(ChangeUserRoleAction.class);
	private String username,
	               role;
	
	public String execute(){
		try{
		    if(logger.isInfoEnabled()){
		        logger.info(String.format("Changing user %s role to %s by %s",username,role
					                                                         ,actingUser.getUsername()));
		    }
			service.changeRole(username, role);
			return SUCCESS;
		}catch(Exception e){
	        logger.error(String.format("Exception caught changing user %s role to %s by %s \n %s",username,role
					                                                                           ,actingUser.getUsername()
					                                                                           ,e));
		}
		return ERROR;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	@RequiredStringValidator(fieldName="role",message="Role is required")
	@RegexFieldValidator(regex="user|writer|admin",message="Please use correct role",fieldName="role")
	public void setRole(String role) {
		this.role = role;
	}

}
