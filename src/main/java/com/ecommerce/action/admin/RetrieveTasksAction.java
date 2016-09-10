package com.ecommerce.action.admin;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.Task;
import com.ecommerce.service.MessageService;
import com.opensymphony.xwork2.ActionSupport;

public class RetrieveTasksAction extends ActionSupport implements AuthenticatedUserAware {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2729760218923879973L;
	private static final Logger logger = Logger.getLogger(RetrieveTasksAction.class);
	
	private MessageService service;
	
	private List<Task> tasks;
	private UserDetails userDetails;
	
	public String userTasks(){
		try{
			tasks = service.retrieveUserTasks(userDetails.getUsername());
			return SUCCESS;
		}catch(Exception e){
			logger.error("Exception caught loading tasks \n" + e);
		}
		return ERROR;
	}
	
	public String pendingTasks(){
		try{
			tasks = service.retrieveUserPendingTasks(userDetails.getUsername());
			return SUCCESS;
		}catch(Exception e){
			logger.error("Exception caught loading tasks \n" + e);
		}
		return ERROR;
	}
		
	public String latestTasks(){
		try{
			tasks = service.latestTasks();
			return SUCCESS;
		}catch(Exception e){
			logger.error("Exception caught loading tasks \n" + e);
		}
		return ERROR;
	}
	
	public String retrieveTasks(){
		try{
			tasks = service.retrieveAllTasks();
			return SUCCESS;
		}catch(Exception e){
			logger.error("Exception caught loading tasks \n" + e);
		}
		return ERROR;
	}
	
	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Autowired
	public void setService(MessageService service) {
		this.service = service;
	}

	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}
}
