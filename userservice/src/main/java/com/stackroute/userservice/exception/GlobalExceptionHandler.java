package com.stackroute.userservice.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<?> userNotFoundException(UserNotFoundException ex, WebRequest request) {
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(Boolean.FALSE));
		return new ResponseEntity<>(details, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidRequestException.class)
	public ResponseEntity<?> invalidRequestException(InvalidRequestException ex, WebRequest request) {
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(Boolean.FALSE));
		return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<?> userAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(Boolean.FALSE));
		return new ResponseEntity<>(details, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(SignUpException.class)
	public ResponseEntity<?> signUpRequestException(SignUpException ex, WebRequest request) {
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(Boolean.FALSE));
		return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
		ErrorDetails details = new ErrorDetails(LocalDateTime.now(), ex.getMessage(),
				request.getDescription(Boolean.FALSE));
		return new ResponseEntity<>(details, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
