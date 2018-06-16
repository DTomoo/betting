package com.dt.betting.user;

import javax.servlet.http.HttpServletRequest;

import com.dt.betting.db.domain.User;

public interface UserService {

	public User getLoggedUser(HttpServletRequest request) throws UserDoesNotExistsException;
	
	public boolean isAdmin();
}
