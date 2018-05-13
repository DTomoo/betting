package com.dt.betting.db.repository.inmemory;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import com.dt.betting.db.domain.IdGenerator;
import com.dt.betting.db.domain.User;

public class UserDataRepositoryTest {

	private UserDataRepository userDataRepository;

	@Before
	public void setUp() {
		userDataRepository = new UserDataRepository();
		Whitebox.setInternalState(userDataRepository, "idGenerator", Mockito.mock(IdGenerator.class));

	}

	@Test
	public void testAddData() {
		// given
		User user = createTestUser();
		List<User> mockedInnerList = Mockito.mock(List.class);
		Whitebox.setInternalState(userDataRepository, "innerList", mockedInnerList);

		// when
		User actualUser = userDataRepository.addData(user);

		// then
		Assert.assertSame(user, actualUser);
		Mockito.verify(mockedInnerList, Mockito.times(1)).add(user);
	}

	@Test
	public void testListData() {
		// given
		userDataRepository.addData(createTestUser());

		List<User> innerList = (List<User>) Whitebox.getInternalState(userDataRepository, "innerList");

		// when
		List<User> actualUserList = userDataRepository.listData();

		// then
		Assert.assertNotSame(innerList, actualUserList);
		Assert.assertEquals(innerList, actualUserList);
	}

	@Test
	public void testAddUser() {
		// given
		String userName = "testUser";

		// when
		User actualUser = userDataRepository.addUser(userName);

		// then
		Assert.assertNotNull(actualUser);
		Assert.assertEquals(userName, actualUser.getName());
	}

	private User createTestUser() {
		User user = new User();
		user.setId(0L);
		user.setName("testUser");
		return user;
	}
}
