package com.ecommerce.exception;

import org.springframework.dao.DuplicateKeyException;

public class DuplicateProductNameException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4406789449470321323L;

	public DuplicateProductNameException(DuplicateKeyException de) {
		super(de);
	}
}
