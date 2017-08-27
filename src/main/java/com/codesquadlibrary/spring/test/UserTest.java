package com.codesquadlibrary.spring.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.codesquadlibrary.spring.domain.User;

public class UserTest {

	@Test
	public void getUsercode() {
		User user = setup();
		user.setUniquecode();
		System.out.println(user.getUniquecode());
		assertEquals(10, user.getUniquecode().length());

	}
	
	User setup() {
		return new User();
	}

}
