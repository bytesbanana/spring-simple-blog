package com.bytebanana.simpleblog.controller;

import com.bytebanana.simpleblog.dto.CommentRequest;
import com.bytebanana.simpleblog.dto.CommentResponse;
import com.bytebanana.simpleblog.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> findByCommentId(@PathVariable("commentId") Long commentId, @RequestBody CommentRequest commentRequest) {
        commentService.updateComment(commentId, commentRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<Void> updateComment(@PathVariable("commentId") Long commentId) {
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/commentId")
    public ResponseEntity<Void> deleteComment() {

        return ResponseEntity.noContent().build();
    }

}
