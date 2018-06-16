package com.dt.betting.db.domain.bet;

public class ScoreSummaryBetPiece extends BetPiece<Long> {

	public ScoreSummaryBetPiece(long scoreSummary) {
		super(scoreSummary);
	}

	@Override
	public BetPieceType getType() {
		return BetPieceType.SCORE_SUMMARY;
	}

	public long getScoreIfOk() {
		return getValue().longValue() == 0 ? 5 : 5 * getValue().longValue();
	}
}
