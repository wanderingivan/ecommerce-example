package com.ecommerce.test.validation;


import org.junit.Test;

import com.ecommerce.action.user.CreateEditAction;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;

public class UserActionValidationTests extends AbstractActionValidationTestCase{


	@Override
	protected void setUpRequestTestParams(String [] param){

		request.setParameter("username",param[0]);
		request.setParameter("address",param[1]);
		request.setParameter("password",param[2]);
		request.setParameter("email",param[3]);
		
	}
	
	@Test
	public void testValidationWithNoErros() throws Exception{
		ActionProxy actionProxy = getProxy("/user/editUser",new String []{"username","address","password","email@email.com"});
		CreateEditAction action =  (CreateEditAction) actionProxy.getAction();	
		assertEquals("The validation didn't pass when it should have",
								ActionSupport.SUCCESS,actionProxy.execute());
		assertTrue("There were errors in the validation when passing correct parameters " + action.getFieldErrors(),
								action.getFieldErrors().size() < 1);
	}
	
	
	@Test
	public void testValidationWithThreeErrors() throws Exception{ // Empty field errors

		
		ActionProxy actionProxy = getProxy("/user/editUser",new String [] {"","","","",""});
		CreateEditAction action =  (CreateEditAction) actionProxy.getAction();	
		
		assertEquals("The validation passed when it shouldn't have",
								ActionSupport.INPUT,actionProxy.execute());
		assertTrue(String.format("There were less  than expected "
				+ "errors in the validation when passing incorrect parameters"
				+ "expected 3 but were [%d]",action.getFieldErrors().size()),
								action.getFieldErrors().size() >= 3);
		
	}
	
	@Test
	public void testValidationWithStringLengthErrors() throws Exception{

		
		ActionProxy actionProxy = getProxy("/user/editUser",new String [] {"som","address","pas","email@email.com"});
		CreateEditAction action =  (CreateEditAction) actionProxy.getAction();	
		
		
		
		assertEquals("The validation passed when it shouldn't have",
								ActionSupport.INPUT,actionProxy.execute());
		assertTrue("There were less  than expected "
				+ "errors in the validation when passing incorrect parameters",
								action.getFieldErrors().size() == 1);	

	}
	

}
