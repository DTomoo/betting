package com.dt.betting.db.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dt.betting.db.domain.bet.BetPieceType;

public class Match extends DomainObject<Match> {

	private Team team1 = new Team();
	private Team team2 = new Team();
	private LocalDateTime dateTime = LocalDateTime.now();
	private MatchStatus status = MatchStatus.NEW;
	private final List<Bet> bets = new ArrayList<>();
	private Bet result;
	private Long groupId;
	private List<BetPieceType> possibleBetPieces = Arrays.asList(BetPieceType.SCORE_HOME, BetPieceType.SCORE_GUEST, BetPieceType.FINAL_RESULT,
			BetPieceType.WINNER, BetPieceType.SCORE_DIFFERENCE);

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

	public Bet getResult() {
		return result;
	}

	public void setResult(Bet result) {
		this.result = result;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public List<Bet> getBets() {
		return bets;
	}

	public MatchStatus getStatus() {
		return status;
	}

	public void setStatus(MatchStatus status) {
		this.status = status;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return team1.getName() + " - " + team2.getName();
	}

	public boolean equalsTeamIds(Long teamId1, Long teamId2) {
		if (team1 == null || team2 == null) {
			return false;
		}
		return (team1.equalsId(teamId1) && team2.equalsId(teamId2)) || (team1.equalsId(teamId2) && team2.equalsId(teamId1));
	}

	public List<BetPieceType> getPossibleBetPieces() {
		return possibleBetPieces;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((team1 == null) ? 0 : team1.hashCode());
		result = prime * result + ((team2 == null) ? 0 : team2.hashCode());
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
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (team1 == null || other.team1 == null || team2 == null || other.team2 == null) {
			return false;
		}
		return equalsTeamIds(other.getTeam1().getId(), other.getTeam2().getId());
	}
}
