package com.codesquadlibrary.spring.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long userid;
	
	@Column(nullable = true)
	private boolean isAdmin;
	
	@Column(nullable = false, unique = true, length = 25)
	private String name;
	
	@Column(nullable = false, unique = true, length = 25)
	private String loginid;
	
	@Column(nullable = false, unique = false, length = 25)
	@JsonIgnore
	private String pwd;
	
	@Column(nullable = true, unique = true)
	private String email;
	
	@Column(nullable = false, unique = true, length = 25)
	private String slackId;
	
	@Column(nullable = true, unique = true, length = 25)
	private String profilePath;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Book> bookRentalList;

	@JsonIgnore
	public List<Book> getBookRentalList() {
		return this.bookRentalList;
	}

	public void setBookRentalList(Book book) {

		if (this.bookRentalList == null) {
			this.bookRentalList = new ArrayList<>();
		}

		bookRentalList.add(book);
	}
	
	
	public long getUserId() {
		return this.userid;
	}

	public boolean isAdmin() {
		return this.isAdmin;
	}
	
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getProfilePath() {
		if (this.profilePath == null) {
			return "default";
		}
		return this.profilePath;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSlackId() {
		return slackId;
	}

	public void setSlackId(String slackId) {
		this.slackId = slackId;
	}

	public boolean isLoginSuccess(String id, String password) {
		if (this.loginid.equals(id) && this.pwd.equals(password)) {
			return true;
		}
		return false;
	}

}
