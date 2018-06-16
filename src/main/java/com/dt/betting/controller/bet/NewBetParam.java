package com.dt.betting.controller.bet;

import java.util.HashMap;
import java.util.Map;

import com.dt.betting.db.domain.bet.BetPieceType;

public class NewBetParam {

	private Long matchId;
	private Map<BetPieceType, String> bet = new HashMap<>();
	private Long resultScore1;
	private Long resultScore2;

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public Long getMatchId() {
		return matchId;
	}

	public void setBet(Map<BetPieceType, String> bet) {
		this.bet = bet;
	}

	public Map<BetPieceType, String> getBet() {
		return bet;
	}

	public Long getResultScore1() {
		return resultScore1;
	}

	public void setResultScore1(Long resultScore1) {
		this.resultScore1 = resultScore1;
	}

	public Long getResultScore2() {
		return resultScore2;
	}

	public void setResultScore2(Long resultScore2) {
		this.resultScore2 = resultScore2;
	}
}
