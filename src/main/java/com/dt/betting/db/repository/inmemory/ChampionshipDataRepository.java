package com.dt.betting.db.repository.inmemory;

import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.Championship;
import com.dt.betting.db.repository.DataAlreadyExistsException;

@Component
public class ChampionshipDataRepository extends InMemoryDataRepository<Championship> {

	public Championship addChampionship(String name) throws DataAlreadyExistsException {
		return addData(createChampionship(name));
	}

	private Championship createChampionship(String name) {
		Championship championship = new Championship();
		championship.setName(name);
		return championship;
	}
}
