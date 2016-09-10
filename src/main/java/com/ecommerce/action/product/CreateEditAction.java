package com.ecommerce.action.product;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.exception.DuplicateProductNameException;
import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.Product;
import com.ecommerce.model.User;
import com.ecommerce.service.ImageService;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;


public class CreateEditAction extends AbstractProductAction implements ModelDriven<Product>,AuthenticatedUserAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -45994865729680974L;

	private static final Logger logger = Logger.getLogger(CreateEditAction.class);

	private ImageService imageService;
	
	private File productPic;
	private UserDetails user;
	private String productPicContentType,
				   productPicFileName;
	
	private Product product = new Product();
	
	public String createProduct(){
		try{
			if(productPic != null){
				String imagePath = imageService.saveImage(productPic, productPicContentType, productPicFileName);
				product.setImagePath(imagePath);
			}
			logger.info(String.format("Creating Product %s by user %s",product,user.getUsername()));
			service.createProduct(product,(User) user);
			return SUCCESS;
		}catch(DuplicateProductNameException de){
			addFieldError("product.productName", getText("global.duplicate.product_name"));
			return INPUT;
		}catch(Exception e){
			logger.error(String.format("Error creating product %s by user %s \n %s",product,user.getUsername(),e));
		}
		return ERROR;
	}
	
	public String editProduct(){
		try{
			logger.info(String.format("Editing Product %d by user %s",product.getId(),user.getUsername()));
			if(productPic != null){
				String imagePath = imageService.saveImage(productPic, productPicContentType, productPicFileName);
				product.setImagePath(imagePath);
			}
			service.editProduct(product);
			return SUCCESS;
		}catch(DuplicateProductNameException de){
			addFieldError("product.productName", "global.duplicate.product_name");
			return INPUT;
		}catch(Exception e){
			logger.error(String.format("Error editing product %d by user %s \n %s",product.getId(),user.getUsername(),e));
			e.printStackTrace();
		}
		return ERROR;
	}

	@Override
	public Product getModel() {
		return getProduct();
	}
	
	@VisitorFieldValidator(appendPrefix=true)
	public Product getProduct(){
		return product;
	}

	public File getProductPic() {
		return productPic;
	}

	public void setProductPic(File productPic) {
		this.productPic = productPic;
	}

	public String getProductPicContentType() {
		return productPicContentType;
	}

	public void setProductPicContentType(String productPicContentType) {
		this.productPicContentType = productPicContentType;
	}

	public String getProductPicFileName() {
		return productPicFileName;
	}

	public void setProductPicFileName(String productPicFileName) {
		this.productPicFileName = productPicFileName;
	}

	@Override
	public void setUserDetails(UserDetails user) {
		this.user = user;
	}

	@Autowired
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}
}
