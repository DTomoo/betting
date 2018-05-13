package com.dt.betting.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.inmemory.UserDataRepository;

@Component
@Profile("prod")
public class ProdUserService implements UserService {

	@Autowired
	private UserDataRepository userDataRepository;

	@Override
	public User getLoggedUser(HttpServletRequest request) {
		// TODO: replace it with the proper implementation
		return userDataRepository.listData().get(0);
	}
}
