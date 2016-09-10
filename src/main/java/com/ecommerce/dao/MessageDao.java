package com.ecommerce.dao;

import java.util.List;

import com.ecommerce.model.Chat;
import com.ecommerce.model.Message;
import com.ecommerce.model.Task;
/**
 * 
 * DataAccesObject interface 
 * Provides API messages retrieval and insertion
 * to the database and administrative methods for 
 * assigning tasks to staff.
 * 
 */
public interface MessageDao {
	
	/**
	 * Returns the unfinished tasks count for the admin user from the database
	 * @param username
	 * 
	 * @return count
	 */
	int countPending(String username);
	
	/**
	 * Inserts a task for the specified user in the database.<br/>
	 * The task will appear in a users pending tasks
	 * and is visible to everyone with admin permission 
	 * @param task the 
	 * @param assigner username that creates the task
	 * @param assignee 
	 */
	void addTask(Task task,String assigner,String assignee);
		
	/**
	 *  Sets a task as complete 
	 * @param task_id id of the task in db
	 * @param comment optional comment on task completion 
	 */
	void completeTask(long task_id,String comment);
	
	/**
	 * Removes a task from the db
	 * @param task_id id of the task in db
	 */
	void removeTask(long task_id);
	
	/**
	 * List tasks from the database.
	 * @param fetchAll optional parameter sets whether all task 
	 *        would be retrieved or just a limited set
	 *        
	 * @return a collection of Task ordered by task creation date
	 * @see Task
	 */
	List<Task> getTasks(boolean fetchAll);

	/**
	 * List tasks from the database for a particular user.
	 * @param username the user whose assigned taskes will be fetched
	 * 
	 * @param fetchAll optional parameter sets whether all task 
	 *        would be retrieved or just a limited set
	 * @return a collection of Task ordered by task creation date
	 * @see Task
	 */
	List<Task> getTasks(String username, boolean fetchAll);

	/**
	 * Returns the unread  messages count for a user from the database
	 * @param username
	 * 
	 * @return count
	 */
	int countUnread(long user_id);
	
	/**
	 * Returns a list  of unread messages for a user from the database.
	 * The messages will be marked as read after retrieval.
	 * @param user_id the id of the user 
	 * @return a collection of Message ordered by date of post.
	 * @see Message
	 */
	List<Message> getUnread(long user_id);
	
	/**
	 * Send a message between users
	 * If there is no existing chat between the users one will be created in the db
	 * else the message will just be added to existing chat
	 * The message will be set as unread
	 * @param message the message content to add
	 * @param sender the username of the sender
	 * @param receiver the username of the receiver
	 */
	void sendMessage(String message, String sender, String receiver);

	/**
	 *  Sends  message to a chat
	 * @param sender the username of the sender
	 * @param chatId the chat to add the message to 
	 * @param message the message content to add
	 */
	void addMessage(String sender, long chatId, String message);
	
	/**
	 * Returns a message from the db
	 * @param message
	 * @param owner
	 */
	void deleteMessage(String message, String owner);
	
	/**
	 * Returns a limited list of chats that the user is a participant of
	 * with only the latest comment loaded.
	 * @param user_id the user to retrieve chats for
	 * @return collection of Chat ordered by time of last update for Chat
	 * @see Chat
	 */
	List<Chat> loadUserChats(long user_id);

	/**
	 * Loads a chat with all messages
	 * @param chatId the chat id in db
	 * @return a Chat with all messages ordered by time of post descending 
	 * @see Chat
	 */
	Chat loadChat(long chatId);
	

}