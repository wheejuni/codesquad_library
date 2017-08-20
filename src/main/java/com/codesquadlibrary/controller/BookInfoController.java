package com.codesquadlibrary.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.codesquadlibrary.domain.Book;
import com.codesquadlibrary.domain.User;
import com.codesquadlibrary.handler.ImageRequestHandler;
import com.codesquadlibrary.handler.RandomStringGenerator;
import com.codesquadlibrary.repositories.BookRepository;
import com.codesquadlibrary.repositories.UserRepository;

@Controller
public class BookInfoController {

	@Autowired
	BookRepository bookRepo;

	@Autowired
	UserRepository userRepo;

	@GetMapping("/books")
	public ModelAndView getBooksList(@RequestParam(value = "search", required = false) String keyword) {
		ModelAndView listPage = new ModelAndView("books/list");
		if (keyword == null) {
			listPage.addObject("books", bookRepo.findAll());
			return listPage;
		}

		listPage.addObject("books", bookRepo.findByTitleContaining(keyword));
		return listPage;
	}

	@GetMapping("/books/{uniqueId}")
	public ModelAndView showBookDetail(@PathVariable long uniqueId) {

		Book selectedBook = bookRepo.findOne(uniqueId);
		ModelAndView bookdetail = new ModelAndView("books/detail");
		bookdetail.addObject("book", selectedBook);
		return bookdetail;

	}

	@GetMapping("/books/{uniqueId}/rent")
	public String rentBook(@PathVariable long uniqueId, HttpSession session) {
		User loggedUser = (User) session.getAttribute("loginuser");
		User actualUser = userRepo.findOne(loggedUser.getUserId());
		Book selectedBook = bookRepo.findByUniqueid(uniqueId);
		if (selectedBook.isPossessed()) {
			return "books/renterror";
		}

		selectedBook.setUser(actualUser);
		selectedBook.setPossessed(true);
		selectedBook.setLastRentDate();
		// actualUser.setBookRentalList(selectedBook);

		bookRepo.save(selectedBook);

		return "books/rentsuccess";

	}

	@GetMapping("/books/{uniqueId}/return")
	public String returnBook(@PathVariable long uniqueId, HttpSession session) {
		User loggedUser = (User) session.getAttribute("loginuser");
		Book returnBook = bookRepo.findByUniqueid(uniqueId);
		if (returnBook.getUser().getUserId() != loggedUser.getUserId()) {
			return "books/returnerror";
		}
		returnBook.setPossessed(false);
		bookRepo.save(returnBook);
		return "books/returnsuccess";
	}

	@GetMapping("/books/photo/{uniqueId}")

	public String returnForm(@PathVariable long uniqueId, Model model, HttpSession session) {
		Book book = bookRepo.findByUniqueid(uniqueId);
		User loggedUser = (User) session.getAttribute("loginuser");
		if (loggedUser.isAdmin()) {
			model.addAttribute("bookinfo", book);
			return "books/photoedit";
		}
		String dest = book.getPicturePath();
		return "redirect:https://s3.ap-northeast-2.amazonaws.com/codesquad-library/" + dest;
	}

	@PostMapping("/books/new")
	public String newBook(Book book) {
		book.setPossessed(false);
		bookRepo.save(book);

		return "redirect:/";
	}

	@PostMapping("/books/photo/{uniqueId}")
	public String getUpload(@PathVariable long uniqueId, HttpServletRequest request,
			@RequestParam("pic") MultipartFile fileUpload, HttpSession session) throws Exception {

		Book book = bookRepo.findByUniqueid(uniqueId);
		User loggedUser = (User) session.getAttribute("loginuser");

		if (fileUpload.isEmpty()) {
			return "books/photoerror";
		}

		if (loggedUser == null) {
			return "books/loginfail";
		}

		if (loggedUser.isAdmin() == false) {
			return "books/loginfail";
		}

		String filename = RandomStringGenerator.randomStringFactory(10);
		byte[] uploadedBytes = fileUpload.getBytes();
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(uploadedBytes));

		book.setPicturePath(filename);
		if (ImageRequestHandler.ImageInputRequest(img, filename)) {
			System.out.println("S3 업로드에 성공함");
		}
		bookRepo.save(book);
		return "redirect:/books";
	}

}
