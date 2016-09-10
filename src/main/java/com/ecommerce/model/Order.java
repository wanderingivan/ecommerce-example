package com.ecommerce.model;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

public class Order extends ItemHolder {

	
	private String address;

	private String cardNumber;
	
	private Date sold;
	
	private boolean sent;
	
	public Order() {
		super();
	}

	@RequiredStringValidator
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}

	public Date getSold() {
		return sold;
	}

	public void setSold(Date sold) {
		this.sold = sold;
	}

	public boolean isSent() {
		return sent;
	}

	public void setSent(boolean sent) {
		this.sent = sent;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public List<Long> getProductIds() {
		Long[] ids = new Long[items.size()];
		int i =0;
		for(Product p : items){
			ids[i++]= p.getId();
		}
		return Arrays.asList(ids);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Order [address=").append(address).append(", sold=")
				.append(sold).append(", sent=").append(sent).append(", id=")
				.append(id).append(", items=").append(items).append(", total=")
				.append(total).append("]");
		return builder.toString();
	}
	
}
