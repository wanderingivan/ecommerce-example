package com.ecommerce.action.user;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.ecommerce.interceptor.AuthenticatedUserAware;
import com.ecommerce.model.User;
import com.ecommerce.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class GetOrdersAsXlsAction extends ActionSupport implements AuthenticatedUserAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1736124423529558354L;

	private static final Logger logger = Logger.getLogger(GetOrdersAsXlsAction.class);
	
	private long id;
	
	private UserService service;
	
	private InputStream inputStream;
	
	private User user;
	
	public String gerOrderAsXls(){
		if(logger.isTraceEnabled()){
			logger.trace("Getting wb for " + id);
		}
		Workbook workbook = null;
		try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
		    workbook = service.getOrderAsXLSX(id,user);
			if(logger.isDebugEnabled()){
				logger.debug("Workbook " +workbook );
			}
			workbook.write(out);
			inputStream = new ByteArrayInputStream(out.toByteArray());
			return SUCCESS;
		}catch(Exception e){
			logger.error(String.format("Error converting order with id %d to xlsx %s",id,e));
		}finally{
			if(workbook != null){
				try{
					workbook.close();
				}catch(Exception ignore){}
			}
		}
		return ERROR;
	}

	public String gerOrdersAsXls(){
		if(logger.isTraceEnabled()){
			logger.trace("Getting wb for user " + id);
		}
		Workbook workbook = null;
		try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
		    workbook = service.getOrdersAsXLSX(user);
			if(logger.isDebugEnabled()){
				logger.debug("Workbook " +workbook );
			}
			workbook.write(out);
			inputStream = new ByteArrayInputStream(out.toByteArray());
			return SUCCESS;
		}catch(Exception e){
			logger.error(String.format("Error converting orders for user %s to xlsx %s",user.getUsername(),e));
		}finally{
			if(workbook != null){
				try{
					workbook.close();
				}catch(Exception ignore){}
			}
		}
		return ERROR;
	}	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	@Autowired
	public void setService(UserService service) {
		this.service = service;
	}

	@Override
	public void setUserDetails(UserDetails userDetails) {
		this.user = (User) userDetails;
	}
}
