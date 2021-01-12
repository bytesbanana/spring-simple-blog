package com.bytebanana.simpleblog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
	
	private String username;
	private String token;
	private String refreshToken;
	private Instant expiry;
}	
