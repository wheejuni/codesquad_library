package com.codesquadlibrary.spring.domain.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.codesquadlibrary.spring.domain.Book;

public class BookTest {
	
	Book newBook;
	
	@Before
	public void setup() {
		newBook = new Book();
		newBook.setUniqueid(13);
	}
	

	@Test
	public void test() {
		
		assertEquals(13, newBook.getUniqueid());
	}

}
