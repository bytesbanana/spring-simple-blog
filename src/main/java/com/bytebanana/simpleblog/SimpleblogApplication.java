package com.bytebanana.simpleblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class SimpleblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleblogApplication.class, args);
	}

}
