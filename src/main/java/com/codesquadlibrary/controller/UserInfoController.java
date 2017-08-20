package com.codesquadlibrary.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
			return new ModelAndView("users/nologinfail");
		}
		User actualUser = userRepo.findOne(loggedUser.getUserId());
		
		mylist.addObject("booklist", actualUser.getBookRentalList());
		return mylist;
	}
	
	@GetMapping("/user/myuserinfo")
	public ModelAndView editMyInfo(HttpSession session) {
		ModelAndView mydetails = new ModelAndView("users/detail");
		User loggedUser = (User) session.getAttribute("loginuser");
		if (loggedUser == null) {
			return new ModelAndView("users/nologinfail");
		}
		User actualUser = userRepo.findOne(loggedUser.getUserId());
		
		mydetails.addObject("myinfo", actualUser);
		return mydetails;
	}
	
	@GetMapping("/user/admin")
	public ModelAndView getAdminPage(HttpSession session) {
		ModelAndView admin = new ModelAndView("users/admin");
		User loggedUser = (User) session.getAttribute("loginuser");
		if (loggedUser.isAdmin()) {
			admin.addObject("userlist", userRepo.findAll());
			return admin;
		}
		return new ModelAndView("users/adminfail");
	
	}

	@PostMapping("/user/login")
	public String getLoginResult(String id, String password, HttpSession session) {
		User destUser = userRepo.findByLoginid(id);
		if (destUser.isLoginSuccess(id, password)) {
			session.setAttribute("loginuser", destUser);
			return "redirect:/";
		}

		return "users/loginfail";
	}
	
	@GetMapping("/user/logout")
	public String logout(HttpSession session) {
		session.setAttribute("loginuser", null);
		return "redirect:/";
	}

	@PostMapping("/user/join")
	public String getJoinResult(User user) {

		//ArrayList<User> users = (ArrayList<User>) userRepo.findAll();

		if (user.getLoginid() == null || user.getPwd() == null || user.getSlackId() == null) {
			return "users/joinfail";
		}

		return "users/joinsuccess";
	}
	
	@GetMapping("/user/{userid}")
	public ModelAndView getUserDetail(@PathVariable long userid, HttpSession session) {
		User loggedUser = (User) session.getAttribute("loginuser");
		User detailUser = userRepo.findOne(userid);
		if (loggedUser.isAdmin()) {
			ModelAndView userinfo = new ModelAndView("users/detail");
			userinfo.addObject("userinfo", detailUser);
			return userinfo;
		}
		
		return new ModelAndView("users/adminfail");
	}
	
	@GetMapping("/user/photo/{userid}")
	public String getPhotoEditForm(@PathVariable long userid, HttpSession session) {
		User loggedUser = (User) session.getAttribute("loginuser");
		User actualUser = userRepo.findOne(userid);
		if (actualUser.getUserId() == loggedUser.getUserId()) {
			return "users/photoupload";
		}
		
		return "redirect:https://s3.ap-northeast-2.amazonaws.com/codesquad-library-user/" + actualUser.getProfilePath();
	}

}
