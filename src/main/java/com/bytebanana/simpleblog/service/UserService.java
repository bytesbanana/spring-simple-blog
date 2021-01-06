package com.bytebanana.simpleblog.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bytebanana.simpleblog.entity.User;
import com.bytebanana.simpleblog.exception.UserNotFoundException;
import com.bytebanana.simpleblog.repository.UserRepositry;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepositry userRepositry;

	public User findByUsername(String username) {
		Optional<User> userOptional = userRepositry.findByUsername(username);
		User user = userOptional.orElseThrow(() -> {
			return new UserNotFoundException("User not found username : " + username);
		});

		return user;

	}
	
	public User findById(Long userId) {
		Optional<User> userOptional = userRepositry.findById(userId);
		User user = userOptional.orElseThrow(() -> {
			return new UserNotFoundException("User not found user id : " + userId);
		});

		return user;
		
	}

}
