package com.dt.betting.controller.matchdetails;

import com.dt.betting.db.domain.User;

public class BetDTO {

	private int score1;
	private int score2;
	private User owner;
	private Long matchId;
	private BetStatus betStatus;

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

	public BetStatus getBetStatus() {
		return betStatus;
	}

	public void setBetStatus(BetStatus betStatus) {
		this.betStatus = betStatus;
	}

	public String getShortText() {
		return score1 + " - " + score2;
	}
}
