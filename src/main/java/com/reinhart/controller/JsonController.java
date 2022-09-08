package com.reinhart.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JsonController {
	
	@GetMapping("/welcome")
	public String testDisplay() {
		return "Welcome Screen";
	}

}
