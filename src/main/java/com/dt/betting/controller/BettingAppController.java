package com.dt.betting.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dt.betting.user.UserDoesNotExistsException;
import com.dt.betting.user.UserService;

public abstract class BettingAppController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	protected UserService userService;

	protected void errorHandler(List<String> errorMessages, Exception ex) {
		errorMessages.add(ex.getMessage());
		LOGGER.error(ex.getMessage());
	}

	protected boolean isAdmin(HttpServletRequest request) throws UserDoesNotExistsException {
		return userService.getLoggedUser(request).isAdmin();
	}
}
