package com.ecommerce.exception;

import org.springframework.dao.DuplicateKeyException;


public class DuplicateEmailException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5051593248412638810L;

	public DuplicateEmailException(DuplicateKeyException de) {
		super(de);
	}

}
