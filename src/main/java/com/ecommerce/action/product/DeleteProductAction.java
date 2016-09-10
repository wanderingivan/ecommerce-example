package com.ecommerce.action.product;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.Product;
import com.opensymphony.xwork2.ModelDriven;

public class DeleteProductAction extends AbstractProductAction implements ModelDriven<Product>,AuthenticatedUserAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5229611279968630386L;

	private static final Logger logger =  Logger.getLogger(DeleteProductAction.class);
	private Product product = new Product();
	private UserDetails user;
	
	public String execute(){
		try {
			logger.info(String.format("Deleting product with id %d name %s by %s", product.getId(),product.getProductName(),user.getUsername()));
			service.delete(product);
			return SUCCESS;
		}catch(Exception e){
			logger.error(String.format("Error Deleting product with id %s by %s \n %s", product,user.getUsername(),e));
			e.printStackTrace();
		}
		return ERROR;
	}

	@Override
	public Product getModel() {
		return product;
	}

	@Override
	public void setUserDetails(UserDetails user) {
		this.user = user;
	}
	
}
