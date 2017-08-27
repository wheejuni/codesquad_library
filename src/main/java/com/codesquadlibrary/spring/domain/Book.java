package com.codesquadlibrary.spring.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.codesquadlibrary.spring.handler.RandomStringGenerator;
import com.codesquadlibrary.spring.handler.TimeStringHandler;

@Entity
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long uniqueid;

	@Column(nullable = false, unique = false, length = 25)
	private String author;

	@Column(nullable = false, unique = false, length = 25)
	private String publisher;

	@Column(nullable = false)
	private boolean isPossessed;

	@Column(nullable = false, unique = false, length = 25)
	private String title;

	@Column(nullable = true)
	private String picturePath;

	@Column(nullable = true)
	private String lastRentDate;

	@Column(nullable = true, unique = true, length = 10)
	private String uniquecode;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_books_user"))
	private User user;

	public void setUniquecode() {
		this.uniquecode = RandomStringGenerator.randomStringFactory(10);

	}
	
	public String getUniquecode() {
		return this.uniquecode;
	}

	public long getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(long uniqueid) {
		this.uniqueid = uniqueid;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLastRentDate() {
		return this.lastRentDate;
	}

	public void setLastRentDate() {
		this.lastRentDate = TimeStringHandler.getTimeStamp();
	}

	public String getPicturePath() {
		if (this.picturePath == null) {
			return "default";
		}
		return this.picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public boolean isPossessed() {
		return isPossessed;
	}

	public void setPossessed(boolean isPossessed) {
		this.isPossessed = isPossessed;
	}

}
