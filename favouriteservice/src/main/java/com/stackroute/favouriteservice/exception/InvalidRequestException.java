package com.stackroute.favouriteservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidRequestException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8531601069453472933L;

	public InvalidRequestException(String meesage) {
		super(meesage);
	}

}
