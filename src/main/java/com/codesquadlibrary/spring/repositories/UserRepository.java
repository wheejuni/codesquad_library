package com.codesquadlibrary.spring.repositories;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import com.codesquadlibrary.spring.domain.User;

public interface UserRepository extends CrudRepository <User, Long>{
	
	User findByLoginid(String loginid);
	User findByUniquecode(String uniquecode);
	ArrayList<User> findByNameContaining(String username);

}
