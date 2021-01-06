package com.bytebanana.simpleblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bytebanana.simpleblog.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

}
