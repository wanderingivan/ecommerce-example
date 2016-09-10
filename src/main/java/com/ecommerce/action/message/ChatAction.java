package com.ecommerce.action.message;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.Chat;
import com.ecommerce.model.User;

/**
 *	Provides action methods 
 *  for loading of individual chats
 *  or chats that a particular user 
 *  is a member of.
 */
public class ChatAction extends AbstractMessageAction implements
		AuthenticatedUserAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6731314002615735186L;

	private static final Logger logger = Logger.getLogger(ChatAction.class);
	
	private List<Chat> chats;
	private Chat chat;
	private long chatId;
	private UserDetails user;
	
	public String loadChat(){
		try{
			if(logger.isTraceEnabled()){
				logger.trace("loading chat " + chatId );
			}
			chat = service.getChat(chatId);
			if(logger.isDebugEnabled()){
				logger.debug("result "+ chat);
			}
			return SUCCESS;
		}catch(Exception e){
			logger.error("Caught exception loading chat "+ chatId + "\n" +e);
		}
		return ERROR;
	}
	
	public String userChats(){
		try{			
			if(logger.isTraceEnabled()){
				logger.trace("loading  latest chats for user " + user.getUsername());
			}
			chats = service.retrieveUserChats(((User) user).getId());
			if(logger.isDebugEnabled()){
				logger.debug("Loaded chats " +chats);
			}
			return SUCCESS;
		}catch(Exception e){
			logger.error("Caught exception loading chats for user "+ user.getUsername() + "\n" +e);
		}
		return ERROR;
	}
	
	public List<Chat> getChats() {
		return chats;
	}

	public void setChats(List<Chat> chats) {
		this.chats = chats;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public long getChatId() {
		return chatId;
	}

	public void setChatId(long chatId) {
		this.chatId = chatId;
	}

	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.user = userDetails;
	}
	public User getUser(){
		return (User) user;
	}

}
