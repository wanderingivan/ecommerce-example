package com.ecommerce.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ecommerce.model.Product;

public class ProductRowMapper implements RowMapper<Product> {

	@Override
	public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
		Product p = new Product();
		p.setId(rs.getLong("product_id"));
		p.setProductName(rs.getString("name"));
		p.setCategory(rs.getString("category"));
		p.setHits(rs.getInt("hits"));
		p.setSold(rs.getInt("sold"));
		p.setOnSale(rs.getBoolean("sale"));
		p.setFeatured(rs.getBoolean("featured"));
		p.setDescription(rs.getString("description"));
		p.setPrice(rs.getBigDecimal("price"));
		p.setImagePath(rs.getString("imagePath"));
		return p;
	}

}
