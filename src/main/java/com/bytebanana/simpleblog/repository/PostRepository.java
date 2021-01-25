package com.bytebanana.simpleblog.repository;

import com.bytebanana.simpleblog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bytebanana.simpleblog.entity.Post;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByOrderByCreateDateDesc();
    List<Post> findAllByPublishedAndUserOrderByCreateDateDesc(Boolean isPublished, User user);

    @Query("SELECT p FROM Post p WHERE p.title like ?1 or p.subTitle like ?1 or p.content like ?1 and p.published = ?2")
    List<Post> searchPostByKeyword(String keyword, Boolean published);
    List<Post> findAllBySubTitleContainsAndPublishedOrderByCreateDateDesc(String title, Boolean published);
    List<Post> findAllByContentContainsAndPublishedOrderByCreateDateDesc(String title, Boolean published);
}
