package com.dt.betting.db.repository.inmemory;

import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.Team;

@Component
public class TeamDataRepository extends InMemoryDataRepository<Team> {

	public Team addTeam(String name) {
		return addData(createTeam(name));
	}

	private Team createTeam(String name) {
		Team team = new Team();
		team.setId(idGenerator.createId(Team.class));
		team.setName(name);
		addData(team);
		return team;
	}
}
