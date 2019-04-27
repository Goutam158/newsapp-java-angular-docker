package com.stackroute.userservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stackroute.userservice.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	
	public Optional<UserEntity> findByEmail(String email);
	
	public UserEntity findByEmailAndPassword(String email,String password);

}
