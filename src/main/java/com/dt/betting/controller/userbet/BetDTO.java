package com.dt.betting.controller.userbet;

import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.Match;

public class BetDTO {

	private int score1;
	private int score2;
	private Long matchId;
	private String matchName;

	BetDTO(Match match, Bet bet) {
		this.matchId = match.getId();
		this.matchName = match.getName();
		this.score1 = bet.getScore1();
		this.score2 = bet.getScore2();
	}

	public int getScore1() {
		return score1;
	}

	void setScore1(int score1) {
		this.score1 = score1;
	}

	public int getScore2() {
		return score2;
	}

	void setScore2(int score2) {
		this.score2 = score2;
	}

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

	public String getShortText() {
		return score1 + " - " + score2;
	}
}