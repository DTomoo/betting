package com.dt.betting;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Controller
public class MainController extends WebMvcConfigurerAdapter {

	private static final String VIEW_NAME = "index";

	@RequestMapping("/")
	public String greeting() {
		return VIEW_NAME;
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
}
