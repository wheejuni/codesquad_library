package com.codesquadlibrary.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.codesquadlibrary.domain.User;
import com.codesquadlibrary.repositories.UserRepository;

@Controller
public class UserInfoController {

	@Autowired
	UserRepository userRepo;

	@GetMapping("/user/login")
	public String getLoginForm() {
		return ("users/login");

	}

	@GetMapping("/user/join")
	public String getJoinForm() {
		return "users/join";
	}
	
	@GetMapping("/user/mypage")
	public ModelAndView getMyPage(HttpSession session) {
		ModelAndView mylist = new ModelAndView("users/list");
		User loggedUser = (User) session.getAttribute("loginuser");
		if (loggedUser == null) {
			return new ModelAndView("books/loginfail");
		}
		User actualUser = userRepo.findOne(loggedUser.getUserId());
		
		mylist.addObject("booklist", actualUser.getBookRentalList());
		return mylist;
	}

	@PostMapping("/user/login")
	public String getLoginResult(User user, HttpSession session) {
		User destUser = userRepo.findByLoginid(user.getLoginid());
		if (destUser.isLoginSuccess(user)) {
			session.setAttribute("loginuser", destUser);
			return "redirect:/";
		}

		return "login/fail";
	}

	@PostMapping("/user/join")
	public String getJoinResult(User user) {

		ArrayList<User> users = (ArrayList<User>) userRepo.findAll();

		if (user.getLoginid() == null || user.getPwd() == null || user.getSlackId() == null) {
			return "users/joinfail";
		}

		return "users/joinsuccess";
	}

}
