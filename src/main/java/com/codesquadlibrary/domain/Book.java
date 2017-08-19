package com.codesquadlibrary.domain;

public class Book {

	private String author;
	private String publisher;
	private boolean isPossessed;
	private String title;

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
