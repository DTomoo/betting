package com.dt.betting.controller.matchlist;

import java.util.ArrayList;
import java.util.List;

import com.dt.betting.controller.BaseDTO;
import com.dt.betting.controller.matchdetails.MatchDataDTO;
import com.dt.betting.db.domain.Team;

public class MatchesDTO extends BaseDTO {

	private List<MatchDataDTO> matches = new ArrayList<>();
	private List<Team> teams = new ArrayList<>();
	private boolean admin;

	public List<MatchDataDTO> getMatches() {
		return matches;
	}

	public void addMatch(MatchDataDTO matchDataDTO) {
		matches.add(matchDataDTO);
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	
	public boolean isAdmin() {
		return admin;
	}
}