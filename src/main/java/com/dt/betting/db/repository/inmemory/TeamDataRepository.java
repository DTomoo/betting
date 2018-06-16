package com.dt.betting.db.repository.inmemory;

import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.Team;
import com.dt.betting.db.repository.DataAlreadyExistsException;

@Component
public class TeamDataRepository extends InMemoryDataRepository<Team> {

	public Team addTeam(String name) throws DataAlreadyExistsException {
		return addData(createTeam(name));
	}

	private Team createTeam(String name) {
		Team team = new Team();
		team.setName(name);
		return team;
	}
}
