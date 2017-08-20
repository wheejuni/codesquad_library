package com.codesquadlibrary.spring;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodesquadLibraryApplication {
	
	
	@Autowired
	public static void main(String[] args) {
		SpringApplication.run(CodesquadLibraryApplication.class, args);
	}
}
