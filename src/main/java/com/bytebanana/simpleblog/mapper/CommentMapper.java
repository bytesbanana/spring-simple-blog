package com.bytebanana.simpleblog.mapper;

import com.bytebanana.simpleblog.dto.CommentRequest;
import com.bytebanana.simpleblog.dto.CommentResponse;
import com.bytebanana.simpleblog.dto.PostRequest;
import com.bytebanana.simpleblog.dto.PostResponse;
import com.bytebanana.simpleblog.entity.Comment;
import com.bytebanana.simpleblog.entity.Post;
import com.bytebanana.simpleblog.entity.User;
import com.bytebanana.simpleblog.exception.PostNotFoundException;
import com.bytebanana.simpleblog.repository.PostRepository;
import com.bytebanana.simpleblog.service.AuthService;
import com.bytebanana.simpleblog.service.CommentService;
import com.bytebanana.simpleblog.service.PostService;
import com.bytebanana.simpleblog.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Autowired
    protected PostRepository postRepository;
    @Autowired
    protected AuthService authService;

    @Mapping(target = "post", expression = "java(findPostById(commentRequest.getPostId()))")
    @Mapping(target = "user", expression = "java(authService.getCurrentUser())")
    public abstract Comment mapRequestToComment(CommentRequest commentRequest);

    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    @Mapping(target = "postId" ,source = "post.postId")
    public abstract CommentResponse mapToRespone(Comment comment);

    protected Post findPostById(Long postId) {
        Optional<Post> postOp = postRepository.findById(postId);
        Post post = postOp.orElseThrow(() -> new PostNotFoundException("Post not found id :" + postId));

        return post;
    }
}
