package com.bytebanana.simpleblog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bytebanana.simpleblog.entity.User;

public interface UserRepositry extends JpaRepository<User, Long>{
	public Optional<User> findByEmail(String email);
}
