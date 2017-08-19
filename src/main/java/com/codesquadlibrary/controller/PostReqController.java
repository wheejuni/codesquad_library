package com.codesquadlibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.codesquadlibrary.domain.Book;

@Controller
public class PostReqController {
	
	@PostMapping("/api/register")
	
	public String registNewBook(Book book) {
		
		return "register/success";
	}

}
