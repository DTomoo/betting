package com.dt.betting.controller.matchdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.User;

@Service
public class MatchDTOConverter {

	@Autowired
	private BetDTOConverter betDTOConverter;

	public MatchDataDTO convert(Match source, User loggedUser) {
		MatchDataDTO target = new MatchDataDTO();
		target.setMatchId(source.getId());
		target.setTeam1(source.getTeam1());
		target.setTeam2(source.getTeam2());
		target.setDateTime(source.getDateTime());
		target.setGameStatistics(source.getGameStatistics());
		target.setStatus(source.getStatus());

		for (Bet bet : source.getBets()) {
			BetDTO betDTO = betDTOConverter.convert(bet);

			if (bet.isTheBetOf(loggedUser)) {
				target.setUserBet(betDTO);
			} else {
				target.getOtherBets().add(betDTO);
			}
		}
		return target;
	}
}
