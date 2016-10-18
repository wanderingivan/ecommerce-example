package com.ecommerce.action.admin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.service.ProductService;
import com.opensymphony.xwork2.ActionSupport;

public class ProductFeatureAction extends ActionSupport implements AuthenticatedUserAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1105804135707205117L;

	private static Logger logger = Logger.getLogger(ProductFeatureAction.class);
	
	
	
	private UserDetails user;
	private ProductService service;

	private String productName;
	
	public String addToFeatured(){
		try{
		    if(logger.isInfoEnabled()){
		        logger.info(String.format("Adding product %s to featured by user %s",productName,user.getUsername()));
		    }
			service.addFeatured(productName);
			return SUCCESS;
		}catch(Exception e){
			logger.error(String.format("Exception caught adding product %s to featured \n %s",productName,e));
		}
		return ERROR;
	}
	
	public String removeFromFeatured(){
		try{
			logger.info(String.format("Removing product %s from featured by user %s",productName,user.getUsername()));
			service.removeFeatured(productName);
			return SUCCESS;
		}catch(Exception e){
			logger.error(String.format("Exception caught removing product %s from featured \n %s",productName,e));
		}
		return ERROR;
	}
	
	public String putOnSale(){
		try{
		    if(logger.isInfoEnabled()){
		        logger.info(String.format("Putting product %s on sale by user %s",productName,user.getUsername()));
		    }
			service.putOnSale(productName);
			return SUCCESS;
		}catch(Exception e){
			logger.error(String.format("Exception caught putting product %s on sale \n %s",productName,e));
		}
		return ERROR;
	}
	
	public String takeOffSale(){
		try{
            if(logger.isInfoEnabled()){
                logger.info(String.format("Putting product %s off sale by user %s",productName,user.getUsername()));
            }		    
			service.removeSale(productName);
			return SUCCESS;
		}catch(Exception e){
			logger.error(String.format("Exception caught putting product %s off sale  \n %s",productName,e));
		}
		return ERROR;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Autowired
	public void setService(ProductService service) {
		this.service = service;
	}

	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.user = userDetails;
	}
}
