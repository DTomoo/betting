package com.dt.betting.db.domain.bet;

public class ScoreHomeBetPiece extends BetPiece<Long> {

	public ScoreHomeBetPiece(long scoreHome) {
		super(scoreHome);
	}

	@Override
	public BetPieceType getType() {
		return BetPieceType.SCORE_HOME;
	}

	public long getScoreIfOk() {
		return getValue().longValue() == 0 ? 5 : 5 * getValue().longValue();
	}
}
