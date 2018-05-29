package com.dt.betting.controller.userlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.inmemory.UserDataRepository;
import com.dt.betting.user.UserDoesNotExistsException;
import com.dt.betting.user.UserService;

@Controller
public class UserListController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserListController.class);

	private static final String VIEW_NAME = "userListPage";

	private static final String FRONTEND_VARIABLE_ALLOW_NEW_DATA_TO_ADD = "allowNewDataToAdd";
	private static final String FRONTEND_VARIABLE_USERS = "users";
	private static final String FRONTEND_VARIABLE_ERROR_MESSAGES = "errorMessages";

	@Autowired
	private UserDataRepository userDataRepository;
	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST, path = "/ds/user/add")
	public ModelAndView addUser(@RequestParam("userName") String userName, HttpServletRequest request) {
		return handleMethod(addUser(userName), request);
	}

	@RequestMapping("/ds/users")
	public ModelAndView display(HttpServletRequest request) throws UserDoesNotExistsException {
		return handleMethod(doNothing(), request);
	}

	private ModelAndView handleMethod(UserExtraFunction userFunction, HttpServletRequest request) {
		User loggedUser = null;
		List<User> listData = new ArrayList<>();
		List<String> errorMessages = new ArrayList<>();
		try {
			loggedUser = userService.getLoggedUser(request);

			userFunction.run(loggedUser);

			listData = userDataRepository.listData();
			calculateUserScores(listData);

		} catch (UserDoesNotExistsException ex) {
			errorMessages.add(ex.getMessage());
			LOGGER.error(ex.getMessage());
		}
		return createMAV(loggedUser.isAdmin(), listData, errorMessages);
	}

	private UserExtraFunction addUser(String userName) {
		return loggedUser -> {

			if (!loggedUser.isAdmin()) {
				throw new RuntimeException();
			}
			userDataRepository.addUser(userName);

		};
	}

	private static UserExtraFunction doNothing() {
		return (o) -> {
		};
	}

	private void calculateUserScores(List<User> listData) {
		// TODO: calculate the real values
		Random rnd = new Random(0);
		for (User user : listData) {
			user.setScores(rnd.nextInt(10));
		}
	}

	public ModelAndView createMAV(boolean allowNewDataToAdd, List<User> listData, List<String> errorMessages) {
		ModelAndView mav = new ModelAndView(VIEW_NAME);
		mav.addObject(FRONTEND_VARIABLE_USERS, listData);
		mav.addObject(FRONTEND_VARIABLE_ALLOW_NEW_DATA_TO_ADD, allowNewDataToAdd);
		mav.addObject(FRONTEND_VARIABLE_ERROR_MESSAGES, errorMessages);
		return mav;
	}

	private static interface UserExtraFunction {
		public void run(User loggedUser);
	}
}
