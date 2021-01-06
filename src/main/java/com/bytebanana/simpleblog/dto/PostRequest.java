package com.bytebanana.simpleblog.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
	private Long postId;
	private String title;
	private String content;
	private Instant createDate;
	private Instant lastUpdateDate;
	private Long userId;
}
