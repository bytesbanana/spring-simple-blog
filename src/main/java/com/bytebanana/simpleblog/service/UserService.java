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

}
