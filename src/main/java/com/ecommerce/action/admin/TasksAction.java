package com.ecommerce.action.admin;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.Task;
import com.ecommerce.service.MessageService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class TasksAction extends ActionSupport implements
														  AuthenticatedUserAware, ModelDriven<Task> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4412691530351645973L;

	private static final Logger logger = Logger.getLogger(RetrieveTasksAction.class);
	
	private Task task = new Task();
	private UserDetails user;
	private String assigned,
				   comment;
	private MessageService service;
	
	public String addTask(){
		try{
			service.addTask(task,user.getUsername(),assigned);
			return SUCCESS;
		}catch(Exception e){
			logger.error("Exception caught adding task \n" +e);
		}
		return ERROR;
	}
	
	public String completeTask(){
		try{
			service.completeTask(task.getId(),comment);
			return SUCCESS;
		}catch(Exception e){
			logger.error("Exception caught completing task \n" +e);
		}
		return ERROR;
	}
	
	public String removeTask(){
		try{
			service.removeTask(task.getId());
			return SUCCESS;
		}catch(Exception e){
			logger.error("Exception caught removing task \n" +e);
			logger.error(e);
		}
		return ERROR;
	}
	
	public String getAssigned() {
		return assigned;
	}

	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.user = userDetails;
	}

	@Override
	public Task getModel() {
		return task;
	}

	@Autowired
	public void setService(MessageService service) {
		this.service = service;
	}

}
