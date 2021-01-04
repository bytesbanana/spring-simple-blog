package com.bytebanana.simpleblog.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bytebanana.simpleblog.entity.User;
import com.bytebanana.simpleblog.repository.UserRepositry;
import com.bytebanana.simpleblog.exception.UserNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepositry userRepositry;

	public User findByEmail(String email) {
		Optional<User> userOptional = userRepositry.findByEmail(email);
		User user = userOptional.orElseThrow(() -> new UserNotFoundException(""));

		return user;
	}
	

}
