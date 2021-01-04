package com.bytebanana.simpleblog.service;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;

@Service
public class JwtService {

	private static final String SECRET_KEY = "spring-simple-blog";
	private KeyStore keyStore;
	
	@Autowired
	private UserService userService;
	

	@PostConstruct
	public void init() {
		try {
			keyStore = KeyStore.getInstance("JKS");
			InputStream inputStream = getClass().getClassLoader().getResourceAsStream("spring-simple-blog.jks");
			keyStore.load(inputStream, SECRET_KEY.toCharArray());
		} catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
			e.printStackTrace();
		}
	}
	
	public String generateToken(String emaill,String password) {
//		userService.findByEmail(email)
		
		
		return null;
	}

	public PrivateKey getPrivateKey() {
		PrivateKey privateKey = null;
		try {
			privateKey = (PrivateKey) keyStore.getKey("spring-simple-blog", SECRET_KEY.toCharArray());
		} catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return privateKey;
	}

	public PublicKey getPublicKey() {
		PublicKey publicKey = null;
		try {
			publicKey = keyStore.getCertificate("spring-simple-blog").getPublicKey();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return publicKey;
	}

	public String generateTokenFromEmail(String email) {
		String token = Jwts.builder().setSubject(email).signWith(getPrivateKey()).compact();
		return token;
	}

}
