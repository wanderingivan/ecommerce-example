package com.ecommerce.test.validation;


import org.junit.Test;

import com.ecommerce.action.product.CreateEditAction;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;

public class ProductActionValidationTests extends AbstractActionValidationTestCase{


	@Override
	protected void setUpRequestTestParams(String [] param){

		request.setParameter("productName",param[0]);
		request.setParameter("price",param[1]);
		request.setParameter("description",param[2]);
		request.setParameter("category",param[3]);
	}	
	
	@Test
	public void testValidationWithNoErros() throws Exception{
		ActionProxy actionProxy = getProxy("/product/editProduct",new String []{"prname","13.7","description","fighters"});
		CreateEditAction action =  (CreateEditAction) actionProxy.getAction();	
        String result = actionProxy.execute();
        
		assertEquals("The validation didn't pass when it should have :",
								ActionSupport.SUCCESS,result);
		assertTrue("There were errors in the validation when passing correct parameters " + action.getFieldErrors(),
								action.getFieldErrors().size() < 1);
	}
	
	
	@Test
	public void testValidationWithThreeErrors() throws Exception{ // Empty field errors

		
		ActionProxy actionProxy = getProxy("/product/editProduct",new String [] {"","14.56","","",""});
		CreateEditAction action =  (CreateEditAction) actionProxy.getAction();	
        String result = actionProxy.execute();

		assertEquals("The validation passed when it shouldn't have",
								ActionSupport.INPUT,result);
		assertTrue(String.format("There were less  than expected "
				+ "errors in the validation when passing incorrect parameters"
				+ "expected 3 but were [%d]",action.getFieldErrors().size()),
								action.getFieldErrors().size() == 3);
		
	}
	
	@Test
	public void testValidationWithStringLengthErrors() throws Exception{

		
		ActionProxy actionProxy = getProxy("/product/editProduct",new String [] {"name","14.56","desc","fighters"});
		CreateEditAction action =  (CreateEditAction) actionProxy.getAction();	
        String result = actionProxy.execute();
		
		assertEquals("The validation passed when it shouldn't have",
								ActionSupport.INPUT,result);
		assertTrue("There were less  than expected "
				+ "errors in the validation when passing incorrect parameters",
								action.getFieldErrors().size() == 2);	

	}
	

}
