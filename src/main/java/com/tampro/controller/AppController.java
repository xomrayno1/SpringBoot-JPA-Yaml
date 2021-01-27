package com.tampro.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {
	@Value("${app.name}")
	private String appName;
	
	@GetMapping("/app-name")
	public String getAppName() {
		return appName;	
	}
}
