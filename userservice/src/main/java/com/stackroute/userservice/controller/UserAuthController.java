package com.stackroute.userservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.userservice.exception.InvalidRequestException;
import com.stackroute.userservice.exception.SignUpException;
import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.models.UserModel;
import com.stackroute.userservice.service.UserSignUpService;

@RestController
@CrossOrigin(origins= {"http://localhost:4200"},maxAge = 4800)
@RequestMapping("/userservice/api/v1")
public class UserAuthController {

	@Autowired
	private Environment environment;
	@Autowired
	private UserSignUpService signUpService;

	/**
	 * User Login Endpoint
	 * @param email
	 * @param password
	 * @return
	 * @throws UserNotFoundException 
	 */
	@PostMapping(value = "/login", produces="text/html",consumes="application/json")
	public ResponseEntity<String> login(@RequestBody UserModel user) throws UserNotFoundException {
		String token = signUpService.login(user.getEmail(), user.getPassword());
		if(null ==  token) {
			throw new UserNotFoundException(environment.getProperty("newsapp.auth.filter.no.valid.user"));
		}
		return new ResponseEntity<String>(token, HttpStatus.OK);
		
	}

	/**
	 * User Registration Endpoint
	 * @param newUser
	 * @return
	 * @throws InvalidRequestException
	 * @throws SignUpException
	 * @throws UserAlreadyExistsException
	 * @throws UserNotFoundException
	 */
	@PostMapping(value = "/signup",produces="text/html", consumes="application/json")
	public ResponseEntity<String> signup(@Valid @RequestBody UserModel newUser)
			throws InvalidRequestException, SignUpException, UserAlreadyExistsException, UserNotFoundException {
		if (StringUtils.isEmpty(newUser.getEmail())) {
			throw new InvalidRequestException(environment.getProperty("newsapp.user.cannot.be.null"));
		}
		boolean status = signUpService.addUser(newUser);
		if (status) {
			return new ResponseEntity<String>(environment.getProperty("newsapp.signup.successful") + newUser.getEmail(),
					HttpStatus.CREATED);
		} else {
			throw new SignUpException(environment.getProperty("newsapp.user.cannot.be.null") + newUser.getEmail());
		}

	}

}
