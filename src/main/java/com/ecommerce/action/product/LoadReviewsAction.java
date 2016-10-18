package com.ecommerce.action.product;

import java.util.List;

import org.apache.log4j.Logger;

import com.ecommerce.model.Review;

public class LoadReviewsAction extends AbstractProductAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 698732033010304936L;

	private static final Logger logger = Logger.getLogger(LoadReviewsAction.class);
	
	private List<Review> reviews;
	
	private long product_id;

	public String execute(){
		try{
		    if(logger.isTraceEnabled()){
		        logger.trace("Loading reviews for pId " + product_id);
		    }
			reviews = service.getReviews(product_id);
			if(logger.isDebugEnabled()){
				logger.debug("Result" + reviews);
			}
			return SUCCESS;
		}catch(Exception e){
			logger.error("Exception caught loading reviews for pId " + product_id + "\n" + e);
		}
		return ERROR;
	}
	
	
	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}
	
}
