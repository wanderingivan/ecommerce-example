package com.ecommerce.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.ecommerce.model.Cart;
import com.ecommerce.model.Product;

public class CartResultSetExtractor implements ResultSetExtractor<Cart> {

	private static RowMapper<Cart> cartMapper = new CartRowMapper();
	private static RowMapper<Product> productMapper = new BeanPropertyRowMapper<Product>(Product.class,false);
	
	
	@Override
	public Cart extractData(ResultSet rs) throws SQLException,
			DataAccessException { 
	
		Cart c = null;
		Map<Long,Product> products = new HashMap<>();
		while(rs.next()){
			if(c == null){
				c = cartMapper.mapRow(rs,rs.getRow());
			}
			Long pId = rs.getLong("id");
			Product p  = products.get(pId);
			if(p == null ){
				p = productMapper.mapRow(rs,rs.getRow());
				products.put(p.getId(),p);
				p.setQuantity(1);
				c.addItem(p);
			}else{
				p.setQuantity(p.getQuantity()+1);
			}
			
		}
		return c;
	}
}
