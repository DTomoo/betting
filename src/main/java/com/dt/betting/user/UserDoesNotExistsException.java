package com.dt.betting.user;

public class UserDoesNotExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserDoesNotExistsException() {
		super("The system cannot find the logged in user.");
	}
}
