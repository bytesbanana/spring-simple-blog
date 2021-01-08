package com.bytebanana.simpleblog.controller;

import com.bytebanana.simpleblog.dto.CommentResponse;
import com.bytebanana.simpleblog.dto.PostRequest;
import com.bytebanana.simpleblog.dto.PostResponse;
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
        List<PostResponse> postResponseList = postList.stream()
                .map(post -> postMapper.mapToResponse(post))
                .collect(Collectors.toList());
        return ResponseEntity.ok(postResponseList);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostById(@PathVariable("postId") Long postId) {
        Post post = postService.findPostById(postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.createNewPost(postRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@PathVariable("postId") Long postId ,@RequestBody PostRequest postRequest) {
        postService.updatePost(postId,postRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId) {
        postService.delete(postId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentResponse>> findAllCommentOfPost(@PathVariable("postId") Long postId){
        List<CommentResponse> commentResponses = commentService.findAllByPostId(postId);
        return ResponseEntity.ok(commentResponses);
    }
}
