package com.codesquadlibrary.spring.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codesquadlibrary.spring.domain.Book;

public interface BookRepository extends CrudRepository<Book, Long>{
	
	Book findByTitle(String title);
	Book findByTitleContaining(String title);
	Book findByUniqueid(long uniqueid);

}
