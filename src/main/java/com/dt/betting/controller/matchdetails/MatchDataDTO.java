package com.dt.betting.controller.matchdetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dt.betting.controller.BaseDTO;
import com.dt.betting.db.domain.GameStatistics;
import com.dt.betting.db.domain.MatchStatus;
import com.dt.betting.db.domain.Team;

public class MatchDataDTO extends BaseDTO {

	private Long matchId;
	private Team team1;
	private Team team2;
	private LocalDateTime dateTime;
	private GameStatistics gameStatistics;
	private MatchStatus status;
	private BetDTO userBet;
	private List<BetDTO> otherBets = new ArrayList<>();

	public Long getMatchId() {
		return matchId;
	}

	public void setMatchId(Long matchId) {
		this.matchId = matchId;
	}

	public List<BetDTO> getOtherBets() {
		return otherBets;
	}

	public Team getTeam1() {
		return team1;
	}

	void setTeam1(Team team1) {
		this.team1 = team1;
	}

	public Team getTeam2() {
		return team2;
	}

	void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public MatchStatus getStatus() {
		return status;
	}

	void setStatus(MatchStatus status) {
		this.status = status;
	}

	public GameStatistics getGameStatistics() {
		return gameStatistics;
	}

	void setGameStatistics(GameStatistics gameStatistics) {
		this.gameStatistics = gameStatistics;
	}

	public BetDTO getUserBet() {
		return userBet;
	}

	void setUserBet(BetDTO userBet) {
		this.userBet = userBet;
	}

}