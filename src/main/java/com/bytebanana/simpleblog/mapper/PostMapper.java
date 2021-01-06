package com.bytebanana.simpleblog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.bytebanana.simpleblog.dto.PostRequest;
import com.bytebanana.simpleblog.entity.Post;
import com.bytebanana.simpleblog.service.UserService;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

	@Autowired
	public UserService userService;

	@Mapping(target = "user", expression = "java(userService.findById(postRequest.getUserId()))")
	public abstract Post mapToPost(PostRequest postRequest);

}
