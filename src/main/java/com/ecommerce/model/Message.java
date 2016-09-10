package com.ecommerce.model;

import java.util.Date;


public class Message {

	private long id;
	
	private String message;
	
	private Date sent;

	private User sender;
	
	private Chat chat;
	
	private boolean read;
	
	public Message(){
		super();
	}
	
	public Message(User sender, String message,Chat chat) {
		super();
		this.sender = sender;
		this.message = message;
		this.chat = chat;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getSent() {
		return sent;
	}

	public void setSent(Date sent) {
		this.sent = sent;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Chat getChat() {
		return chat;
	}

	public void setChat(Chat chat) {
		this.chat = chat;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", message=" + message + ", sent=" + sent
				+ ", sender=" + sender + "]";
	}
	
}
