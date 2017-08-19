package com.codesquadlibrary.repositories;

import org.springframework.data.repository.CrudRepository;

import com.codesquadlibrary.domain.User;

public interface UserRepository extends CrudRepository <User, Long>{
	
	User findByLoginid(String loginid);

}
