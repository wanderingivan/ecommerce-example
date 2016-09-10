package com.ecommerce.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Chat implements Comparable<Chat> {
	
	private long id;
	
	private List<User> users;
	
	private List<Message> messages;

	private Date lastUpdate;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void addMessage(Message message){
		if(messages == null){
			messages = new ArrayList<>();
		}
		messages.add(message);
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void addUser(User user){
		if(users ==null){
			users = new ArrayList<User>();
		}
		users.add(user);
	}
	
	public User getOther(String username){
		System.out.println("NAME:" + username);
		for(User u : users){
			if(!(u.getUsername().equals(username))){
				System.out.println(u);
				return u;
				
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "Conversation [id=" + id + ", messages=" + messages
				+ ", lastUpdate=" + lastUpdate + "]";
	}

	@Override
	public int compareTo(Chat o) {
		return this.lastUpdate.compareTo(o.getLastUpdate());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Chat))
			return false;
		Chat other = (Chat) obj;
		if (id != other.id)
			return false;
		if (lastUpdate == null) {
			if (other.lastUpdate != null)
				return false;
		} else if (!lastUpdate.equals(other.lastUpdate))
			return false;
		return true;
	}
	
	
}
