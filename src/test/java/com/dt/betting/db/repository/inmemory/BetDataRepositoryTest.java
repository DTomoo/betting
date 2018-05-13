package com.dt.betting.db.repository.inmemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.Match;
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
		AddBetDTO addBetDTO = createAddBetDTO();

		// when
		Bet actualBet = betDataRepository.addBet(addBetDTO);

		// then
		Assert.assertNotNull(actualBet);
		Assert.assertEquals(addBetDTO.getScore1(), actualBet.getScore1());
		Assert.assertEquals(addBetDTO.getScore2(), actualBet.getScore2());
		Assert.assertSame(addBetDTO.getUser(), actualBet.getOwner());
		Assert.assertTrue(addBetDTO.getUser().getBets().contains(actualBet));
		Assert.assertTrue(addBetDTO.getMatch().getBets().contains(actualBet));
	}

	private AddBetDTO createAddBetDTO() {
		Match match = new Match();
		User user = new User();

		AddBetDTO addBetDTO = new AddBetDTO();
		addBetDTO.setMatch(match);
		addBetDTO.setUser(user);
		addBetDTO.setScore1(1);
		addBetDTO.setScore2(2);
		return addBetDTO;
	}
}
