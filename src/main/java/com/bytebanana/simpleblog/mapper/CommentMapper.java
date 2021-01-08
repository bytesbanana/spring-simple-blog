package com.bytebanana.simpleblog.mapper;

import com.bytebanana.simpleblog.dto.CommentRequest;
import com.bytebanana.simpleblog.dto.CommentResponse;
import com.bytebanana.simpleblog.dto.PostRequest;
import com.bytebanana.simpleblog.entity.Comment;
import com.bytebanana.simpleblog.entity.Post;
import com.bytebanana.simpleblog.entity.User;
import com.bytebanana.simpleblog.service.AuthService;
import com.bytebanana.simpleblog.service.CommentService;
import com.bytebanana.simpleblog.service.PostService;
import com.bytebanana.simpleblog.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper(componentModel = "spring")
public abstract class CommentMapper {
    @Autowired
    protected PostService postService;
    @Autowired
    protected AuthService authService;

    @Mapping(target = "user" , expression = "java(getRequestUser())")
    @Mapping(target = "post" , expression = "java(postService.findPostById(commentRequest.getPostId()))")
    public abstract Comment mapRequestToComment(CommentRequest commentRequest);

    public abstract CommentResponse mapToRespone(Comment comment);

    public User getRequestUser() {
        User user = authService.getCurrentUser();
        return user;
    }
}
