package com.ecommerce.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RegexFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.StringLengthFieldValidator;


public class Product {
	
	private long id;
	
	private String productName,
				   category,
				   description,
				   imagePath;

	private User seller;

	private long hits,
				 sold,
				 quantity;
	
	private boolean onSale,
					featured;
	
	private Date created;
	
	private BigDecimal price;	
	
	private List<String> details;
	
	private List<Review> reviews;
	
	
	public Product (){
		super();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	@RequiredStringValidator(fieldName="productName",key="global.empty_field")
	@StringLengthFieldValidator(minLength = "5",
								maxLength = "250",
								fieldName="productName",key="global.field_between_size")
	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public long getHits() {
		return hits;
	}

	public void setHits(long hits) {
		this.hits = hits;
	}

	public long getSold() {
		return sold;
	}

	public void setSold(long sold) {
		this.sold = sold;
	}

	@RequiredFieldValidator(fieldName="price",key="global.empty_field")
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	@RequiredStringValidator(fieldName="description",key="global.empty_field")
	@StringLengthFieldValidator(maxLength = "1000",
	        					fieldName="description",key="global.field_max_size")
	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getDetails() {
		return details;
	}

	public void setDetails(List<String> details) {
		this.details = details;
	}

	public List<Review> getReviews() {
		return reviews;
	}

	public void setReviews(List<Review> reviews) {
		this.reviews = reviews;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@RequiredStringValidator(fieldName="category",key="global.empty_field")
	@RegexFieldValidator(regex="fighters|bombers|ground-attack|transport",fieldName="category",key="global.category_select_input")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isOnSale() {
		return onSale;
	}

	public void setOnSale(boolean onSale) {
		this.onSale = onSale;
	}

	public boolean isFeatured() {
		return featured;
	}

	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	
	public void addDetail(String det){
		if(getDetails() == null){details = new ArrayList<String>();}
		getDetails().add(det);
	}
	
	public void addReview(Review review){
		if(getReviews() == null){reviews = new ArrayList<Review>();}
		getReviews().add(review);
	}
	

	
	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public long getQuantity() {
		return quantity;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Product [id=").append(id).append(", productName=")
				.append(productName).append(", category=").append(category)
				.append(", imagePath=").append(imagePath).append(", price=")
				.append(price).append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result
				+ ((productName == null) ? 0 : productName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		if (productName == null) {
			if (other.productName != null)
				return false;
		} else if (!productName.equals(other.productName))
			return false;
		return true;
	}



	
	
}
