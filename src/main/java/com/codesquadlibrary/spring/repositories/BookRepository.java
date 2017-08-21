package com.codesquadlibrary.spring.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.codesquadlibrary.spring.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long>{
	
	Book findByTitle(String title);
	ArrayList<Book> findByTitleContaining(String title);
	Book findByUniqueid(long uniqueid);

}
