package com.dt.betting.security;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class UserDTO {
	@NotNull
	@NotEmpty
	private String firstName;

	@NotNull
	@NotEmpty
	private String lastName;

	@NotNull
	@NotEmpty
	private String password;
	private String matchingPassword;

	@NotNull
	@NotEmpty
	private String email;

	String getFirstName() {
		return firstName;
	}

	void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	String getLastName() {
		return lastName;
	}

	void setLastName(String lastName) {
		this.lastName = lastName;
	}

	String getPassword() {
		return password;
	}

	void setPassword(String password) {
		this.password = password;
	}

	String getMatchingPassword() {
		return matchingPassword;
	}

	void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	String getEmail() {
		return email;
	}

	void setEmail(String email) {
		this.email = email;
	}
}