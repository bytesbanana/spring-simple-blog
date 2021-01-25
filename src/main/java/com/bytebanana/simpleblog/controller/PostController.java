package com.bytebanana.simpleblog.controller;

import com.bytebanana.simpleblog.dto.*;
import com.bytebanana.simpleblog.entity.Post;
import com.bytebanana.simpleblog.mapper.PostMapper;
import com.bytebanana.simpleblog.service.CommentService;
import com.bytebanana.simpleblog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;
    private final PostMapper postMapper;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPost() {
        List<Post> postList = postService.findAllPost();
        List<PostResponse> postResponseList =
                postList.stream()
                        .map(postMapper::mapToResponse)
                        .collect(Collectors.toList());
        return ResponseEntity.ok(postResponseList);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable("postId") Long postId) {
        PostResponse postResponse = postService.findPostById(postId);
        return ResponseEntity.ok(postResponse);
    }

    @PostMapping("/search")
    public ResponseEntity<List<PostResponse>> searchPost(@RequestParam("keyword") String keyword){

        return ResponseEntity.ok(postService.searchPost(keyword));
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        return ResponseEntity.ok(postService.createNewPost(postRequest));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable("postId") Long postId, @RequestBody PostRequest postRequest) {
        System.out.println(postRequest);
        Post post = postService.updatePost(postId, postRequest);
        PostResponse postResponse = postMapper.mapToResponse(post);
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId) {
        postService.delete(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> findAllCommentOfPost(@PathVariable("postId") Long postId) {
        List<CommentResponse> commentResponses = commentService.findAllCommentByPostId(postId);
        return ResponseEntity.ok(commentResponses);
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<Void> addCommentToPost(@PathVariable("postId") Long postId, @RequestBody  CommentRequest request) {
        commentService.addComment(postId, request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/checkpermission")
    public ResponseEntity<Void> checkUserPermission(@PathVariable("postId") Long postId) {
        postService.isUserOwnedPost(postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/vote")
    public ResponseEntity<Void> votePost(@PathVariable("postId") Long postId,@RequestParam("like") Boolean isLike){
        postService.votePost(postId,isLike);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{postId}/voteCount")
    public ResponseEntity<VoteResponse> getCountVote(@PathVariable("postId") Long postId){

        return ResponseEntity.ok(postService.findCountVote(postId));
    }



}
