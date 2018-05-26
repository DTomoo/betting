package com.dt.betting.db.repository.inmemory;

import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.repository.DataAlreadyExistsException;

@Component
public class BetDataRepository extends InMemoryDataRepository<Bet> {

	public Bet addBet(Bet bet) throws DataAlreadyExistsException {
		return addData(bet);
	}
}
