package com.ecommerce.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ecommerce.model.Chat;

public class ChatRowMapper implements RowMapper<Chat> {

	@Override
	public Chat mapRow(ResultSet rs, int rowNum) throws SQLException {
		Chat c  = new Chat();
		c.setId(rs.getLong("chat_id"));
		c.setLastUpdate(rs.getDate("lastUpdate"));
		return c;
	}

}