package com.dt.betting.db.repository.inmemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.DataAlreadyExistsException;

public class UserDataRepositoryTest {

	private UserDataRepository userDataRepository;

	@Before
	public void setUp() {
		userDataRepository = new UserDataRepository();
		Whitebox.setInternalState(userDataRepository, "idGenerator", Mockito.mock(IdGenerator.class));
	}

	@Test
	public void testAddUser() throws DataAlreadyExistsException {
		// given
		String userName = "testUser";

		// when
		User actualUser = userDataRepository.addUser(userName);

		// then
		Assert.assertNotNull(actualUser);
		Assert.assertEquals(userName, actualUser.getName());
	}
}
