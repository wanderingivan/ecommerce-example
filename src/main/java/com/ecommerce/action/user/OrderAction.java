package com.ecommerce.action.user;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.User;
import com.opensymphony.xwork2.validator.annotations.CustomValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

/**
 * 
 * Places an order from a users cart 
 *
 */
public class OrderAction extends AbstractUserAction implements AuthenticatedUserAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7312811907991943700L;

	private static final Logger logger = Logger.getLogger(OrderAction.class);

	private UserDetails user;
	
	private String  address,
				    cardNumber;

	public String execute(){
		try{
	        if(logger.isInfoEnabled()){
	            logger.info(String.format("Creating order for user %s with address %s "
	                    ,user.getUsername(),address));
	        }
			service.addOrder((User) user);
			return SUCCESS;
		}catch(Exception e){
			logger.error(String.format("Error inserting order %s for user %s", user,user.getUsername()) + "\n" + e);
		}
		return ERROR;
	}


	public String getCardNumber() {
		return cardNumber;
	}

	@RequiredStringValidator(message="Card Number is required",fieldName="cardNumber")
	@CustomValidator(type="luhnValidator",message="A valid card number is required",fieldName="cardNumber")
	public void setCardNumber(String cardNumber) {
		cardNumber.replaceAll("-", "");
		this.cardNumber = cardNumber;
	}

	public String getAddress() {
		return address;
	}

	@RequiredStringValidator(message="Address is required",fieldName="cardNumber")
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.user = userDetails;
	}
	
}
