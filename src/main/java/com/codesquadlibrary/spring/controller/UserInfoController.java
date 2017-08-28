package com.codesquadlibrary.spring.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.codesquadlibrary.spring.domain.User;
import com.codesquadlibrary.spring.handler.AdminCheckingFromSession;
import com.codesquadlibrary.spring.repositories.UserRepository;

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
		User actualUser = userRepo.findOne(loggedUser.getUserid());
		
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
		User actualUser = userRepo.findOne(loggedUser.getUserid());
		
		mydetails.addObject("userinfo", actualUser);
		return mydetails;
	}
	
	@GetMapping("/user/admin")
	public ModelAndView getAdminPage(@RequestParam(value = "search", required = false) String keyword, HttpSession session) {
		ModelAndView admin = new ModelAndView("users/admin");
		User loggedUser = (User) session.getAttribute("loginuser");
		if (loggedUser != null && loggedUser.isAdmin()) {
			if (keyword != null) {
				admin.addObject("userlist", userRepo.findByNameContaining(keyword));
			}
			admin.addObject("userlist", userRepo.findAll());
			return admin;
		}
		return new ModelAndView("users/adminfail");
	
	}

	@PostMapping("/user/login")
	public String getLoginResult(String id, String password, HttpSession session) {
		User destUser = userRepo.findByLoginid(id);
		if (destUser == null) {
			return "users/loginfail";
		}
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
		user.setUniquecode();
		userRepo.save(user);
		return "users/joinsuccess";
	}
	
	@GetMapping("/user/{userid}")
	public ModelAndView getUserDetail(@PathVariable long userid, HttpSession session) {
		User loggedUser = (User) session.getAttribute("loginuser");
		User detailUser = userRepo.findOne(userid);
		if (loggedUser != null && loggedUser.isAdmin()) {
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
		if (actualUser.getUserid() == loggedUser.getUserid()) {
			return "users/photoupload";
		}
		
		return "redirect:https://s3.ap-northeast-2.amazonaws.com/codesquad-library-user/" + actualUser.getProfilePath();
	}
	
	@GetMapping("/user/modify/{userid}")
	public String getUserEditForm(@PathVariable long userid, HttpSession session, Model model) {
		User targetUser = userRepo.findOne(userid);
		if (AdminCheckingFromSession.isSessionedUserAdmin(session)) {
			model.addAttribute("userinfo", targetUser);
			return "users/modify";
		}
		return "users/adminfail";
	}
	
	@PostMapping("/user/modify/{userid}")
	public String getUserEditForm(@PathVariable long userid, HttpSession session, User user) {
		User targetUser = userRepo.findOne(userid);
		if (AdminCheckingFromSession.isSessionedUserAdmin(session)) {
			user.setUniquecode();
			targetUser = user;
			userRepo.save(targetUser);
			return "redirect:/user/" + userid;
		}
		return "users/adminfail";
	}
	
	@GetMapping("/user/modify/setadmin/{userid}")
	public String setUserAsAdmin(@PathVariable long userid, HttpSession session) {
		User targetUser = userRepo.findOne(userid);
		if (AdminCheckingFromSession.isSessionedUserAdmin(session)) {
			targetUser.setAdmin(true);
			userRepo.save(targetUser);
			return "redirect:/user/admin";
		}
		return "users/adminfail";
	}

}
