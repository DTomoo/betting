//package com.dt.betting;
//
//import javax.validation.Valid;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.servlet.ViewResolver;
//import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.view.InternalResourceViewResolver;
//
////import com.dt.betting.security.UserDto;
//
//@Controller
//@EnableWebMvc
//public class WebController extends WebMvcConfigurerAdapter {
//
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/index").setViewName("index");
//		registry.addViewController("/results").setViewName("results");
//		registry.addViewController("/login").setViewName("login");
//		registry.addViewController("/form").setViewName("form");
//	}
//
//	@RequestMapping(value = "/", method = RequestMethod.GET)
//	public String getHomePage() {
//		return "index";
//	}
//
////	@RequestMapping(value = "/form", method = RequestMethod.GET)
////	public String showForm(UserDto user) {
////		return "form";
////	}
////
////	@RequestMapping(value = "/form", method = RequestMethod.POST)
////	public String checkPersonInfo(@Valid UserDto person, BindingResult bindingResult) {
////
////		if (bindingResult.hasErrors()) {
////			return "form";
////		}
////		return "redirect:/results";
////	}
//
//	@Bean
//	public ViewResolver getViewResolver() {
//		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//		resolver.setPrefix("templates/");
//		// resolver.setSuffix(".html");
//		return resolver;
//	}
//
//	@Override
//	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//		configurer.enable();
//	}
//
//}