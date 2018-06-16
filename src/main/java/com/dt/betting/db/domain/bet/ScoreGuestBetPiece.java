package com.dt.betting.db.domain.bet;

public class ScoreGuestBetPiece extends BetPiece<Long> {

	public ScoreGuestBetPiece(long scoreGuest) {
		super(scoreGuest);
	}

	@Override
	public BetPieceType getType() {
		return BetPieceType.SCORE_GUEST;
	}

	public long getScoreIfOk() {
		return getValue().longValue() == 0 ? 5 : 5 * getValue().longValue();
	}
}
