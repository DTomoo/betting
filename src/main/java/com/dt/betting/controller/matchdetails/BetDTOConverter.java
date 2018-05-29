package com.dt.betting.controller.matchdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dt.betting.db.domain.Bet;

@Service
public class BetDTOConverter {

	@Autowired
	private BetStatusCalculator betStatusCalculator;

	public BetDTO convert(Bet source) {
		BetDTO target = new BetDTO();
		target.setMatchId(source.getMatchId());
		target.setOwner(source.getOwner());
		target.setScore1(source.getScore1());
		target.setScore2(source.getScore2());
		target.setBetStatus(betStatusCalculator.getStatusOf(source));
		return target;
	}
}
