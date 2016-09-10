package com.ecommerce.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ecommerce.model.Product;
import com.ecommerce.model.User;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public class ProductResultSetExtractor implements ResultSetExtractor<List<Product>> {

	private static RowMapper<String> detailMapper = new DetailsRowMapper();
	private static RowMapper<Product> productMapper = new ProductRowMapper();
	private static RowMapper<User> userMapper = new RowMapper<User>(){

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getLong("user_id"));
			user.setUsername(rs.getString("username"));
			return user;
		}
						
	};
					
	@Override
	public List<Product> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Map<Long,Product> products = new LinkedHashMap<>();
		while(rs.next()){
			Product p = products.get(rs.getLong("product_id"));
			if (p == null){
				p = productMapper.mapRow(rs, rs.getRow());
				products.put(p.getId(),p);
			}
			if(rs.getLong("detail_id") != 0){
				p.addDetail(detailMapper.mapRow(rs, rs.getRow()));
			}
			if(p.getSeller() == null){
				p.setSeller(userMapper.mapRow(rs, rs.getRow()));
			}

		}
		return new ArrayList<>(products.values());
	}

}
