package com.bytebanana.simpleblog.controller;

import com.bytebanana.simpleblog.dto.PostResponse;
import com.bytebanana.simpleblog.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/me")
@AllArgsConstructor
public class MeController {

    private final PostService postService;

    @PostMapping("/draft")
    public ResponseEntity<List<PostResponse>> fetchMyDraftPost(){
        return ResponseEntity.ok(postService.findMyDraftPost());
    }

    @PostMapping("/published")
    public ResponseEntity<List<PostResponse>> fetchMyPublishedPost(){
        return ResponseEntity.ok(postService.findMyPublishedPost());
    }
}
