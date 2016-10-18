package com.ecommerce.action.product;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.User;

public class AddReviewAction extends AbstractProductAction implements AuthenticatedUserAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3343217217934265162L;

	private final static Logger logger = Logger.getLogger(AddReviewAction.class);
	private UserDetails user;
	
	private String message,
				   productName;
	private int rating;
	
	
	public String execute(){
        if(logger.isInfoEnabled()){
            logger.info(String.format("Creating review with message %s and rating %d for product %s by user %s",
                                                                                    message,rating,productName,user.getUsername()));
        }
		try{
			service.addReview((User) user,message,rating,productName);
			return SUCCESS;
		}catch(Exception e){
			logger.error("Exception caught creating review:\n" +e );
		}
		return ERROR;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Override
	public void setUserDetails(UserDetails user) {
		this.user = user;
	}
	
}
