package com.bytebanana.simpleblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {
    private String username;
    private Instant createDate;
    private Long commentId;
    private String message;
    private Long postId;
}
