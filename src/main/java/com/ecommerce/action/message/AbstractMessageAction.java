package com.ecommerce.action.message;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.service.MessageService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Abstract class used as scaffolding for message 
 * actions.
 * Sets default services and members. 
 */
public class AbstractMessageAction extends ActionSupport{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3063746935486845925L;
	
	protected MessageService service;
	protected String message;
	
	
	
	
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
