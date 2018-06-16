package com.dt.betting.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

import com.dt.betting.security.UserDTO;

@Controller
public class UserController {

	@RequestMapping(value = "/ds/user/registration")
	public String showRegistrationForm(WebRequest request, Model model) {
		UserDTO userDto = new UserDTO();
		model.addAttribute("user", userDto);
		return "registration";
	}
}
