package com.dt.betting.db.repository;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.dt.betting.db.repository.inmemory.UserDataRepository;

@Component
@Profile("!prod")
public class DevDataInitializer implements InitializingBean {

	@Autowired
	private UserDataRepository userDataRepository;

	@Override
	public void afterPropertiesSet() throws Exception {
		userDataRepository.addUser("DT");
		userDataRepository.addUser("Nooob");
		userDataRepository.addUser("Player3");
	}
}
