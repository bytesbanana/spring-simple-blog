package com.bytebanana.simpleblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableAsync
@EnableSwagger2
public class SimpleblogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleblogApplication.class, args);
	}

}
