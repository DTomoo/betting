package com.dt.betting.db.domain;

import java.util.ArrayList;
import java.util.List;

public class Group extends DomainObject<Group> {

	private Long championshipId;
	private Long roundId;

	private List<Team> teams = new ArrayList<>();
	private List<Match> matches = new ArrayList<>();

	public List<Match> getMatches() {
		return matches;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public Long getChampionshipId() {
		return championshipId;
	}

	public void setChampionshipId(Long championshipId) {
		this.championshipId = championshipId;
	}

	public Long getRoundId() {
		return roundId;
	}

	public void setRoundId(Long roundId) {
		this.roundId = roundId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((championshipId == null) ? 0 : championshipId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((roundId == null) ? 0 : roundId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Group other = (Group) obj;
		if (championshipId == null) {
			if (other.championshipId != null)
				return false;
		} else if (!championshipId.equals(other.championshipId))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (roundId == null) {
			if (other.roundId != null)
				return false;
		} else if (!roundId.equals(other.roundId))
			return false;
		return true;
	}
}
