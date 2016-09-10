package com.ecommerce.exception;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateUsernameException extends RuntimeException {

	public DuplicateUsernameException(DuplicateKeyException de) {
		super(de);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -600172034697453054L;

}
