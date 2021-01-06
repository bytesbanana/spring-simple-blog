package com.bytebanana.simpleblog.entity;

import static  javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Long userId;
	
	@Email
	@Column(unique = true)
	@NotBlank(message = "Email is mandatory")
	private String email;
	
	@Column(unique = true)
	@NotBlank(message = "Username is mandatory")
	private String username;

	@NotBlank(message = "Password is mandatory")
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	@Column(columnDefinition = "boolean default false")
	private Boolean enable;

}
