package com.ecommerce.action;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.model.Product;
import com.ecommerce.service.ProductService;
import com.opensymphony.xwork2.ActionSupport;


public class WelcomeAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4335932088473144565L;

	
	private List<Product> latest;
	
	private List<Product> featured;
	

	@Autowired
	private ProductService service;
	
	private static Logger logger = Logger.getLogger(WelcomeAction.class);
	
	public String execute(){
		
		try{
			latest = service.getLatest();
			featured = service.getFeatured();
			return SUCCESS;
		}catch(Exception e){
			logger.error("Error caught when executing welcome action: \n" +e);
		}
		return ERROR;
	}
	
	public void setService(ProductService service) {
		this.service = service;
	}

	public List<Product> getFeatured() {
		return featured;
	}


	public List<Product> getLatest() {
		return latest;
	}
	
}
