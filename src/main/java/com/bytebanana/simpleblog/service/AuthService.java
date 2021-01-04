package com.bytebanana.simpleblog.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bytebanana.simpleblog.dto.LoginRequest;
import com.bytebanana.simpleblog.dto.LoginResponse;
import com.bytebanana.simpleblog.dto.RegisterRequest;
import com.bytebanana.simpleblog.entity.User;
import com.bytebanana.simpleblog.model.NotificationEmail;
import com.bytebanana.simpleblog.repository.UserRepositry;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

	private final UserRepositry userRepositry;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;

	public LoginResponse login(LoginRequest loginRequest) {
		String token = jwtService.generateTokenFromEmail(loginRequest.getEmail());
		
		return LoginResponse.builder().token(token).userId(-1L).build();
	}
	
	public void register(RegisterRequest registerRequest) {
		User user = new User();
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		user.setFirstName(registerRequest.getFirstName());
		user.setLastName(registerRequest.getLastName());
		user.setEnable(false);
		
		userRepositry.save(user);
		NotificationEmail notificationEmail = new NotificationEmail();
		notificationEmail.setSubject("Account Verification");
		notificationEmail.setRecipient(user.getEmail());
		notificationEmail.setBody("<a href='http://localhost:8080/api/verifyAccount'> Activation Link </a>");
		
		mailService.sendMail(notificationEmail);
		
		
	}
}
