package com.ecommerce.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Provides a basic structure for 
 * item holder types
 */
public abstract class ItemHolder {
	
	protected long id;
	
	protected User owner;
	
	protected List<Product> items;
	
	protected BigDecimal total;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<Product> getItems() {
		return items;
	}

	public void setItems(List<Product> items) {
		this.items = items;
	}

	public void addItem(Product p){
		if(items == null){ items = new ArrayList<Product>(); }
		items.add(p);
	}
	
	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	/**
	 *  Returns the id of every product held in items
	 * @return
	 */
	public abstract List<Long> getProductIds();
	
}
