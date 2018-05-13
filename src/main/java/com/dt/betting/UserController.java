package com.dt.betting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dt.betting.db.repository.inmemory.UserDataRepository;

@Controller
public class UserController {

	private static final String VIEW_NAME = "viewUsers";
	private static final String FRONTEND_VARIABLE_USERS = "users";

	@Autowired
	private UserDataRepository userDataRepository;

	@RequestMapping("/ds/user/add")
	public ModelAndView addUser(@RequestParam("userName") String userName) {
		userDataRepository.addUser(userName);
		return display();
	}

	@RequestMapping("/ds/users")
	public ModelAndView display() {
		ModelAndView mav = new ModelAndView(VIEW_NAME);
		mav.addObject(FRONTEND_VARIABLE_USERS, userDataRepository.listData());
		return mav;
	}
}
