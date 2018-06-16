package com.dt.betting.controller.matchdetails;

import java.util.Map;

import com.dt.betting.db.domain.User;

public class BetDTO {

	private User owner;
	private Long matchId;
	private BetStatus betStatus;
	private Map<String, String> betPieces;
	private boolean joker;
	private long score;

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

	public Map<String, String> getBetPieces() {
		return betPieces;
	}

	public void setBetPieces(Map<String, String> betPieces) {
		this.betPieces = betPieces;
	}

	public void setJoker(boolean joker) {
		this.joker = joker;
	}

	public boolean isJoker() {
		return joker;
	}
	
	public void setScore(long score) {
		this.score = score;
	}
	
	public long getScore() {
		return score;
	}
}
