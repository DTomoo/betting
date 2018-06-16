package com.dt.betting.controller.matchdetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.dt.betting.controller.BaseDTO;
import com.dt.betting.db.domain.MatchStatus;
import com.dt.betting.db.domain.Team;
import com.dt.betting.db.domain.bet.BetPieceType;

public class MatchDataDTO extends BaseDTO {

	private long id;
	private long groupId;
	private Team team1;
	private Team team2;
	private LocalDateTime dateTime;
	private MatchStatus status;
	private BetDTO result;
	private BetDTO userBet;
	private List<BetDTO> otherBets = new ArrayList<>();
	private List<BetPieceType> possibleBetPieces;

	public long getId() {
		return id;
	}

	public void setId(long matchId) {
		this.id = matchId;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
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

	public BetDTO getUserBet() {
		return userBet;
	}

	void setUserBet(BetDTO userBet) {
		this.userBet = userBet;
	}

	public BetDTO getResult() {
		return result;
	}

	public void setResult(BetDTO result) {
		this.result = result;
	}

	public List<BetPieceType> getPossibleBetPieces() {
		return possibleBetPieces;
	}

	public void setPossibleBetPieces(List<BetPieceType> possibleBetPieces) {
		this.possibleBetPieces = possibleBetPieces;
	}

}