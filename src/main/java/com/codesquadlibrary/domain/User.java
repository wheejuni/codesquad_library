package com.codesquadlibrary.domain;

public class User {

	private String name;
	private String email;
	private String slackId;
	private String profilePath;
	
	public String getProfilePath() {
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

}
