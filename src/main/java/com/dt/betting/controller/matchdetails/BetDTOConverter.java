package com.dt.betting.controller.matchdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.betting.db.domain.Bet;

@Service
public class BetDTOConverter {

	@Autowired
	private BetStatusCalculator betStatusCalculator;
	@Autowired
	private BetPieceConverter betPieceConverter;

	public BetDTO convert(Bet source) {
		if (source == null) {
			return null;
		}
		BetDTO target = new BetDTO();
		target.setMatchId(source.getMatchId());
		target.setOwner(source.getOwner());
		target.setJoker(source.isJoker());
		target.setScore(source.getScore());
		target.setBetPieces(betPieceConverter.convert(source.getBetPiecesByType()));
		target.setBetStatus(betStatusCalculator.getStatusOf(source));
		return target;
	}
}
