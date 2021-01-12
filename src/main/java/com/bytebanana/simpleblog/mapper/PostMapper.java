package com.bytebanana.simpleblog.mapper;

import com.bytebanana.simpleblog.dto.PostRequest;
import com.bytebanana.simpleblog.dto.PostResponse;
import com.bytebanana.simpleblog.entity.Post;
import com.bytebanana.simpleblog.service.UserService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Mapping(target = "username", expression = "java(post.getUser().getUsername())")
    public abstract PostResponse mapToResponse(Post post);


    @Mapping(target = "user", ignore = true)
    @Mapping(target = "postId", ignore = true)
    @Mapping(target = "lastUpdateDate", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    public abstract Post mapCreateDtoToPost(PostRequest postRequest);



}
