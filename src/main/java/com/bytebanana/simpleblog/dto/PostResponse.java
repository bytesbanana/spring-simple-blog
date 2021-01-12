package com.bytebanana.simpleblog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private Long postId;
    private String title;
    private String content;
    private Instant createDate;
    private Instant lastUpdateDate;
    private String username;
}
