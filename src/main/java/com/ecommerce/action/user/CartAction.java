package com.ecommerce.action.user;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.Cart;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.service.ImageService;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * Provides methods to add and remove items from an
 * user's cart and retrieve all products for display. 
 *
 */
public class CartAction extends AbstractUserAction implements AuthenticatedUserAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = 417132749579993693L;

	private static final Logger logger = Logger.getLogger(CartAction.class);


	private ImageService imageService;

	private List<Product> items;
	private BigDecimal total;
	private long product_id;
	
	private UserDetails user;
	
	public String loadCart(){
		try{
	        if(logger.isInfoEnabled()){
	            logger.info("Getting cart for user "+user.getUsername());
	        }
			Cart cart = service.getCart(((User) user));
			if(cart==null){
				return SUCCESS;
			}
			items = cart.getItems();
			//Convert all the images to Base64
			for(Product p : items){
				p.setImagePath(imageService.getB64(p.getImagePath()));
			}
			total = cart.getTotal();
			return SUCCESS;
		}catch(Exception e){
			logger.error("Caught Exception when retrieving cart \n" + e);
		}
		return ERROR;
	}

	public String addItem(){
		try{
	        if(logger.isInfoEnabled()){
	            logger.info(String.format("Adding item  %d for user %s",product_id,user.getUsername()));
	        }
			service.addCartItem((User) user, product_id);
			return SUCCESS;
		}catch(Exception e){
			logger.error("Caught exception when adding item with id "+product_id + "\n" + e);
		}
		return ERROR;
	}


	public String removeItem(){
		try{
	        if(logger.isInfoEnabled()){
	            logger.info(String.format("Removing item  %d for user %s",product_id,user.getUsername()));
	        }
			service.removeCartItem((User) user, product_id);
			return SUCCESS;
		}catch(Exception e){
			logger.error("Caught exception when removing item with id "+product_id + "\n" + e);
		}
		return ERROR;	
	
	}
	
	public List<Product> getItems() {
		return items;
	}

	public void setItems(List<Product> items) {
		this.items = items;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public long getProduct_id() {
		return product_id;
	}

	public void setProduct_id(long product_id) {
		this.product_id = product_id;
	}

	@Autowired
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	@Override
	public void setUserDetails(UserDetails user) {
		this.user = user;
	}

	
}
