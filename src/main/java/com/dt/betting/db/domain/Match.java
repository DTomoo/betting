package com.dt.betting.db.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Match extends DomainObject<Match> {

	private Team team1 = new Team();
	private Team team2 = new Team();
	private LocalDateTime localDateTime = LocalDateTime.now();
	private boolean ended;
	private GameStatistics gameStatistics = new GameStatistics();
	private final List<Bet> bets = new ArrayList<>();

	public Team getTeam1() {
		return team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public boolean isEnded() {
		return ended;
	}

	public void setEnded(boolean ended) {
		this.ended = ended;
	}

	public GameStatistics getGameStatistics() {
		return gameStatistics;
	}

	public List<Bet> getBets() {
		return bets;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((localDateTime == null) ? 0 : localDateTime.hashCode());
		result = prime * result + ((team1.getId() == null) ? 0 : team1.getId().hashCode());
		result = prime * result + ((team2.getId() == null) ? 0 : team2.getId().hashCode());
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
		Match other = (Match) obj;
		if (localDateTime == null) {
			if (other.localDateTime != null)
				return false;
		} else if (!localDateTime.equals(other.localDateTime))
			return false;
		if (team1.getId() == null) {
			if (other.team1.getId() != null)
				return false;
		} else if (!team1.getId().equals(other.team1.getId()))
			return false;
		if (team2.getId() == null) {
			if (other.team2.getId() != null)
				return false;
		} else if (!team2.getId().equals(other.team2.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
