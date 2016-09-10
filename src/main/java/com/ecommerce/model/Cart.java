package com.ecommerce.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Cart extends ItemHolder{
	
	private Date created;

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	/**
	 * Unlike Orders or ItemHolders the total is not 
	 * final and as such is not stored in db.
	 * It must be recalculated on each call
	 */
	@Override
	public BigDecimal getTotal(){
		return calculateTotal();
	}
	
	public List<Long> getProductIds() {
		ArrayList<Long> ids = new ArrayList<>();
		for(Product p: items){
			Long id = p.getId();
			long times = p.getQuantity();
			for(int i = 0;i < times;i++){
				ids.add(id);
			}
		}
		return ids;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Cart [created=").append(created).append(", items=")
				.append(items).append(", total=").append(getTotal()).append("]");
		return builder.toString();
	}
	
	/**
	 * Calculates total from all Products in items.
	 * @return
	 */
	protected BigDecimal calculateTotal(){
		BigDecimal total = new BigDecimal(0);
		for(Product p: items){
			BigDecimal price = p.getPrice();
			long times = p.getQuantity();
			total = total.add(price.multiply(new BigDecimal(times)));
		}
		return total;
	}
}
