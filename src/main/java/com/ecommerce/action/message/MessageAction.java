package com.ecommerce.action.message;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;

/**
 *	Provides action methods for
 *  sending a message between two users
 *  or adding a message to an existing chat. 	
 * 
 *
 */
public class MessageAction extends AbstractMessageAction implements AuthenticatedUserAware {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4113494272046831126L;

	private static final Logger logger = Logger.getLogger(MessageAction.class);

	private String receiver;
	private long chatId;
	private UserDetails user;
	
	public String sendMessage(){
		try{
			logger.debug(String.format("Sending message %s to %s from %s ",message,receiver,user.getUsername()));
			service.sendMessage(message, user.getUsername(), receiver);
			return SUCCESS;
		}catch(Exception e){
			logger.error("Exception caught: "+e);
		}
		return ERROR;
	}

	public String addMessage(){
		try{
			logger.debug(String.format("Adding message %s to chat %d from %s", message,chatId,user.getUsername()));
			service.addMessage(user.getUsername(), chatId, message);
			return SUCCESS;
		}catch(Exception e){
			logger.error("Exception caught: "+e);
		}
		return ERROR;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public long getChatId() {
		return chatId;
	}

	public void setChatId(long chat_id) {
		this.chatId = chat_id;
	}

	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.user = userDetails;
	}
	
	
}
