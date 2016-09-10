package com.ecommerce.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.ecommerce.model.Order;
import com.ecommerce.model.Product;

public class OrderResultSetExtractor implements ResultSetExtractor<List<Order>> {
	
	private static RowMapper<Order> orderMapper = new OrderRowMapper();
	private static RowMapper<Product> productMapper = new BeanPropertyRowMapper<Product>(Product.class,false);
	
	@Override
	public List<Order> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Map<Long,Product> products = new HashMap<>();
		List<Order> orders = new LinkedList<>();
		Order o = null;
		while(rs.next()){
			if(o == null || o.getId() != rs.getLong("order_id")){
				o = orderMapper.mapRow(rs,rs.getRow());
				products.clear();
				orders.add(o);
			}
			Product p  = products.get(rs.getLong("id"));
			if(p == null ){
				p = productMapper.mapRow(rs,rs.getRow());
				products.put(p.getId(),p);
				p.setQuantity(1);
				o.addItem(p);
			}else{
				p.setQuantity(p.getQuantity()+1);
			}

					
		}
		return orders;
	}

}
