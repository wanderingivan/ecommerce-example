package com.ecommerce.action.message;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.Message;
import com.ecommerce.model.User;
import com.ecommerce.service.ImageService;

import java.util.List;

/**
 * 
 *	Provides an action method for retrieving a user's
 *  unread messages. 
 *
 */
public class RetrieveUnreadMessagesAction extends AbstractMessageAction implements
		AuthenticatedUserAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7460760231809882831L;

	private static final Logger logger = Logger.getLogger(RetrieveUnreadMessagesAction.class);	
	
	private ImageService imageService; 
	
	private List<Message> messages;
	private UserDetails user;
	

	
	public String unread(){
		try{
			messages = service.getUnread(((User) user).getId());
			for(Message m : messages){
				User sender = m.getSender();
				sender.setImagePath(imageService.getB64(sender.getImagePath()));
			}
			if(logger.isDebugEnabled()){
				logger.debug(messages);
			}
			return SUCCESS;
		}catch(Exception e){
			logger.error("Caught exception "+ e);
			logger.error(e);
		}
		return ERROR;
	}
	


	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
	
	
	@Autowired
	public void setImageService(ImageService imageService) {
		this.imageService = imageService;
	}

	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.user = userDetails;
	}

}
