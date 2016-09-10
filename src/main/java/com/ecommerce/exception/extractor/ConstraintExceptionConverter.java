package com.ecommerce.exception.extractor;


import org.springframework.dao.DuplicateKeyException;

import com.ecommerce.exception.DuplicateEmailException;
import com.ecommerce.exception.DuplicateProductNameException;
import com.ecommerce.exception.DuplicateUsernameException;

public class ConstraintExceptionConverter {
	
	
	public static RuntimeException convertException(DuplicateKeyException de){
		
		String message = de.getCause().getMessage();
		if(message.contains("email")){
			return new DuplicateEmailException(de);
		}else if(message.contains("username")){
			return new DuplicateUsernameException(de);
		}else if (message.contains("name")){
			return new DuplicateProductNameException(de);
		}else{
			return de;
		}
		
	}

}
