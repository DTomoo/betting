package com.dt.betting.db.domain;

import java.util.ArrayList;
import java.util.List;

public class Round extends DomainObject<Round> {

//	private List<Team> teams = new ArrayList<>();
	private List<Group> groups = new ArrayList<>();
	private Long championshipId;
	private boolean finalized;

//	public List<Team> getTeams() {
//		return teams;
//	}
//
//	public void setTeams(List<Team> teams) {
//		this.teams = teams;
//	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public Long getChampionshipId() {
		return championshipId;
	}

	public void setChampionshipId(Long championshipId) {
		this.championshipId = championshipId;
	}

	public boolean isFinalized() {
		return finalized;
	}

	public void setFinalized(boolean finalized) {
		this.finalized = finalized;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((championshipId == null) ? 0 : championshipId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Round other = (Round) obj;
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
		return true;
	}
}
