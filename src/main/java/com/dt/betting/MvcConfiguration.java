package com.dt.betting;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@ComponentScan("com.dt.betting")
public class MvcConfiguration extends WebMvcConfigurerAdapter {

	@Value("${spring.view.prefix}")
	private String prefix;

	@Value("${spring.view.suffix}")
	private String suffix;

	@Value("${spring.view.view-names}")
	private String viewNames;

	@Bean
	InternalResourceViewResolver jspViewResolver() {
		final InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/view/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setViewNames("*");
		return viewResolver;
	}
}