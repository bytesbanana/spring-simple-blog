package com.bytebanana.simpleblog.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
	private String title;
	private String subTitle;
	private String content;
	private Boolean published;
}
