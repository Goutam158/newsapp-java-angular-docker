package com.stackroute.userservice.util;

import static java.util.Objects.nonNull;

import java.util.Date;
import java.util.ResourceBundle;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;

import com.stackroute.userservice.entity.UserEntity;
import com.stackroute.userservice.models.UserModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UserAuthUtil {
	
	static ResourceBundle applicationProperties = ResourceBundle.getBundle("application");
	/**
	 * Utility method for converting model to
	 * Entity
	 * @param user
	 * @return UserEntity
	 */
	public static UserEntity convertUserModelToEntity(UserModel user) {
		UserEntity entity = null;
		if (nonNull(user)) {
			entity = new UserEntity();
			BeanUtils.copyProperties(user, entity);
		}
		return entity;
	}

	/**
	 * Utility method for converting entity to
	 * model
	 * @param entity
	 * @return UserModel
	 */
	public static UserModel convertEntitytoUserModel(UserEntity entity) {
		UserModel user = null;
		if (nonNull(entity)) {
			user = new UserModel();
			BeanUtils.copyProperties(entity, user);
		}
		return user;
	}
	
	/**
	 * Utility method to form JWT token
	 * @param email
	 * @return Token
	 */
	public static String generateToken(String email) {
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.signWith(SignatureAlgorithm.HS256, applicationProperties.getString("newsapp.jwt.secret.key"))
				.compact();
	}

}
