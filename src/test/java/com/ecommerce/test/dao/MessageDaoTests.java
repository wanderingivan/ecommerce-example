package com.ecommerce.test.dao;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import javax.transaction.Transactional;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.ecommerce.dao.MessageDao;

import com.ecommerce.model.Chat;
import com.ecommerce.model.Task;
import com.ecommerce.model.Message;

public class MessageDaoTests extends AbstractDBTest {

	private MessageDao dao;
	
	@Test
	@Transactional
	public void testTaskRetrieval(){
		List<Task> tasks = dao.getTasks(false);
		assertNotNull(tasks);
		assertEquals(2, tasks.size());
	}
	
	@Test
	@Transactional
	public void testUserTasksRetrieval(){
		List<Task> tasks = dao.getTasks("username1",false);
		assertNotNull(tasks);
		assertEquals(1, tasks.size());
		Task test = tasks.get(0);
		assertEquals("username2",test.getAssigner());
		assertEquals("username1",test.getAssignee());
	}
	
	@Test
	@Transactional
	public void testCountTasks(){
		int tasks = dao.countPending("username1");
		assertEquals(1,tasks);
	}
	
	@Test
	@Transactional
	public void testAddTask(){
		dao.addTask(new Task("test task 3","test desc"), "username2", "username3");
		List<Task> tasks = dao.getTasks("username3",true);
		assertNotNull(tasks);
		assertEquals(2, tasks.size());
		Task test = tasks.get(0);
		assertEquals("username2",test.getAssigner());
		assertEquals("username3",test.getAssignee());
	}
	
	@Test
	@Transactional
	public void testCompleteTask(){
		dao.completeTask(1, "complete message");
		List<Task> tasks = dao.getTasks("username1",true);
		assertNotNull(tasks);
		assertEquals(1, tasks.size());
		Task test = tasks.get(0);
		assertTrue(test.isComplete());
		assertNotNull(test.getCompleted());
		assertEquals("complete message", test.getComment());
	}
	
	@Test
	@Transactional
	public void testRemoveTask(){
		dao.removeTask(1);
		List<Task> tasks = dao.getTasks("username1",true);
		assertEquals(0, tasks.size());
	}
	
	@Test
	@Transactional
	public void testSendMessage(){
		dao.sendMessage("message4", "username3", "username2");
		List<Chat> chats = dao.loadUserChats(2);
		assertNotNull(chats);
		assertEquals(2,chats.size());
		assertEquals(1,chats.get(0).getMessages().size());
	}
	
	@Test
	@Transactional
	public void testCountUnread(){
		dao.addMessage("username1", 5, "message");
		int unread = dao.countUnread(2);
		assertEquals(2, unread);
	}
	
	@Test
	@Transactional
	public void testGetUnread(){
		dao.addMessage("username1", 5, "message");
		List<Message> unread =dao.getUnread(2);
		assertNotNull(unread);
		assertEquals(2, unread.size());
		int count =dao.countUnread(2);
		assertEquals(0,count);
	}
	
	@Test
	@Transactional
	public void testRetrieveConversation(){
		List<Chat> chats = dao.loadUserChats(2);
		assertEquals("Incorrect number of conversations retrieved",1,chats.size());
		Chat test = dao.loadChat(chats.get(0).getId());
		assertEquals("Incorrect number of messages retrieved",2,test.getMessages().size());
	}
	
	@Test
	@Transactional
	public void testRetrieveChat(){
		Chat c = dao.loadChat(5);
		assertEquals(2, c.getMessages().size());
	}
	
	@Test
	@Transactional
	public void testSendMessageExistingChat(){
		dao.sendMessage("test message","username1","username2");
		List<Chat> chats = dao.loadUserChats(2);
		assertNotNull(chats);
		assertEquals(1,chats.size());
		Chat c = dao.loadChat(chats.get(0).getId());
		assertEquals(3,c.getMessages().size());
		assertEquals("test message",c.getMessages().get(0).getMessage());
	}

	@Test
	@Transactional
	public void testAddMessage(){
	    dao.addMessage("username1",5,"test message");
		Chat c = dao.loadChat(5);
		assertEquals(3, c.getMessages().size());
		assertEquals("test message",c.getMessages().get(0).getMessage());
	}
	
	@Override
	protected IDataSet getDataSet() throws MalformedURLException,
			DataSetException {
		return new FlatXmlDataSetBuilder().build(new File(defaultTestResourceFolder.concat("DbTestDataSet.xml")));
	}

	
	@Autowired
	public void setDao(MessageDao dao) {
		this.dao = dao;
	}

	
	
}
