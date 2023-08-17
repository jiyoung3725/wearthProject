package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String test() {
		return "main/main";
	}

	@GetMapping("/customerCenter")
	public String customerCenter() {
		return "customerCenter/customerCenter";
	}
	
	
}

