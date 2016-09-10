package com.ecommerce.action.message;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.User;


/**
 * 
 *	Provides an action method for retrieving a user's
 *  unread messages count. 
 */
public class UnreadCountAction extends AbstractMessageAction implements
		AuthenticatedUserAware {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4782191955441013987L;

	private static final Logger logger = Logger.getLogger(UnreadCountAction.class);	
	
	private int unread;
	
	private UserDetails user;
	
	public String unreadCount(){
		try{
			unread = service.countUnread(((User) user).getId());
			if(logger.isDebugEnabled()){
				logger.debug("Unread count for " + user.getUsername() + " " + unread);
			}
			return SUCCESS;
		}catch(Exception e){
			logger.error("Caught exception counting unread for "+ user.getUsername());
			logger.error(e);
		}
		return ERROR;
	}
	
	public int getUnread() {
		return unread;
	}

	public void setUnread(int unread) {
		this.unread = unread;
	}
	
	
	@Override
	public void setUserDetails(UserDetails userDetails) {
		user = userDetails;
	}

}
