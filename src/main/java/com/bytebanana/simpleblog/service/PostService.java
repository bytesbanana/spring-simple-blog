package com.bytebanana.simpleblog.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.bytebanana.simpleblog.dto.PostRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bytebanana.simpleblog.entity.Post;
import com.bytebanana.simpleblog.entity.User;
import com.bytebanana.simpleblog.exception.PostNotFoundException;
import com.bytebanana.simpleblog.exception.SpringSimpleBlogException;
import com.bytebanana.simpleblog.mapper.PostMapper;
import com.bytebanana.simpleblog.repository.PostRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;

    public List<Post> findAllPost() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }

    public Post findPostById(Long postId) {
        Optional<Post> postOp = postRepository.findById(postId);
        Post post = postOp.orElseThrow(() -> new PostNotFoundException("Post not found id :" + postId));

        return post;
    }

    public void createNewPost(PostRequest postRequest) {
        Post post = postMapper.mapCreateDtoToPost(postRequest);
        post.setPostId(0L);
        post.setCreateDate(Instant.now());
        User user = getCurrentUser();
        post.setUser(user);
        postRepository.save(post);
    }

    public void updatePost(Long postId, PostRequest updatePostRequest) {
        Post post = postMapper.mapCreateDtoToPost(updatePostRequest);
        post.setPostId(postId);
        post.setLastUpdateDate(Instant.now());
        Post existingPost = findPostById(post.getPostId());
        if (userIsPostOwner(existingPost)) {
            throw new SpringSimpleBlogException("Cannot update post id" + post.getPostId());
        }

        postRepository.save(post);
    }

    private boolean userIsPostOwner(Post existingPost) {
        User user = getCurrentUser();
        return existingPost.getUser() != user;
    }

    private boolean isUpdatePost(Post post) {
        return post.getPostId() != 0;
    }

    public void delete(Long postId) {

        User user = getCurrentUser();
        Post post = findPostById(postId);

        if (post.getUser() != user) {
            throw new SpringSimpleBlogException("Cannot delete post id:" + postId);
        }
        postRepository.delete(post);

    }

    private User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User currentUser = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        User user = userService.findByUsername(currentUser.getUsername());
        return user;
    }

}
