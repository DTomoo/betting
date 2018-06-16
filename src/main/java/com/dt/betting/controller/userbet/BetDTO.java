package com.dt.betting.controller.userbet;

import java.util.Map;

import com.dt.betting.controller.matchdetails.BetStatus;

public class BetDTO {

	private Long matchId;
	private String matchName;
	private BetStatus betStatus;
	private Map<String, String> betPieces;
	private boolean joker;

	public Long getMatchId() {
		return matchId;
	}

	void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public String getMatchName() {
		return matchName;
	}

	void setMatchName(String matchName) {
		this.matchName = matchName;
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
}