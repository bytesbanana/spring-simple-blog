package com.bytebanana.simpleblog.entity;

import java.time.Instant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	@Id
	@GeneratedValue
	private Long postId;
	
	@NotBlank(message = "Post title is mandatory")
	private String title;

	@NotBlank(message = "Content title is mandatory")
	private String content;
	
	private Instant createDate;
	
	private Instant lastUpdateDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId" ,referencedColumnName = "userId")
	private User user;
	
	
}
