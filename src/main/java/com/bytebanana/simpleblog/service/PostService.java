package com.bytebanana.simpleblog.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.bytebanana.simpleblog.dto.PostRequest;
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

	public void save(PostRequest postRequest) {
		Post post = postMapper.mapToPost(postRequest);
		if(isUpdatePost(post)) {
			Post existingPost = findPostById(post.getPostId());
			if(userIsPostOwner(post, existingPost)) {
				throw new SpringSimpleBlogException("Cannot update post id" + post.getPostId());
			}
		}	
		postRepository.save(post);
	}

	private boolean userIsPostOwner(Post post, Post existingPost) {
		return existingPost.getUser() != post.getUser();
	}

	private boolean isUpdatePost(Post post) {
		return post.getPostId() != 0;
	}

	public void delete(Long postId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getPrincipal().toString();
		User user = userService.findByUsername(username);
		Post post = findPostById(postId);
		
		if(post.getUser() != user) {
			throw new SpringSimpleBlogException("Cannot delete post id:"+postId);
		}
		
		postRepository.delete(post);
		
	}

}
