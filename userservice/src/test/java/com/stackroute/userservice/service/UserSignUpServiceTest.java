package com.stackroute.userservice.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.userservice.entity.UserEntity;
import com.stackroute.userservice.exception.UserAlreadyExistsException;
import com.stackroute.userservice.exception.UserNotFoundException;
import com.stackroute.userservice.models.UserModel;
import com.stackroute.userservice.repository.UserRepository;

import io.jsonwebtoken.Jwts;

@RunWith(SpringRunner.class)
public class UserSignUpServiceTest {
	@Mock
	private Environment env; 

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserSignUpService userService;

	private UserEntity user;


	@Before
	public void init() {
		user = new UserEntity();
		user.setId(1);
		user.setFirstName("Test");
		user.setLastName("User");
		user.setEmail("test.user@exmaple.com");
		user.setPassword("Test@pass1");
		user.setCreated(LocalDateTime.now());
	}
	
	@Test
	public void whenLogin() {
		when(userRepository.findByEmailAndPassword("test.user@exmaple.com", "Test@pass1"))
		.thenReturn(this.user);
		
		String token = userService.login("test.user@exmaple.com", "Test@pass1");
		String email = Jwts
				.parser()
				.setSigningKey("mc&ll$m")
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
		assertThat(email)
		.isEqualTo("test.user@exmaple.com");
	}
	
	@Test
	public void whenLoginError() {
		when(userRepository.findByEmailAndPassword("test.user@exmaple.com", "Test@pass1"))
		.thenReturn(null);
		
		String token = userService.login("test.user@exmaple.com", "Test@pass1");
		assertThat(token)
		.isNull();
	}

	@Test
	public void whenAdduser() throws UserAlreadyExistsException, UserNotFoundException {

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(null));
		
		when(userRepository.saveAndFlush(any(UserEntity.class))).thenReturn(this.user);

		UserModel userModel =  new UserModel();
		userModel.setFirstName("Test");
		userModel.setLastName("User");
		userModel.setEmail("test.user@exmaple.com");
		userModel.setPassword("Test@pass1");
		userModel.setCreated(LocalDateTime.now());

