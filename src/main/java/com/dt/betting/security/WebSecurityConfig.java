package com.dt.betting.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList<>();
        users.add(user("NooobAdmin","hoppacska5767", "ADMIN"));
        users.add(user("NooobUser","hoppacska5767", "USER"));
        users.add(user("Janos","hoppacska5744", "USER"));
        return new InMemoryUserDetailsManager(users);
    }
    
    private UserDetails user(String name, String password, String... roles) {
    	return  User
    			.withDefaultPasswordEncoder().username(name)
    			.password(password).roles(roles).build();
    }
}