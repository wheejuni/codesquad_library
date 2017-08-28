package com.codesquadlibrary.spring.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codesquadlibrary.spring.domain.Book;
import com.codesquadlibrary.spring.domain.User;
import com.codesquadlibrary.spring.handler.QrCodeMaker;
import com.codesquadlibrary.spring.handler.RentalRequestHandler;
import com.codesquadlibrary.spring.repositories.BookRepository;
import com.codesquadlibrary.spring.repositories.UserRepository;
import com.google.zxing.WriterException;

@RestController
public class RestApiController {

	@Autowired
	UserRepository userRepo;

	@Autowired
	BookRepository bookRepo;

	QrCodeMaker qrMaker = new QrCodeMaker();
	
	@ResponseBody
	@GetMapping("/api/signin/generateqr/{userid}")
	public HttpEntity getQrCode(HttpServletResponse response, @PathVariable long userid) throws IOException, WriterException {
		User user = userRepo.findOne(userid);
		String qrOriginalCode = user.getUniquecode();
		//ByteArrayOutputStream baos = new ByteArrayOutputStream();
		InputStream getQrByteArray = new ByteArrayInputStream(qrMaker.makeQr(qrOriginalCode));
		StreamUtils.copy(getQrByteArray, response.getOutputStream());
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		
		return new ResponseEntity(HttpStatus.OK);

	}

	@PostMapping("/api/signin/userinfo")
	public String signinByApi(HttpSession session, Model model, String usercode) {
		User loginuser = userRepo.findByUniquecode(usercode);
		if (loginuser == null) {
			return null; // apimessage/error
		}
		session.setAttribute("loginuser", loginuser);
		model.addAttribute("userinfo", loginuser);
		return "redirect:/";
	}

	@PostMapping("/api/rental/book")
	public String rentalByApi(HttpSession session, String bookcode, String usercode) {
		User user = userRepo.findByUniquecode(usercode);
		Book book = bookRepo.findByUniquecode(bookcode);

		if (user == null || book == null) {
			return "api fail message";

		}
		RentalRequestHandler.rentalRequestWithUniquecode(bookcode, user);
		return "success message";

	}

	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public class InternalServerError extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public InternalServerError(final String message, final Throwable cause) {
			super(message);
		}

	}

}
