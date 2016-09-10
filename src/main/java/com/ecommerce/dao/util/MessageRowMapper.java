package com.ecommerce.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ecommerce.model.Message;
import com.ecommerce.model.User;

public class MessageRowMapper implements RowMapper<Message> {

	@Override
	public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
		Message m = new Message();
		User u = new User();
		m.setId(rs.getLong("message_id"));
		m.setMessage(rs.getString("message"));
		m.setSent(rs.getTimestamp("sent"));
		m.setRead(rs.getBoolean("read"));
		u.setUsername(rs.getString("username"));
		u.setImagePath(rs.getString("imagePath"));
		m.setSender(u);
		return m;
	}

}