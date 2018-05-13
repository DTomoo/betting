package com.dt.betting.db.repository.inmemory;

import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.repository.DataAlreadyExistsException;

@Component
public class BetDataRepository extends InMemoryDataRepository<Bet> {

	public Bet addBet(AddBetDTO addBetDTO) throws DataAlreadyExistsException {

		if (userHasBetOnMatch(addBetDTO)) {
			throw new DataAlreadyExistsException();
		}

		return createBet(addBetDTO);
	}

	private boolean userHasBetOnMatch(AddBetDTO addBetDTO) {
		return new MatchFunction().userHasBetOnMatch(addBetDTO.getMatch(), addBetDTO.getUser());
	}

	private Bet createBet(AddBetDTO addBetDTO) {
		Bet bet = new Bet();
		bet.setOwner(addBetDTO.getUser());
		bet.setScore1(addBetDTO.getScore1());
		bet.setScore2(addBetDTO.getScore2());

		addBetDTO.getUser().getBets().add(bet);
		addBetDTO.getMatch().getBets().add(bet);
		addData(bet);
		return bet;
	}
}
