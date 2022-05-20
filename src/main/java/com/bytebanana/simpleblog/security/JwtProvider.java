package com.bytebanana.simpleblog.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import javax.annotation.PostConstruct;

import com.bytebanana.simpleblog.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.bytebanana.simpleblog.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class JwtProvider {

    private static final String SECRET_KEY = "spring-simple-blog";
    private KeyStore keyStore;
    @Value("${jwt.exp.milli}")
    private Long expirationInMilli;

    @Autowired
    private UserService userService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

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

    public String generateToken(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();

        return Jwts.builder().setSubject(principal.getUsername()).setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(expirationInMilli))).signWith(getPrivateKey())
                .compact();
    }

    public String generateTokenByUsername(String username) {

        return Jwts.builder().setSubject(username).setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusMillis(expirationInMilli))).signWith(getPrivateKey())
                .compact();
    }

    public boolean validateToken(String jwt) {
        try {
            JwtParser jwtParser = buildJwsParse();
            jwtParser.parseClaimsJws(jwt);
            return true;
        } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException
                | IllegalArgumentException e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private JwtParser buildJwsParse() {
        return Jwts.parserBuilder().setSigningKey(getPublicKey()).build();
    }

    public String getUsernameFromJwt(String jwt) {
        JwtParser jwtParser = buildJwsParse();
        Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
        return claims.getSubject();
    }

    public Date getExpiryFromJwt(String jwt) {
        JwtParser jwtParser = buildJwsParse();
        Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
        return claims.getExpiration();
    }

}
