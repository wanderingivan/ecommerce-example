package com.ecommerce.action.admin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.service.MessageService;
import com.opensymphony.xwork2.ActionSupport;

public class CountUserTasksAction extends ActionSupport implements
		AuthenticatedUserAware {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -2634975104305034603L;
	private static final Logger logger = Logger.getLogger(CountUserTasksAction.class);
	private UserDetails user;
	private int tasks;
	private String message;
	private MessageService service;
	
	public String execute(){
		try{
			tasks = service.countPending(user.getUsername());
			message = String.valueOf(tasks)
					        .concat(getText("global.pending_tasks"));
			if(logger.isDebugEnabled()){
				logger.debug("Message: " + message);
			}
			return SUCCESS;
		}catch(Exception e){
			logger.error("Exception caught counting tasks for " + user.getUsername() + "\n" + e);
		}
		return ERROR;
	}
	
	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.user = userDetails;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Autowired
	public void setService(MessageService service) {
		this.service = service;
	}
	
}
