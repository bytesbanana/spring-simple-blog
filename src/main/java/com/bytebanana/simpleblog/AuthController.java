package com.bytebanana.simpleblog;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytebanana.simpleblog.dto.LoginRequest;
import com.bytebanana.simpleblog.dto.LoginResponse;
import com.bytebanana.simpleblog.dto.RegisterRequest;
import com.bytebanana.simpleblog.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
		return ResponseEntity.ok(authService.login(loginRequest));
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody RegisterRequest registerRequest){
		authService.register(registerRequest);
		return ResponseEntity.ok().build();
	}
	
	
}
