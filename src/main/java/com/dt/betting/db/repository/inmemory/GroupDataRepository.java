package com.dt.betting.db.repository.inmemory;

import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.Group;
import com.dt.betting.db.repository.DataAlreadyExistsException;

@Component
public class GroupDataRepository extends InMemoryDataRepository<Group> {

	public Group addGroup(String name, Long championshipId, Long roundId) throws DataAlreadyExistsException {
		return addData(createGroup(name, championshipId, roundId));
	}

	private Group createGroup(String name, Long championshipId, Long roundId) {
		Group group = new Group();
		group.setName(name);
		group.setChampionshipId(championshipId);
		group.setRoundId(roundId);
		return group;
	}
}
