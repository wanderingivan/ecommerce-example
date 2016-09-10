package com.ecommerce.action.user;

import java.io.File;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.exception.DuplicateEmailException;
import com.ecommerce.exception.DuplicateUsernameException;
import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.User;
import com.ecommerce.service.ImageService;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator;

/**
 * 
 * Provides methods from creating and editing of users 
 *
 */
public class CreateEditAction extends AbstractUserAction implements ModelDriven<User>, AuthenticatedUserAware{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1509650137406655285L;

	private static Logger logger = Logger.getLogger(CreateEditAction.class);

	private ImageService imageService;
	private User user = new User();
	private File profilePic;
	private String profilePicContentType,
				   profilePicFileName;
	
	private UserDetails userDetails;


	public String createUser(){
		try{
			logger.info(String.format("Creating user %s",user));
			
			if(profilePic != null){
				String imagePath = imageService.saveImage(profilePic, profilePicContentType, profilePicFileName);
				user.setImagePath(imagePath);
			}
			
			service.saveUser(user);
			return SUCCESS;
			
		}catch(DuplicateUsernameException de){
			
			addFieldError("user.username", getText("global.duplicate.username"));
			return INPUT;
			
		}catch(DuplicateEmailException de){
			
			addFieldError("user.email", getText("global.duplicate.email"));
			return INPUT;
			
		}catch(Exception e){
			
			logger.error("Error creating user " + user + "\n" + e);

		}
		return ERROR;
	}
	
	public String editUser(){
		try{
			logger.info(String.format("User %s Editing user %d",userDetails.getUsername(),user.getId()));
			
			if(profilePic != null){
				String imagePath = imageService.saveImage(profilePic, profilePicContentType, profilePicFileName);
				user.setImagePath(imagePath);
			}
			
			service.editUser(user);
			return SUCCESS;
			
		}catch(DuplicateUsernameException de){
			
			addFieldError("user.username", getText("global.duplicate.username"));
			return INPUT;
			
		}catch(DuplicateEmailException de){
			
			addFieldError("user.email", getText("global.duplicate.email"));
			return INPUT;
			
		}catch(Exception e){
			
			logger.error("Error editing user " + user.getId() + "\n" + e);
		}
		return ERROR;
	}



	public File getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(File profilePic) {
		this.profilePic = profilePic;
	}

	public String getProfilePicContentType() {
		return profilePicContentType;
	}

	public void setProfilePicContentType(String profilePicContentType) {
		this.profilePicContentType = profilePicContentType;
	}

	public String getProfilePicFileName() {
		return profilePicFileName;
	}

	public void setProfilePicFileName(String profilePicFileName) {
		this.profilePicFileName = profilePicFileName;
	}

	@Override
	public User getModel() {
		return getUser();
	}

	@VisitorFieldValidator(appendPrefix = true)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Autowired
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
}
