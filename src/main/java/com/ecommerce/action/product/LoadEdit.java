package com.ecommerce.action.product;

import org.apache.struts2.interceptor.validation.SkipValidation;

import com.ecommerce.model.Product;

public class LoadEdit extends AbstractProductAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5192718515636400392L;
	private Product product;
	private String productName;
	
	@SkipValidation
	public String execute(){
		
		try{
			product = service.getProduct(productName);
			return SUCCESS;
		}catch(Exception e){
			System.out.println(e);
		}
		return ERROR;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}	
	
	
	

}
