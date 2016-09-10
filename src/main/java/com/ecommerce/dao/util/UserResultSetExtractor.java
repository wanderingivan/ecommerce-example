package com.ecommerce.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.ecommerce.model.Product;
import com.ecommerce.model.User;

public class UserResultSetExtractor implements ResultSetExtractor<User> {

	private static final RowMapper<User> userMapper = new UserRowMapper();
	private static final RowMapper<Product> productMapper = new BeanPropertyRowMapper<>(Product.class,false);
	
	@Override
	public  User extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		User user = null;
		while(rs.next()){
			if(user == null){
			  user = userMapper.mapRow(rs, rs.getRow());
			}
			if(rs.getLong("id") != 0){
				user.addProduct(productMapper.mapRow(rs, rs.getRow()));
			}
			
		}
		return user;
	}

}
