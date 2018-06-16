package com.dt.betting.score;

import java.util.List;

import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.bet.BetPiece;
import com.dt.betting.db.domain.bet.BetPieceType;

@Component
public class ScoreCalculator {

	public long calculateScore(List<BetPieceType> possibleBetPieces, Bet matchResult, Bet userBet) {
		long score = 0;
		for (BetPieceType betPieceType : possibleBetPieces) {

			BetPiece<?> betPiece = userBet.getBetPiece(betPieceType);
			BetPiece<?> resultPiece = matchResult.getBetPiece(betPieceType);

			if (resultPiece.equalsValue(betPiece)) {
				score += resultPiece.getScoreIfOk();
			}

		}
		if (userBet.isJoker()) {
			score *= 2;
		}
		return score;
	}

}
