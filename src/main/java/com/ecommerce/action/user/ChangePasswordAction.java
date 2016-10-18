package com.ecommerce.action.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.service.impl.IncorrectPasswordException;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;


public class ChangePasswordAction extends AbstractUserAction implements
		ServletRequestAware, AuthenticatedUserAware {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 78320889848725658L;
	private static final Logger logger = Logger.getLogger(ChangePasswordAction.class);
	
	private HttpServletRequest http;
	private UserDetails user;
	
	private String oldPassword,newPassword;

	public String execute(){
		try{
	        if(logger.isInfoEnabled()){
	            logger.info("Changing password for user " + user);
	        }
			service.changePassword(user.getUsername(), oldPassword, newPassword);
			http.logout();
			return SUCCESS;
		}catch(IncorrectPasswordException ex){
			addFieldError("oldPassword", getText("global.incorrect_password"));
			return INPUT;
		}catch(Exception ex){
			logger.error(String.format("Exception caught changing user %s password: \n %s",user.getUsername(),ex));
		}
		return ERROR;
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.http = request;
	}

	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.user = userDetails;
	}

	public String getOldPassword() {
		return oldPassword;
	}


	@RequiredStringValidator(message="Password is required",fieldName="oldPassword", key="global.empty_field")
	@StringLengthFieldValidator(minLength = "6",
								maxLength = "25",
								fieldName="oldPassword",
								key="global.field_between_size")
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@RequiredStringValidator(message="Password is required",fieldName="newPassword", key="global.empty_field")
	@StringLengthFieldValidator(minLength = "6",
								maxLength = "25",
								fieldName="newPassword",
								key="global.field_between_size")
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}
