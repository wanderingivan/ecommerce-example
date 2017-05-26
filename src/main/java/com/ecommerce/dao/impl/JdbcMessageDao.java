package com.ecommerce.dao.impl;

import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.ecommerce.dao.MessageDao;
import com.ecommerce.dao.util.ChatResultSetExtractor;
import com.ecommerce.dao.util.ChatRowMapper;
import com.ecommerce.dao.util.MessageRowMapper;
import com.ecommerce.dao.util.TaskRowMapper;
import com.ecommerce.model.Chat;
import com.ecommerce.model.Message;
import com.ecommerce.model.Task;

/**
 * 
 * Implementation of MessageDao that
 * uses Spring's JdbcTemplate classes to access the database 
 * @see MessageDao
 */
@Repository
public class JdbcMessageDao implements MessageDao {

	private static final Logger logger = Logger.getLogger(JdbcMessageDao.class);
	
	
	private JdbcTemplate template;
	
	private static RowMapper<Task> taskRowMapper = new TaskRowMapper();
	private static RowMapper<Chat> chatRowMapper = new ChatRowMapper();
	private static RowMapper<Message> messageRowMapper = new MessageRowMapper();
	private static ResultSetExtractor<List<Chat>> chatResultSetExractor = new ChatResultSetExtractor(messageRowMapper,chatRowMapper);
	
	private static final String insertTaskStatement = "INSERT INTO tasks(name,description,assigner,assignee) VALUES(?,?,?,?)",
			
			                    completeTaskStatement = "UPDATE tasks SET complete=1,completed=?,message=? WHERE task_id=?",
			                    
			                    deleteTaskStatement = "DELETE FROM tasks WHERE task_id = ?",
			                    
							    selectTaskQuery = "SELECT task_id,"
							    				       + "name,"
				                                       + "description,"
				                                       + "message as comment,"
				                                       + "created,"
				                                       + "complete,"
				                                       + "completed,"
				                                       + "assigner,"
				                                       + "assignee "				         
				                                  + "FROM tasks ",
				                                  
								countTasksQuery = "SELECT count(*) FROM tasks t WHERE t.assignee= ? AND t.complete =0",
								
							    countUnreadQuery = "SELECT count(*) "
							    		           + "FROM chat_messages cm "
							    		          + "WHERE cm.chat_id IN "
							    		                + "(SELECT chat_id from chats_join_table WHERE user_id=?) "
							    		            + "AND cm.sender_id !=? "
							    		            + "AND cm.read=0",
							    
								selectChatQuery = "SELECT c.chat_id,"
								   		               + "c.lastUpdate,"
								   		               + "m.message_id,"
								   		               + "m.message,"
								   		               + "m.sent,"
								   		               + "m.read,"
								   		               + "u.username,"
								   		               + "u.imagePath "
								   		          + "FROM chats c "
								   		               + "INNER JOIN chat_messages m "
								   		               + "ON m.chat_id = c.chat_id "
								   		               + "INNER JOIN users u "
								   		               + "ON u.user_id = m.sender_id ";
	private final SimpleJdbcCall addMessageCall,
	                             sendMessageCall,
	                             getUnreadCall;
								   
	@Autowired
	public JdbcMessageDao(JdbcTemplate template) {
		super();
		this.template = template;
		
		this.addMessageCall = new SimpleJdbcCall(template).withProcedureName("addMessage")
				                                          .declareParameters(new SqlParameter("sender",Types.VARCHAR),
				                                        		             new SqlParameter("chatId",Types.BIGINT),
		    							                                     new SqlParameter("msg",Types.VARCHAR));
		
		this.sendMessageCall = new SimpleJdbcCall(template).withProcedureName("sendMessage")
				                                           .declareParameters(new SqlParameter("msg",Types.VARCHAR),
				                                                              new SqlParameter("sender",Types.VARCHAR),
				                                        		              new SqlParameter("receiver",Types.VARCHAR));
		
		this.getUnreadCall = new SimpleJdbcCall(template).withProcedureName("unread")
														  .declareParameters(new SqlParameter("uid",Types.BIGINT))
														  .returningResultSet("rs", messageRowMapper);


	}
	

	@Override
	public void addTask(Task task, String assigner, String assignee) {
		template.update(insertTaskStatement,new Object[]{task.getName(),task.getDescription(),assigner,assignee});
	}

	@Override
	public void completeTask(long task_id, String comment) {
		template.update(completeTaskStatement,new Object[]{new Date(),comment,task_id});
	}

	@Override
	public void removeTask(long task_id) {
		template.update(deleteTaskStatement,new Object[]{task_id});
	}

	@Override
	public List<Task> getTasks(boolean fetchAll) {
		StringBuilder query = new StringBuilder(selectTaskQuery);
		query.append("ORDER BY created DESC ");
		if(!fetchAll){
			query.append("LIMIT 10 ");
		}
		return template.query(query.toString(), taskRowMapper);
	}

	@Override
	public List<Task> getTasks(String username,boolean fetchAll){
		StringBuilder query = new StringBuilder(selectTaskQuery);
		query.append("WHERE assignee = ? ");
		
		if(!fetchAll){
			query.append("AND complete = 0 ORDER BY created DESC LIMIT 10 ");
		}else{
			query.append("ORDER BY created DESC");
		}
		logger.debug("Final Query " + query.toString());
		return template.query(query.toString(), new Object[]{username},taskRowMapper);
	}


	@Override
	public int countPending(String username) {
		return template.queryForObject(countTasksQuery,new Object[]{username},Integer.class);
	}


	@Override
	public int countUnread(long user_id) {
		return template.queryForObject(countUnreadQuery,new Object[]{user_id,user_id},Integer.class);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Message> getUnread(long user_id) {
		return (List<Message>) getUnreadCall.execute(user_id).get("rs");
	}

	@Override
	public void sendMessage(String message, String sender, String receiver) {
		sendMessageCall.execute(message,sender,receiver);
	}



	@Override
	public void addMessage(String sender, long chatId, String message) {
        addMessageCall.execute(sender,chatId,message);
	}

	@Override
	public void deleteMessage(String message, String owner) {
		template.update("DELETE FROM chat_messages WHERE message=? and owner_id = (SELECT user_id FROM users where username = ?)"
				                                                                                          ,new Object[]{message,owner});
	}

	@Override
	public List<Chat> loadUserChats(long user_id) {
		StringBuilder query = new StringBuilder(selectChatQuery);
		query.append("WHERE c.chat_id IN (SELECT chat_id FROM chats_join_table WHERE user_id =?) AND m.message_id IN (SELECT MAX(message_id) FROM chat_messages WHERE sender_id != ? GROUP BY chat_id) ORDER BY c.lastUpdate DESC LIMIT 5");
		return template.query(query.toString(),new Object[]{user_id,user_id},chatResultSetExractor);
	}


	@Override
	public Chat loadChat(long chatId) {
		StringBuilder query = new StringBuilder(selectChatQuery);
		query.append("WHERE c.chat_id = ? ORDER BY m.sent DESC");
		List<Chat> chats=  template.query(query.toString(),new Object[]{chatId},chatResultSetExractor);
		return chats.get(0);
	}

}