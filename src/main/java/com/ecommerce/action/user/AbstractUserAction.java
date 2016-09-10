package com.ecommerce.action.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.service.UserService;
import com.opensymphony.xwork2.ActionSupport;


/**
 * Abstract class used as scaffolding for user 
 * actions.
 * Sets default services. 
 */
public abstract class AbstractUserAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7535759213225711971L;
	
	protected UserService service;
	

	@Autowired
	public void setService(UserService service) {
		this.service = service;
	}

}
