package com.ecommerce.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.model.Chat;
import com.ecommerce.model.Message;
import com.ecommerce.model.Task;
import com.ecommerce.service.MessageService;
import com.ecommerce.dao.MessageDao;

/**
 * Implementation of UserService
 * that provides transaction support
 * and delegates CRUD options to a dao.
 * @see MessageDao
 *
 */
@Service
public class MessageServiceImpl implements MessageService {

	private MessageDao dao;
	
	@Autowired
	public MessageServiceImpl(MessageDao dao){
		this.dao = dao;
	}
	
	@Override
	@Transactional
	public void addTask(Task task, String username, String assigned) {
		dao.addTask(task, username, assigned);
	}

	@Override
	@Transactional
	public void completeTask(long taskId, String comment) {
		dao.completeTask(taskId, comment);
	}

	@Override
	@Transactional
	public void removeTask(long taskId) {
		dao.removeTask(taskId);
	}

	@Override
	@Transactional
	public List<Task> latestTasks() {
		return dao.getTasks(false);
	}

	@Override
	@Transactional
	public List<Task> retrieveAllTasks(){
		return dao.getTasks(true);
	}
	
	@Override
	@Transactional
	public List<Task> retrieveUserPendingTasks(String username){
		return dao.getTasks(username, false);
	}
	
	@Override
	@Transactional
	public List<Task> retrieveUserTasks(String username) {
		return dao.getTasks(username, true);
	}

	@Override
	@Transactional
	public int countPending(String username) {
		return dao.countPending(username);
	}

	@Override
	@Transactional
	public int countUnread(long user_id) {
		return dao.countUnread(user_id);
	}

	@Override
	@Transactional
	public void sendMessage(String message, String sender, String receiver) {
		if(sender.equals(receiver)){
			throw new IllegalArgumentException("Sender cannot be the same as receiver " + sender);
		}
		dao.sendMessage(message,sender,receiver);
	}

	@Override
	@Transactional
	public void deleteMessage(String message, String owner) {
		dao.deleteMessage(message,owner);
	}

	@Override
	@Transactional
	public List<Chat> retrieveUserChats(long user_id) {
		return dao.loadUserChats(user_id);
	}

	@Override
	@Transactional
	public Chat getChat(long chatId) {
		return dao.loadChat(chatId);
	}

	@Override
	@Transactional
	public void addMessage(String sender, long chatId, String message) {
		dao.addMessage(sender,chatId,message);
	}

	@Override
	@Transactional
	public List<Message> getUnread(long user_id) {
		return dao.getUnread(user_id);
	}

}