package com.codesquadlibrary.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping("/")
	public String getIndexPage() {
		return "index/main";
	}

}
