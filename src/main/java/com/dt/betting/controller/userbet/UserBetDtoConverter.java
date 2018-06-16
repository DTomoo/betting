package com.dt.betting.controller.userbet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dt.betting.controller.matchdetails.BetPieceConverter;
import com.dt.betting.controller.matchdetails.BetStatus;
import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.Match;

@Component
public class UserBetDtoConverter {

	@Autowired
	private BetPieceConverter betPieceConverter;

	public BetDTO convert(Match match, Bet bet, BetStatus betStatus) {
		BetDTO betDTO = new BetDTO();
		betDTO.setMatchId(match.getId());
		betDTO.setMatchName(match.getName());

		betDTO.setBetPieces(betPieceConverter.convert(bet.getBetPiecesByType()));
		betDTO.setBetStatus(betStatus);
		betDTO.setJoker(bet.isJoker());
		return betDTO;
	}
}
