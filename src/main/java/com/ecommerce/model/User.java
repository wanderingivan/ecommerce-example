package com.ecommerce.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;


public class User implements UserDetails{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 172252887856375470L;

	private long id;
	
	private String username,
				   address,
				   details,
				   password,
				   imagePath,
				   email;

	private boolean locked,
					enabled;
	
	private Date createdOn;
	
	private Cart cart;
	
	private List<SimpleGrantedAuthority> authorities;
	
	private List<Order> orders;
	
	private List<Review> reviews;
	
	private List<Product> sale;

	public User() {
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	@RequiredStringValidator(fieldName="username", key="global.empty_field")
	@StringLengthFieldValidator(minLength = "5",
								maxLength = "25",
								fieldName="username",
								key="global.field_between_size")
	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}

	@RequiredStringValidator(message="Address is required",fieldName="address", key="global.empty_field")
	@StringLengthFieldValidator(minLength = "6",
								maxLength = "250",
								fieldName="address",
								key="global.field_between_size")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@RequiredStringValidator(fieldName="email", key="global.empty_field")
	@EmailValidator(fieldName="email", key="global.email_field")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public List<Product> getSale() {
		return sale;
	}

	public void setSale(List<Product> sale) {
		this.sale = sale;
	}
	
	@RequiredStringValidator(message="Password is required",fieldName="passwrord", key="global.empty_field")
	@StringLengthFieldValidator(minLength = "6",
								maxLength = "25",
								fieldName="password",
								key="global.field_between_size")
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	@StringLengthFieldValidator(maxLength = "250",
							    fieldName="details",
							    key="global.field_between_size")
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [id=").append(id).append(", username=")
				.append(username).append(", address=").append(address)
				.append(", details=").append(details).append(", password=[OMITTED]")
				.append(", imagePath=").append(imagePath)
				.append(", email=").append(email).append(", cart=")
				.append(cart).append(", orders=").append(orders)
				.append(", reviews=").append(reviews).append(", sale=")
				.append(sale)
				.append(", createdOn=").append(createdOn)
				.append("]");
		return builder.toString();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void addAuthority(SimpleGrantedAuthority authority) {
		if (authorities == null){
			authorities = new ArrayList<SimpleGrantedAuthority>();
		}
		authorities.add(authority);
	}

	public void addProduct(Product product) {
		if(getSale()==null){
			setSale(new ArrayList<Product>());
		}
		getSale().add(product);
	}


	
}
