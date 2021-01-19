package com.bytebanana.simpleblog.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bytebanana.simpleblog.dto.PostRequest;
import com.bytebanana.simpleblog.dto.PostResponse;
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
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final UserService userService;

    public List<Post> findAllPost() {
        List<Post> posts = postRepository.findAllByOrderByCreateDateDesc();
        return posts;
    }

    public PostResponse findPostById(Long postId) {
        Optional<Post> postOp = postRepository.findById(postId);
        Post post = postOp.orElseThrow(() -> new PostNotFoundException("Post not found id :" + postId));
        PostResponse postResponse = postMapper.mapToResponse(post);
        return postResponse;
    }

    public PostResponse createNewPost(PostRequest postRequest) {
        Post mappedPost = postMapper.mapDtoToPost(postRequest);
        mappedPost.setPostId(0L);
        mappedPost.setCreateDate(Instant.now());
        mappedPost.setPublished(postRequest.getPublished());
        User user = getCurrentUser();
        mappedPost.setUser(user);
        Post post = postRepository.save(mappedPost);

        PostResponse postResp = postMapper.mapToResponse(post);

        return postResp;
    }

    public Post updatePost(Long postId, PostRequest updatePostRequest) {
        Post post = postMapper.mapDtoToPost(updatePostRequest);
        Optional<Post> postOp = postRepository.findById(postId);
        Post existingPost = postOp.orElseThrow(() -> new PostNotFoundException("Post not found id :" + postId));

        post.setPostId(postId);
        post.setLastUpdateDate(Instant.now());
        post.setUser(getCurrentUser());
        post.setCreateDate(existingPost.getCreateDate());;

        if (userIsPostOwner(existingPost)) {
            throw new SpringSimpleBlogException("Cannot update post id" + post.getPostId());
        }

        return postRepository.save(post);
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
        Optional<Post> postOp = postRepository.findById(postId);
        Post post = postOp.orElseThrow(() -> new PostNotFoundException("Post not found id :" + postId));

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

    public List<PostResponse> findMyDraftPost() {
        List<PostResponse> postResponses = findMyPostByPublished(false);
        return postResponses;
    }

    public List<PostResponse> findMyPublishedPost() {
        List<PostResponse> postResponses = findMyPostByPublished(true);
        return postResponses;
    }

    private List<PostResponse> findMyPostByPublished(boolean isPublished) {
        User currentUser = getCurrentUser();
        List<Post> posts = postRepository.findAllByPublishedAndUserOrderByCreateDateDesc(isPublished, currentUser);
        List<PostResponse> postResponses = posts.stream().map(
                (post) -> postMapper.mapToResponse(post)
        ).collect(Collectors.toList());
        return postResponses;
    }

}
