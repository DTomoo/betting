package com.dt.betting.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

	@RequestMapping("/login")
	public ModelAndView showLoginPage(HttpServletRequest reques) {
		ModelAndView mav = new ModelAndView("login");

		mav.addObject("error", reques.getParameter("error") != null);
		mav.addObject("logout", reques.getParameter("logout") != null);

		return mav;
	}
}
