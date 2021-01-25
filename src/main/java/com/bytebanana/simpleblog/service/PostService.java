package com.bytebanana.simpleblog.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bytebanana.simpleblog.dto.PostRequest;
import com.bytebanana.simpleblog.dto.PostResponse;
import com.bytebanana.simpleblog.dto.VoteResponse;
import com.bytebanana.simpleblog.entity.Vote;
import com.bytebanana.simpleblog.repository.VoteRepository;
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
    private final AuthService authService;
    private final VoteRepository voteRepository;

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
        post.setCreateDate(existingPost.getCreateDate());
        ;

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

    public void isUserOwnedPost(Long postId) {
        Post postForCheck = postRepository.findById(postId).orElseThrow(
                () -> new PostNotFoundException("Post not found id: " + postId)
        );

        User currentUser = getCurrentUser();
        if (!currentUser.equals(postForCheck.getUser())) {
            throw new SpringSimpleBlogException("User have no permisson");
        }
    }

    public List<PostResponse> searchPost(String keyword) {
        List<Post> allFoundPost = new ArrayList<>();
        boolean isPublished = true;
        keyword = "%" + keyword + "%";
        allFoundPost.addAll(postRepository.searchPostByKeyword(keyword, isPublished));

        List<PostResponse> searchResult = allFoundPost.stream()
                .map(postMapper::mapToResponse)
                .collect(Collectors.toList());

        return searchResult;
    }

    public void votePost(Long postId, Boolean isLike) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found id:" + postId));
        User user = authService.getCurrentUser();

        Vote vote = voteRepository.findByPostAndUser(post, user).orElseGet(() -> null);

        if (vote == null) {
            vote = new Vote();
            vote.setVoteId(0L);
            vote.setVote(isLike);
            vote.setPost(post);
            vote.setUser(user);
        }
        vote.setVote(isLike);

        voteRepository.save(vote);

    }

    public VoteResponse findCountVote(Long postId) {
        long voteCount = 0;
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found id:" + postId));
        User user = authService.getCurrentUser();

        List<Vote> voteList = voteRepository.findByPostAndVote(post, true);
        voteCount = voteList.size();
        Vote vote = voteRepository.findByPostAndUser(post, user).orElseGet(()->null);

        Boolean currentUserVoted = true;

        if (vote == null ) {
            currentUserVoted = false;
        }else{
            if(!vote.getVote()){
                currentUserVoted = false;
            }
        }

        return VoteResponse.builder()
                .currentUserVoted(currentUserVoted)
                .voteCount(voteCount)
                .build();
    }
}
