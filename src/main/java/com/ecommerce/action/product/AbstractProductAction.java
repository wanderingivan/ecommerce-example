package com.ecommerce.action.product;

import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.service.ProductService;
import com.opensymphony.xwork2.ActionSupport;

public abstract class AbstractProductAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ProductService service;


	@Autowired
	public void setService(ProductService service) {
		this.service = service;
	}


	
}
