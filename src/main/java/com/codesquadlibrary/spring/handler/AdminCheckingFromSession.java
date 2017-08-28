package com.codesquadlibrary.spring.handler;

import javax.servlet.http.HttpSession;

import com.codesquadlibrary.spring.domain.User;

public class AdminCheckingFromSession {
	
	public static boolean isSessionedUserAdmin(HttpSession session) {
		User sessionedUser = (User)session.getAttribute("loginuser");
		if (sessionedUser == null) {
			return false;
		}
		return sessionedUser.isAdmin();
	}

}
