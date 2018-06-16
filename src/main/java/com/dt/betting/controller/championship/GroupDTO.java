package com.dt.betting.controller.championship;

import java.util.ArrayList;
import java.util.List;

import com.dt.betting.controller.matchdetails.MatchDataDTO;
import com.dt.betting.db.domain.Team;

public class GroupDTO {

	private Long id;
	private String name;
	private List<Team> teams = new ArrayList<>();
	private List<MatchDataDTO> matches = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public List<MatchDataDTO> getMatches() {
		return matches;
	}

	public void setMatches(List<MatchDataDTO> matches) {
		this.matches = matches;
	}
}
