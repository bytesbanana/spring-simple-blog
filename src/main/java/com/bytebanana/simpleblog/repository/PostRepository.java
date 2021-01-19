package com.bytebanana.simpleblog.repository;

import com.bytebanana.simpleblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bytebanana.simpleblog.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreateDateDesc();
    List<Post> findAllByPublishedAndUserOrderByCreateDateDesc(Boolean isPublished, User user);
}
