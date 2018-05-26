package com.dt.betting.db.repository.inmemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.DataAlreadyExistsException;

public class BetDataRepositoryTest {

	private BetDataRepository betDataRepository;

	@Before
	public void setUp() {
		betDataRepository = new BetDataRepository();
		Whitebox.setInternalState(betDataRepository, "idGenerator", Mockito.mock(IdGenerator.class));
	}

	@Test
	public void testAddBet() throws DataAlreadyExistsException {
		// given
		User user = new User();
		Bet bet = new Bet();
		bet.setOwner(user);
		bet.setScore1(1);
		bet.setScore2(2);

		// when
		Bet actualBet = betDataRepository.addBet(bet);

		// then
		Assert.assertNotNull(actualBet);
		Assert.assertEquals(bet.getScore1(), actualBet.getScore1());
		Assert.assertEquals(bet.getScore2(), actualBet.getScore2());
		Assert.assertSame(bet.getOwner(), actualBet.getOwner());
	}
}
