package com.dt.betting.db.domain.bet;

public class ScoreDifferenceBetPiece extends BetPiece<Long> {

	public ScoreDifferenceBetPiece(long scoreDifference) {
		super(scoreDifference);
	}

	@Override
	public BetPieceType getType() {
		return BetPieceType.SCORE_DIFFERENCE;
	}

	public long getScoreIfOk() {
		return getValue().longValue() == 0 ? 5 : 5 * getValue().longValue();
	}
}
