package com.bytebanana.simpleblog.repository;

import com.bytebanana.simpleblog.entity.Post;
import com.bytebanana.simpleblog.entity.User;
import com.bytebanana.simpleblog.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findByPostAndUser(Post post, User user);

    List<Vote> findByPostAndVote(Post post, Boolean isLike);

}
