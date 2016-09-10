package com.ecommerce.test.validation;

import org.junit.Test;

import com.ecommerce.action.user.OrderAction;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ActionSupport;

public class OrderActionValidationTests extends
		AbstractActionValidationTestCase {

	@Override
	protected void setUpRequestTestParams(String[] param) {
		request.setParameter("address", param[0]);
		request.setParameter("cardNumber", param[1]);
	}

	@Test
	public void testEmptyFieldValidation() throws Exception{
		ActionProxy actionProxy = getProxy("/order/order", new String[]{"",""});
		OrderAction action = (OrderAction) actionProxy.getAction();
		assertEquals("The validation passed when it shouldn't have",
								ActionSupport.INPUT,actionProxy.execute());
		
		assertTrue(String.format("There were less  than expected "
				+ "errors in the validation when passing incorrect parameters"
				+ "expected 2 but were [%d]",action.getFieldErrors().size()),
								action.getFieldErrors().size() == 2);
	}
	
	
	
	@Test
	public void testIncorrectCardNumberError() throws Exception{
		ActionProxy actionProxy = getProxy("/order/order", new String[]{"address","123243344"});
		OrderAction action = (OrderAction) actionProxy.getAction();
		assertEquals("The validation passed when it shouldn't have",
								ActionSupport.INPUT,actionProxy.execute());
		assertTrue(String.format("There were more  than expected "
				+ "errors in the validation when passing incorrect parameters"
				+ "expected 1 but were [%d]",action.getFieldErrors().size()),
								action.getFieldErrors().size() == 1);
	}
	
	@Test
	public void testCorrectCardNumber() throws Exception {
		ActionProxy actionProxy = getProxy("/order/order", new String[]{"address","49927398716"});
		OrderAction action = (OrderAction) actionProxy.getAction();
		actionProxy.execute();
		assertTrue("There were errors in the validation when passing correct parameters " + action.getFieldErrors(),
								action.getFieldErrors().size() < 1);
	}
}
