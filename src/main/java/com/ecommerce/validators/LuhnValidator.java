package com.ecommerce.validators;
import com.ecommerce.util.LuhnTestUtil;
import com.opensymphony.xwork2.validator.ValidationException;
import com.opensymphony.xwork2.validator.validators.FieldValidatorSupport;


/**
 * 
 * Luhn Test validator for credit card numbers 
 *
 */
public class LuhnValidator extends FieldValidatorSupport {

	@Override
	public void validate(Object object) throws ValidationException {
		String value = (String) this.getFieldValue(getFieldName(), object);
		value = value.trim();
		if(!LuhnTestUtil.validate(value)){
			addFieldError(getFieldName(),object);
		}
		
		
	}

}
