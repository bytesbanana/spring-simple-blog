package com.bytebanana.simpleblog.service;

import com.bytebanana.simpleblog.entity.RefreshToken;
import com.bytebanana.simpleblog.exception.SpringSimpleBlogException;
import com.bytebanana.simpleblog.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
@Service
@AllArgsConstructor
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(0L);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    public void validateRefreshToken(String refreshToken) {
        refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new SpringSimpleBlogException("Invalid refresh token"));
    }

    public void removeRefreshToken(String token){
        refreshTokenRepository.deleteByToken(token);
    }


}
