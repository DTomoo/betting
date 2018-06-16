package com.dt.betting.db.domain.bet;

public enum BetPieceType {

	SCORE_HOME(ScoreHomeBetPiece.class, "Hazai gólszám"),
	SCORE_GUEST(ScoreGuestBetPiece.class, "Vendég gólszám"),
	FINAL_RESULT(FinalResultBetPiece.class, "Végeredmény"),
	WINNER(WinnerBetPiece.class, "Győztes"),
	SCORE_DIFFERENCE(ScoreDifferenceBetPiece.class, "Gólkülönbség"),
	SCORE_SUMMARY(ScoreSummaryBetPiece.class, "Össz gólszám");

	private BetPieceType(Class<? extends BetPiece<?>> betPieceClass, String text) {
		this.betPieceClass = betPieceClass;
		this.text = text;
	}

	private Class<? extends BetPiece<?>> betPieceClass;
	private String text;

	public String getText() {
		return text;
	}
}
