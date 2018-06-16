package com.dt.betting.db.repository.inmemory;

import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.Round;
import com.dt.betting.db.repository.DataAlreadyExistsException;

@Component
public class RoundDataRepository extends InMemoryDataRepository<Round> {

	public Round addRound(String name, Long championshipId) throws DataAlreadyExistsException {
		return addData(createRound(name, championshipId));
	}

	private Round createRound(String name, Long championshipId) {
		Round round = new Round();
		round.setName(name);
		round.setChampionshipId(championshipId);
		return round;
	}
}
