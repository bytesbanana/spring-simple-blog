package com.bytebanana.simpleblog.controller;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bytebanana.simpleblog.dto.PostRequest;
import com.bytebanana.simpleblog.entity.Post;
import com.bytebanana.simpleblog.entity.User;
import com.bytebanana.simpleblog.service.PostService;
import com.bytebanana.simpleblog.service.UserService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

	private final PostService postService;
	private final UserService userService;

	@GetMapping
	public ResponseEntity<List<Post>> getAllPost() {
		List<Post> postList = postService.findAllPost();
		return ResponseEntity.ok(postList);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<Post> getPostById(@PathVariable("postId") Long postId) {
		Post post = postService.findPostById(postId);
		return ResponseEntity.ok(post);
	}

	public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
		postRequest.setCreateDate(Instant.now());
		postService.save(postRequest);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{postId}")
	public ResponseEntity<Void> updatePost(@RequestBody PostRequest postRequest) {

		postRequest.setLastUpdateDate(Instant.now());
		postService.save(postRequest);
		return ResponseEntity.noContent().build();
	}


	@DeleteMapping("/{postId}")
	public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId ){
		postService.delete(postId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
