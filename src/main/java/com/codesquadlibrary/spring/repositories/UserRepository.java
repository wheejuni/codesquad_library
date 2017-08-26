package com.codesquadlibrary.spring.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codesquadlibrary.spring.domain.User;

public interface UserRepository extends CrudRepository <User, Long>{
	
	User findByLoginid(String loginid);

}
