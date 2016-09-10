package com.ecommerce.action.product;

import java.util.List;

import org.apache.log4j.Logger;

import com.ecommerce.model.Product;

public class ShowCategoryAction extends AbstractProductAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7413573998789092369L;

	private static Logger logger =  Logger.getLogger(ShowCategoryAction.class);

	private List<Product> products;
	
	private String category;

	public String execute(){
		try{
			if(logger.isDebugEnabled()){
				logger.debug("Loading category "+category);
			}
			switch (category) {
			case "mostSeen":
				products = service.getMostSeen();
				break;
			case "mostSold":
				products = service.getMostSold();
				break;
			default:
				products = service.getCategory(category);
				break;
			}
			return SUCCESS;
		}catch(Exception e){
			logger.error("Error loading category "+category + "\n" +e);
		}
		return ERROR;
	}
	
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
