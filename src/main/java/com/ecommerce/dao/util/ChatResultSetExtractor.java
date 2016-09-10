package com.ecommerce.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.ecommerce.model.Message;
import com.ecommerce.model.Chat;

public class ChatResultSetExtractor implements ResultSetExtractor<List<Chat>> {

	private RowMapper<Message> messageRowMapper;
	private RowMapper<Chat> chatRowMapper;

	public ChatResultSetExtractor(RowMapper<Message> messageRowMapper,
			RowMapper<Chat> chatRowMapper) {
		super();
		this.messageRowMapper = messageRowMapper;
		this.chatRowMapper = chatRowMapper;
	}

	@Override
	public List<Chat> extractData(ResultSet rs) throws SQLException,
			DataAccessException {
		Map<Long,Chat> chats  = new LinkedHashMap<>();
		while(rs.next()){
			Chat c = null;
			if((c=chats.get(rs.getLong("chat_id"))) == null){
				c = chatRowMapper.mapRow(rs, rs.getRow());
				chats.put(c.getId(), c);
			}
			c.addMessage(messageRowMapper.mapRow(rs,rs.getRow()));
			
		}

		return new ArrayList<>(chats.values());
	}

}