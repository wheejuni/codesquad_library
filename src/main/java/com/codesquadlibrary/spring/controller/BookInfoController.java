package com.codesquadlibrary.spring.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;

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

import com.codesquadlibrary.spring.domain.Book;
import com.codesquadlibrary.spring.domain.User;
import com.codesquadlibrary.spring.handler.AdminCheckingFromSession;
import com.codesquadlibrary.spring.handler.ImageRequestHandler;
import com.codesquadlibrary.spring.handler.RandomStringGenerator;
import com.codesquadlibrary.spring.repositories.BookRepository;
import com.codesquadlibrary.spring.repositories.UserRepository;

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

	@GetMapping("/books/admin")
	public ModelAndView getAdminBooksPage(@RequestParam(value = "search", required = false) String keyword,
			HttpSession session) {

		if (AdminCheckingFromSession.isSessionedUserAdmin(session) == false) {
			return new ModelAndView("users/adminfail");
		}
		ModelAndView bookAdminPage = new ModelAndView("books/adminlist");
		if (keyword != null) {
			bookAdminPage.addObject("booklist", bookRepo.findByTitleContaining(keyword));
		}
		bookAdminPage.addObject("booklist", bookRepo.findAll());
		return bookAdminPage;
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
		if (loggedUser == null) {
			return "users/nologinfail";
		}
		User actualUser = userRepo.findOne(loggedUser.getUserid());
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
		if (returnBook.getUser().getUserid() != loggedUser.getUserid()) {
			return "books/returnerror";
		}
		returnBook.setPossessed(false);
		returnBook.setUser(null);
		bookRepo.save(returnBook);
		return "books/returnsuccess";
	}

	@GetMapping("/books/{uniqueId}/modify")
	public String modifyBook(@PathVariable long uniqueId, Model model, HttpSession session) {
		User loggedUser = (User) session.getAttribute("loginuser");
		Book targetBook = bookRepo.findByUniqueid(uniqueId);
		if (targetBook == null) {
			return "books/noinfofail";
		}
		if (loggedUser.isAdmin()) {
			model.addAttribute("bookid", uniqueId);
			return "books/edit";
		}
		return "users/adminfail";
	}

	@GetMapping("/books/photo/{uniqueId}")

	public String returnForm(@PathVariable long uniqueId, Model model, HttpSession session) {
		Book book = bookRepo.findByUniqueid(uniqueId);
		User loggedUser = (User) session.getAttribute("loginuser");
		if (loggedUser != null && loggedUser.isAdmin()) {
			model.addAttribute("bookinfo", book);
			return "books/photoedit";
		}
		String dest = book.getPicturePath();
		return "redirect:https://s3.ap-northeast-2.amazonaws.com/codesquad-library-book/" + dest;
	}

	@GetMapping("/books/new")
	public String getNewBookForm(HttpSession session) {
		User loggedUser = (User) session.getAttribute("loginuser");
		if (loggedUser != null && loggedUser.isAdmin()) {
			return "books/new";
		}
		return "users/adminfail";
	}

	@PostMapping("/books/new")
	public String newBook(Book book) {
		book.setPossessed(false);
		bookRepo.save(book);

		return "redirect:/";
	}

	@PostMapping("/books/{uniqueId}/modify")
	public String editBook(@PathVariable long uniqueId, Book book) {
		Book originalBook = bookRepo.findOne(uniqueId);
		if (originalBook.update(book)) {

			bookRepo.save(originalBook);
			return "redirect:/books/admin";
		}
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
			return "users/loginfail";
		}

		if (loggedUser.isAdmin() == false) {
			return "users/adminfail";
		}

		String filename = RandomStringGenerator.randomStringFactory(10);
		byte[] uploadedBytes = fileUpload.getBytes();
		BufferedImage img = ImageIO.read(new ByteArrayInputStream(uploadedBytes));

		File outfile = new File("/tmp/" + filename + ".jpg");
		ImageIO.write(img, "jpg", outfile);
		book.setPicturePath(filename);
		if (ImageRequestHandler.ImageInputRequest(img, filename)) {
			System.out.println("S3 업로드에 성공함");
		}
		bookRepo.save(book);
		return "redirect:/books";
	}

}
