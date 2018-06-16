package com.dt.betting.db.repository.inmemory;

import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.DataAlreadyExistsException;

@Component
public class UserDataRepository extends InMemoryDataRepository<User> {

	public User addUser(String name) throws DataAlreadyExistsException {
		return addData(createUser(name));
	}

	private User createUser(String name) {
		User user = new User();
		user.setName(name);
		return user;
	}
}
