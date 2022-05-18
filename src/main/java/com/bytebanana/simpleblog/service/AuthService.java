package com.bytebanana.simpleblog.service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import com.bytebanana.simpleblog.dto.RefreshTokenRequest;
import com.bytebanana.simpleblog.entity.RefreshToken;
import com.bytebanana.simpleblog.exception.UserNotFoundException;
import com.bytebanana.simpleblog.repository.UserRepositry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bytebanana.simpleblog.dto.AuthenticationRequest;
import com.bytebanana.simpleblog.dto.AuthenticationResponse;
import com.bytebanana.simpleblog.dto.RegisterRequest;
import com.bytebanana.simpleblog.entity.User;
import com.bytebanana.simpleblog.entity.VerificationToken;
import com.bytebanana.simpleblog.exception.SpringSimpleBlogException;
import com.bytebanana.simpleblog.model.NotificationEmail;
import com.bytebanana.simpleblog.repository.VerificationTokenRepository;
import com.bytebanana.simpleblog.security.JwtProvider;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class AuthService {

    @Value("${server.port}")
    private int serverPort;
    private final UserRepositry userRepositry;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);
        Instant expiry = jwtProvider.getExpiryFromJwt(token).toInstant();

        RefreshToken refreshToken = refreshTokenService.generateRefreshToken();
        return AuthenticationResponse.builder()
                .token(token)
                .username(authenticationRequest.getUsername())
                .refreshToken(refreshToken.getToken())
                .expiry(expiry)
                .build();
    }

    public void register(RegisterRequest registerRequest) {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEnable(false);

        User savedUser = userRepositry.save(user);
        String token = UUID.randomUUID().toString();

        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setUser(savedUser);
        verificationTokenRepository.save(verificationToken);

        NotificationEmail notificationEmail = new NotificationEmail();
        notificationEmail.setSubject("Account Verification");
        notificationEmail.setRecipient(user.getEmail());
        notificationEmail
                .setBody("<a href='http://localhost:" + serverPort + "/api/auth/verifyAccount/" + token + "'> Activation Link </a>");

        mailService.sendMail(notificationEmail);

    }

    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationTokenOptional = verificationTokenRepository.findByToken(token);
        VerificationToken verificationToken = verificationTokenOptional.orElseThrow(() -> new SpringSimpleBlogException("Cannot verify account"));

        User user = verificationToken.getUser();
        user.setEnable(true);

        userRepositry.save(user);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User secUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
        String username = secUser.getUsername();
        Optional<User> userOp = userRepositry.findByUsername(username);
        return userOp.orElseThrow(() -> new UserNotFoundException("User not found username:" + username));
    }

    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());

        String token = jwtProvider.generateTokenByUsername(refreshTokenRequest.getUsername());
        Instant expiry = jwtProvider.getExpiryFromJwt(token).toInstant();

        return AuthenticationResponse.builder()
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .token(token)
                .username(refreshTokenRequest.getUsername())
                .expiry(expiry)
                .build();
    }
}
