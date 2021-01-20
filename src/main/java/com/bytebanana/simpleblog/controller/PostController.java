package com.bytebanana.simpleblog.controller;

import com.bytebanana.simpleblog.dto.CommentResponse;
import com.bytebanana.simpleblog.dto.PostRequest;
import com.bytebanana.simpleblog.dto.PostResponse;
import com.bytebanana.simpleblog.entity.Post;
import com.bytebanana.simpleblog.mapper.PostMapper;
import com.bytebanana.simpleblog.service.CommentService;
import com.bytebanana.simpleblog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        List<CommentResponse> commentResponses = commentService.findAllByPostId(postId);
        return ResponseEntity.ok(commentResponses);
    }

    @PostMapping("/{postId}/checkpermission")
    public ResponseEntity<Void> checkUserPermission(@PathVariable("postId") Long postId){
        postService.isUserOwnedPost(postId);
        return ResponseEntity.ok().build();
    }
}
