package com.ecommerce.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import com.ecommerce.model.Review;
import com.ecommerce.model.User;

public class ReviewRowMapper implements RowMapper<Review> {

	private static RowMapper<User> userMapper = new BeanPropertyRowMapper<User>(User.class,false);
	
	@Override
	public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
		Review r = new Review();
		r.setId(rs.getLong("review_id"));
		r.setMessage(rs.getString("message"));
		r.setRating(rs.getInt("rating"));
		r.setPosted(rs.getDate("posted"));
		r.setUser(userMapper.mapRow(rs,rowNum));
		return r;
	}

}
