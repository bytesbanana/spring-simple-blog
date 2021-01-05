package com.bytebanana.simpleblog.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

	@GetMapping
	public String hello() {
		return "Hello";
	}

	@PostMapping
	public String helloPost() {
		return "Post hello";
	}

}
