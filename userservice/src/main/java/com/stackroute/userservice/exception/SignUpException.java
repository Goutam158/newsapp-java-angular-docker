package com.stackroute.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SignUpException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8531601069453472933L;

	public SignUpException(String meesage) {
		super(meesage);
	}

}