		assertThat(userService.addUser(userModel))
		.isTrue();
		verify(userRepository,times(1))
		.saveAndFlush(any(UserEntity.class));
	}
	
	@Test
	public void whenAdduserReturnFalse() throws UserAlreadyExistsException, UserNotFoundException {

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(null));
		
		when(userRepository.saveAndFlush((UserEntity)any())).thenReturn(null);


		UserModel userModel =  new UserModel();
		userModel.setFirstName("Test");
		userModel.setLastName("User");
		userModel.setEmail("test.user@exmaple.com");
		userModel.setPassword("Test@pass1");
		userModel.setCreated(LocalDateTime.now());

		assertThat(userService.addUser(userModel))
		.isFalse();
		verify(userRepository,times(1))
		.saveAndFlush((UserEntity)any());
	}

	@Test
	public void whenAdduserUserNotFoundException(){
		when(env.getProperty("newsapp.user.cannot.be.null"))
		.thenReturn("User cannot be null");
		try {
			userService.addUser(null);
		}catch(Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("User cannot be null");
		}
	}
	
	@Test
	public void whenAdduserUserAlreadyExistsException(){
		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(this.user));
		
		when(env.getProperty("newsapp.exiting.user"))
		.thenReturn("test.user@exmaple.com Already Exists");
		
		UserModel userModel =  new UserModel();
		userModel.setFirstName("Test");
		userModel.setLastName("User");
		userModel.setEmail("test.user@exmaple.com");
		userModel.setPassword("Test@pass1");
		userModel.setCreated(LocalDateTime.now());
		try {
			userService.addUser(userModel);
		}catch(Exception e) {
			assertThat(e).isInstanceOf(UserAlreadyExistsException.class);
			assertThat(e.getMessage()).isEqualTo(user.getEmail()+" Already Exists");
		}
	}
	

	@Test
	public void whenUpdateUser() throws UserNotFoundException, UserAlreadyExistsException {

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(this.user));
		
		when(userRepository.saveAndFlush((UserEntity)any())).thenReturn(this.user);
		
		UserModel userModel =  new UserModel();
		userModel.setFirstName("Test");
		userModel.setLastName("User");
		userModel.setEmail("test.user@exmaple.com");
		userModel.setPassword("Test@pass1");
		userModel.setCreated(LocalDateTime.now());

		assertThat(userService.updateUser(userModel))
		.isTrue();
		verify(userRepository,times(1))
		.saveAndFlush((UserEntity)any());
	}
	

	@Test
	public void whenUpdateUserReturnFalse() throws UserNotFoundException, UserAlreadyExistsException {

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(this.user));
		
		when(userRepository.saveAndFlush((UserEntity)any())).thenReturn(null);

		UserModel userModel =  new UserModel();
		userModel.setFirstName("Test");
		userModel.setLastName("User");
		userModel.setEmail("test.user@exmaple.com");
		userModel.setPassword("Test@pass1");
		userModel.setCreated(LocalDateTime.now());

		assertThat(userService.updateUser(userModel))
		.isFalse();
		verify(userRepository,times(1))
		.saveAndFlush((UserEntity)any());
	}
	
	@Test
	public void whenUpdateUserUserNotFoundExceptionBlankUser(){
		when(env.getProperty("newsapp.user.cannot.be.null"))
		.thenReturn("User cannot be null");
		
		try {
			userService.updateUser(null);
		}catch(Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("User cannot be null");
		}
	}
	
	@Test
	public void whenUpdateUserUserNotFoundException(){
		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(null));
		
		when(env.getProperty("newsapp.user.cannot.be.null"))
		.thenReturn("User cannot be null");
		
		UserModel userModel =  new UserModel();
		userModel.setFirstName("Test");
		userModel.setLastName("User");
		userModel.setEmail("test.user@exmaple.com");
		userModel.setPassword("Test@pass1");
		userModel.setCreated(LocalDateTime.now());
		try {
			userService.addUser(userModel);
		}catch(Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("User with email "+user.getEmail()+" Not found");
		}
	}
	
	@Test
	public void whenDeleteUser() throws UserNotFoundException {

		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(this.user));
		
		UserModel userModel =  new UserModel();
		userModel.setFirstName("Test");
		userModel.setLastName("User");
		userModel.setEmail("test.user@exmaple.com");
		userModel.setPassword("Test@pass1");
		userModel.setCreated(LocalDateTime.now());
		
		assertThat(userService.deleteUser(userModel))
		.isTrue();
		verify(userRepository,times(1))
		.delete((UserEntity)any());
	}
	
	@Test
	public void whenDeleteUserUserNotFoundExceptionBlankUser(){
		when(env.getProperty("newsapp.user.cannot.be.null"))
		.thenReturn("User cannot be null");
		
		try {
			userService.deleteUser(null);
		}catch(Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("User cannot be null");
		}
	}
	
	
	@Test
	public void whenDeleteUserUserNotFoundException(){
		when(userRepository.findByEmail("test.user@exmaple.com"))
		.thenReturn(Optional.ofNullable(null));
		
		when(env.getProperty("newsapp.user.not.found.with.email"))
				.thenReturn("User with not found with email");
		UserModel userModel =  new UserModel();
		userModel.setFirstName("Test");
		userModel.setLastName("User");
		userModel.setEmail("test.user@exmaple.com");
		userModel.setPassword("Test@pass1");
		userModel.setCreated(LocalDateTime.now());
		
		try {
			userService.deleteUser(userModel);
		}catch(Exception e) {
			assertThat(e).isInstanceOf(UserNotFoundException.class);
			assertThat(e.getMessage()).isEqualTo("User with not found with email "+user.getEmail());
		}
	}
	

}
