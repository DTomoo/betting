package com.dt.betting.db.domain;

import java.time.LocalDateTime;

public class Bet extends DomainObject<Bet> {

	private int score1;
	private int score2;
	private User owner;
	private Long matchId;
	private LocalDateTime timeStamp;

	public int getScore1() {
		return score1;
	}

	public void setScore1(int score1) {
		this.score1 = score1;
	}

	public int getScore2() {
		return score2;
	}

	public void setScore2(int score2) {
		this.score2 = score2;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public boolean isTheBetOfUser(Long userId) {
		return userId != null && userId.equals(this.owner.getId());
	}

	public boolean isTheBetOf(User owner) {
		return this.owner != null && this.owner.equals(owner);
	}

	public boolean isTheBetOf(Match match) {
		return this.matchId != null && matchId.equals(match.getId());
	}

	public String getShortText() {
		return score1 + " - " + score2;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matchId == null) ? 0 : matchId.hashCode());
		result = prime * result + ((owner == null || owner.getId() == null) ? 0 : owner.getId().hashCode());
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
		Bet other = (Bet) obj;
		if (matchId == null) {
			if (other.matchId != null)
				return false;
		} else if (!matchId.equals(other.matchId))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.getId().equals(other.owner.getId()))
			return false;
		return true;
	}
}
