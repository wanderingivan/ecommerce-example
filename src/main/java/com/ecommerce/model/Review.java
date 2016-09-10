package com.ecommerce.model;

import java.util.Date;

public class Review {
	
	private long id;

	private Date posted;
	
	private String message;
	
	private int rating;

	private User user;
	
	public Review(){
		super();
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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		if(rating > 5){
			rating = 5;
		}else if(rating <0){
			rating = 0;
		}else{
			this.rating = rating;
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}



	public Date getPosted() {
		return posted;
	}

	public void setPosted(Date posted) {
		this.posted = posted;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Review [id=").append(id).append(", posted=")
				.append(posted).append(", message=").append(message)
				.append(", rating=").append(rating).append(", user=")
				.append(user).append("]");
		return builder.toString();
	}
	
	
}
