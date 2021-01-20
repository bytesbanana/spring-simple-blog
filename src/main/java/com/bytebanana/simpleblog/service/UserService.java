package com.bytebanana.simpleblog.service;

import java.util.Optional;

import com.bytebanana.simpleblog.dto.UserProfileRequest;
import com.bytebanana.simpleblog.dto.UserProfileResponse;
import com.bytebanana.simpleblog.exception.SpringSimpleBlogException;
import com.bytebanana.simpleblog.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bytebanana.simpleblog.entity.User;
import com.bytebanana.simpleblog.exception.UserNotFoundException;
import com.bytebanana.simpleblog.repository.UserRepositry;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@AllArgsConstructor
@Transactional
public class UserService {

    private final UserRepositry userRepositry;
    private final AuthService authService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        Optional<User> userOptional = userRepositry.findByUsername(username);
        User user = userOptional.orElseThrow(() -> new UserNotFoundException("User not found username : " + username));

        return user;
    }

    public void updateProfile(UserProfileRequest request) {
        User currentUser = authService.getCurrentUser();
        User requestUser = findByUsername(request.getUsername());
        if (currentUser.equals(requestUser)) {
            requestUser = userMapper.mapProfileRequestToUser(request);
            requestUser.setUserId(currentUser.getUserId());
            requestUser.setEnable(true);
            userRepositry.save(requestUser);
        } else {
            throw new SpringSimpleBlogException("Access deny.");
        }

    }

    public User findById(Long userId) {
        Optional<User> userOptional = userRepositry.findById(userId);
        User user = userOptional.orElseThrow(() -> {
            return new UserNotFoundException("User not found user id : " + userId);
        });

        return user;
    }

    public User save(User user) {
        return userRepositry.save(user);
    }

    public UserProfileResponse findMyProfile() {
        User user = authService.getCurrentUser();
        UserProfileResponse userProfileResponse = userMapper.mapToProfileResponse(user);

        return userProfileResponse;
    }
}
