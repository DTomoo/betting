package com.dt.betting.db.domain.bet;

public class WinnerBetPiece extends BetPiece<Long> {

	public WinnerBetPiece(Long teamId) {
		super(teamId);
	}

	@Override
	public BetPieceType getType() {
		return BetPieceType.WINNER;
	}

	@Override
	public long getScoreIfOk() {
		return 20;
	}
}
