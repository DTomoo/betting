package com.dt.betting.db.domain.bet;

public class FinalResultBetPiece extends BetPiece<String> {

	public FinalResultBetPiece(long score1, long score2) {
		super(score1 + ":" + score2);
	}

	@Override
	public BetPieceType getType() {
		return BetPieceType.FINAL_RESULT;
	}

	@Override
	public long getScoreIfOk() {
		return 40;
	}
}
