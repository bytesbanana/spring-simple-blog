package com.bytebanana.simpleblog.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long voteId;
	
	private Boolean vote;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId",referencedColumnName = "postId")
	private Post post;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId",referencedColumnName = "userId")
	private User user;
	
	
}
