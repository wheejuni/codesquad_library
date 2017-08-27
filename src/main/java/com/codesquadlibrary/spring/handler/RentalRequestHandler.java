package com.codesquadlibrary.spring.handler;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;

import com.codesquadlibrary.spring.domain.Book;
import com.codesquadlibrary.spring.domain.User;
import com.codesquadlibrary.spring.repositories.BookRepository;

public class RentalRequestHandler {
	
	@Autowired
	static BookRepository bookRepo;
	
	public static boolean rentalRequestWithUniquecode(String uniquecode, User user) {
		try {
		Book rentalBook = bookRepo.findByUniquecode(uniquecode);
		rentalBook.setUser(user);
		rentalBook.setPossessed(true);
		rentalBook.setLastRentDate();
		bookRepo.save(rentalBook);
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		
		
		return true;
	}
	
	public static boolean rentalRequestWithUniqueid(long uniqueid, User user) {
		//TODO process rental request by uniqueid(long)
		return true;
	}

}
