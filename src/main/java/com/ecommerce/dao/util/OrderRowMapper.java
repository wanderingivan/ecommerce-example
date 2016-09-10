package com.ecommerce.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ecommerce.model.Order;

public class OrderRowMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
		Order order = new Order();
		order.setId(rs.getLong("order_id"));
		order.setAddress(rs.getString("address"));
		order.setTotal(rs.getBigDecimal("total"));
		order.setSold(rs.getDate("sold"));
		order.setSent(rs.getBoolean("sent"));
		return order;
	}

}
