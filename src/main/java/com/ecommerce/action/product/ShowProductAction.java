package com.ecommerce.action.product;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.model.Product;
import com.ecommerce.util.HitResolver;

public class ShowProductAction extends AbstractProductAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8299989810400333250L;

	private static Logger logger = Logger.getLogger(ShowProductAction.class); 
	


	
	private HitResolver hitResolver;
	private List<Product> products;
	private Product product;
	private String productName,
				   category;
	
	public String show(){
		try{
			if(logger.isTraceEnabled()){
				logger.trace("Loading product "+productName);
			}
			product = service.getProduct(productName);
			if(product == null){
				return "missing";
			}
			hitResolver.resolve(product);
			products = service.getCategory(product.getCategory());
			return SUCCESS;
		}catch(Exception e){
			logger.error("Caught Exception "+ e + " while loading product with name " + productName);
			e.printStackTrace();
		}
		return ERROR;
	}
	
	public String listCategory(){
		try{
			if(logger.isDebugEnabled()){
				logger.debug("Loading category "+category);
			}
			products = service.getCategory(category);
			return SUCCESS;
		}catch(Exception e){
			logger.error("Error loading category "+category + " \n" + e);
			e.printStackTrace();
		}
		return ERROR;
	}
	
	
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
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

	@Autowired
	public void setHitResolver(HitResolver hitResolver) {
		this.hitResolver = hitResolver;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
}
