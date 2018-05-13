package com.dt.betting.db.repository.inmemory;

import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.User;

@Component
public class UserDataRepository extends InMemoryDataRepository<User> {

	public User addUser(String name) {
		return addData(createUser(name));
	}

	private User createUser(String name) {
		User user = new User();
		user.setName(name);
		return user;
	}
}
