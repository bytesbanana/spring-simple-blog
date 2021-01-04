package com.bytebanana.simpleblog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEmail {

	private String subject;
	private String body;
	private String recipient;

}
