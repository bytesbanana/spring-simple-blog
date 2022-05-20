package com.bytebanana.simpleblog.mapper;

import com.bytebanana.simpleblog.dto.UserProfileRequest;
import com.bytebanana.simpleblog.dto.UserProfileResponse;
import com.bytebanana.simpleblog.service.AuthService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.bytebanana.simpleblog.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public abstract class UserMapper {

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected AuthService authService;


    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    public abstract UserProfileResponse mapToProfileResponse(User user);

    @Mapping(target = "password", expression = "java(checkPassword(request))")
    public abstract User mapProfileRequestToUser(UserProfileRequest request);

    protected String checkPassword(UserProfileRequest request) {
        User currentUser = authService.getCurrentUser();
        if (StringUtils.hasText(request.getPassword())) {
            return passwordEncoder.encode(request.getPassword());
        } else {
            return currentUser.getPassword();
        }
    }

}
