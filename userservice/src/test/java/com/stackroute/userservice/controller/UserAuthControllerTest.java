package com.stackroute.userservice.controller;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.models.UserModel;
import com.stackroute.userservice.service.UserSignUpService;

@RunWith(SpringRunner.class)
@WebMvcTest(UserAuthController.class)
public class UserAuthControllerTest {

	@Autowired
	private MockMvc mvc;
	@Mock
	private Environment env;
	@MockBean
	private UserSignUpService signUpService;

	private UserModel userModel;

	@Before
	public void init() {
		this.userModel = new UserModel();
		userModel.setEmail("test_user@test.com");
		userModel.setPassword("123Test$");
		userModel.setFirstName("Test");
		userModel.setLastName("User");

	}

	@Test
	public void test_login() {
		when(signUpService.login("test_user@test.com", "123Test$")).thenReturn("sample_token");

		try {
			this.mvc.perform(post("/userservice/api/v1/login").content(asJsonString(this.userModel))
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andDo(result -> {
						assertThat(result.getResponse().getContentAsString()).isEqualTo("sample_token");
					});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_login_failed() {
		when(signUpService.login("test_user@test.com", "123Test$")).thenReturn(null);

		try {
			this.mvc.perform(post("/userservice/api/v1/login").content(asJsonString(this.userModel))
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_signup() throws UserAlreadyExistsException, UserNotFoundException {
		when(signUpService.addUser(any())).thenReturn(Boolean.TRUE);
		when(env.getProperty("newsapp.signup.successful"))
		.thenReturn("User signed up successfully with Email ");
				

		try {
			this.mvc.perform(post("/userservice/api/v1/signup").content(asJsonString(this.userModel))
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andDo(result -> {
						assertThat(result.getResponse().getContentAsString())
								.isEqualTo("User signed up successfully with Email " + this.userModel.getEmail());
					});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void test_signup_UserAlreadyExists() throws UserAlreadyExistsException, UserNotFoundException {
		when(signUpService.addUser(any())).thenThrow(new UserAlreadyExistsException("User Already Exists"));
		
		try {
			this.mvc.perform(post("/userservice/api/v1/signup").content(asJsonString(this.userModel))
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String asJsonString(final Object object) {

		try {
			return new ObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
