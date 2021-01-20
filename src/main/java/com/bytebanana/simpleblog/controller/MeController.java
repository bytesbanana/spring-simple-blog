package com.bytebanana.simpleblog.controller;

import com.bytebanana.simpleblog.dto.PostResponse;
import com.bytebanana.simpleblog.dto.UserProfileRequest;
import com.bytebanana.simpleblog.dto.UserProfileResponse;
import com.bytebanana.simpleblog.entity.User;
import com.bytebanana.simpleblog.service.PostService;
import com.bytebanana.simpleblog.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Generated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/me")
@AllArgsConstructor
public class MeController {

    private final PostService postService;
    private final UserService userService;


    @PostMapping("/profile")
    public ResponseEntity<UserProfileResponse> getMyUserProfile() {
        return ResponseEntity.ok(userService.findMyProfile());
    }

    @PutMapping("/profile")
    public ResponseEntity<Void> updateMyUserProfile(@RequestBody UserProfileRequest userProfileRequest) {
        userService.updateProfile(userProfileRequest);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/draft")
    public ResponseEntity<List<PostResponse>> fetchMyDraftPost() {
        return ResponseEntity.ok(postService.findMyDraftPost());
    }

    @PostMapping("/published")
    public ResponseEntity<List<PostResponse>> fetchMyPublishedPost() {
        return ResponseEntity.ok(postService.findMyPublishedPost());
    }
}
