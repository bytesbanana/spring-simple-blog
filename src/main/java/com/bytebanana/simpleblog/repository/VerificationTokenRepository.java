package com.bytebanana.simpleblog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bytebanana.simpleblog.entity.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
	public Optional<VerificationToken> findByToken(String token);
}
