package com.bytebanana.simpleblog.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.bytebanana.simpleblog.dto.RegisterRequest;
import com.bytebanana.simpleblog.entity.User;
import com.bytebanana.simpleblog.service.UserService;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public abstract class UserMapper {

	@Autowired
	public UserService userService;

//	@Mapping(target = "userId", expression = "java(userService.findById())")
//	public abstract User mapDtoToUser(RegisterRequest registerRequest);

}
