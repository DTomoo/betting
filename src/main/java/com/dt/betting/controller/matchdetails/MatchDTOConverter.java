package com.dt.betting.controller.matchdetails;

import java.util.ArrayList;

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
		target.setId(source.getId() == null ? -1 : source.getId().longValue());
		target.setTeam1(source.getTeam1());
		target.setTeam2(source.getTeam2());
		target.setDateTime(source.getDateTime());
		target.setResult(betDTOConverter.convert(source.getResult()));
		target.setStatus(source.getStatus());
		target.setGroupId(source.getGroupId());
		target.setPossibleBetPieces(new ArrayList<>(source.getPossibleBetPieces()));

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
