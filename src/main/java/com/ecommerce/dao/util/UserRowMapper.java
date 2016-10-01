package com.ecommerce.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ecommerce.model.User;

public class UserRowMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet rs, int rowNum) throws SQLException {
		User user = new User();
		user.setId(rs.getLong("user_id"));
		user.setUsername(rs.getString("username"));
		user.setAddress(rs.getString("address"));
		user.setDetails(rs.getString("details"));
		user.setEmail(rs.getString("email"));
		user.setImagePath(rs.getString("imagePath"));
		user.setEnabled(rs.getBoolean("enabled"));
	
		return user;
	}

}
