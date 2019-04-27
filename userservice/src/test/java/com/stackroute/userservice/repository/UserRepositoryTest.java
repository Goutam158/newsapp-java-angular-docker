package com.stackroute.userservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.stackroute.userservice.entity.UserEntity;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class UserRepositoryTest {
	@Autowired
	UserRepository repository;
	@Autowired
	TestEntityManager entityManager;
	
	@Before
	public void init() {
		UserEntity user = new UserEntity();
		user.setFirstName("Test");
		user.setLastName("User");
		user.setEmail("test.user@exmaple.com");
		user.setPassword("Test@pass1");
		user.setCreated(LocalDateTime.now());
		entityManager.persist(user);
		entityManager.flush();
	}

	@Test
	public void whenFindByEmail() {
		Optional<UserEntity> userOpt  = repository.findByEmail("test.user@exmaple.com");
		if(userOpt.isPresent()) {
			UserEntity user = userOpt.get();
			assertThat(user).isNotNull();
			assertThat(user.getFirstName()).isEqualTo("Test");
			assertThat(user.getLastName()).isEqualTo("User");
			assertThat(user.getEmail()).isEqualTo("test.user@exmaple.com");
			assertThat(user.getPassword()).isEqualTo("Test@pass1");
		}
	}

	@Test
	public void whenFindByEmailAndPassword() {
		UserEntity user  = repository.findByEmailAndPassword("test.user@exmaple.com","Test@pass1");
		assertThat(user).isNotNull();
		assertThat(user.getFirstName()).isEqualTo("Test");
		assertThat(user.getLastName()).isEqualTo("User");
		assertThat(user.getEmail()).isEqualTo("test.user@exmaple.com");
		assertThat(user.getPassword()).isEqualTo("Test@pass1");
	}

	

}
