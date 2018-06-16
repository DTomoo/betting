package com.dt.betting.db.domain;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import com.dt.betting.db.domain.bet.BetPiece;
import com.dt.betting.db.domain.bet.BetPieceType;
import com.dt.betting.db.domain.bet.FinalResultBetPiece;
import com.dt.betting.db.domain.bet.ScoreDifferenceBetPiece;
import com.dt.betting.db.domain.bet.ScoreGuestBetPiece;
import com.dt.betting.db.domain.bet.ScoreHomeBetPiece;
import com.dt.betting.db.domain.bet.ScoreSummaryBetPiece;

public class Bet extends DomainObject<Bet> {

	private User owner;
	private Long matchId;
	private LocalDateTime timeStamp;
	private Map<BetPieceType, BetPiece<?>> betPiecesByType = new HashMap<>();
	private boolean joker;
	private long score;

	public Bet() {
	}

	public Bet(long score1, long score2) {
		putBetPiece(new ScoreHomeBetPiece(score1));
		putBetPiece(new ScoreGuestBetPiece(score2));
		putBetPiece(new FinalResultBetPiece(score1, score2));
		putBetPiece(new ScoreDifferenceBetPiece(Math.abs(score1 - score2)));
		putBetPiece(new ScoreSummaryBetPiece(score1 + score2));
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
		BetPiece<?> betPiece = getBetPiece(BetPieceType.FINAL_RESULT);
		
		String text = "X";
		if (betPiece instanceof FinalResultBetPiece) {
			text = ((FinalResultBetPiece)betPiece).getValue();
		}
		return text;
	}

	public BetPiece<?> getBetPiece(BetPieceType betPieceType) {
		return betPiecesByType.get(betPieceType);
	}

	public void putBetPiece(BetPiece<?> betPiece) {
		betPiecesByType.put(betPiece.getType(), betPiece);
	}

	public Map<BetPieceType, BetPiece<?>> getBetPiecesByType() {
		return betPiecesByType;
	}

	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public void setJoker(boolean joker) {
		this.joker = joker;
	}

	public boolean isJoker() {
		return joker;
	}

	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
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
		} else if (!owner.equals(other.owner))
			return false;
		return true;
	}
}
