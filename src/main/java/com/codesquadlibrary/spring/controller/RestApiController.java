package com.codesquadlibrary.spring.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codesquadlibrary.spring.repositories.UserRepository;

@RestController
public class RestApiController {
	
	@Autowired
	UserRepository userRepo;
	
	@PostMapping("/api/signin/userinfo")
	public String signinByApi(HttpSession session, Model model, String usercode) {
		return null;
	}
	
	@PostMapping("/api/rental/book")
	public String rentalByApi(HttpSession session, String bookcode) {
		return null;
	}
	


}
