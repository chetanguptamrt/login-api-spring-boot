package com.login.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", exposedHeaders ="*")
public class HomeController {

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String welcome() {
		String text = "This is private page";
		text += ", this page is not allowed to unathenticated user";
		return text;
	}
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home() {
		String text = "This is home page";
		text += ", this page is not allowed to unathenticated user";
		return text;
	}
	
	@RequestMapping(value = "/getUsers", method = RequestMethod.GET)
	public String getUser() {
		return "{ \"name\":\"Chetan gupta\" }";
	}
	
}
