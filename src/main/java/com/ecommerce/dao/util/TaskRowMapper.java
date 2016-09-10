package com.ecommerce.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.ecommerce.model.Task;

public class TaskRowMapper implements RowMapper<Task> {

	@Override
	public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
		Task t = new Task(rs.getString("t.name"),rs.getString("description"));
		t.setComment(rs.getString("comment"));
		t.setComplete(rs.getBoolean("complete"));
		t.setCreated(rs.getDate("created"));
		if(t.isComplete()){
			t.setCompleted(rs.getDate("completed"));
		}
		t.setId(rs.getLong("task_id"));
		t.setAssigner(rs.getString("assigner"));
		String assignee = null;
		if((assignee=rs.getString("assignee"))!= null){
		  t.setAssignee(assignee);
		}
		
		return t;
	}

}
