package com.bytebanana.simpleblog.repository;

import com.bytebanana.simpleblog.entity.Comment;
import com.bytebanana.simpleblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findAllByPostOrderByCreateDateAsc(Post post);
}
