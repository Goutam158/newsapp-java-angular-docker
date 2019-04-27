package com.stackroute.favouriteservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ArticleAllreadyExistsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8531601069453472933L;

	public ArticleAllreadyExistsException(String meesage) {
		super(meesage);
	}

}
