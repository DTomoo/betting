package com.dt.betting.controller.matchdetails;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.dt.betting.db.domain.Bet;

@Service
public class BetStatusCalculator {

	private static final Duration TIME_GAP = Duration.ofSeconds(60);

	public BetStatus getStatusOf(Bet bet) {
		BetStatus betStatus = BetStatus.NONE;

		if (bet != null && bet.getTimeStamp() != null) {
			if (isUnderTimeGap(bet, TIME_GAP)) {
				betStatus = BetStatus.MODIFIABLE;
			} else {
				betStatus = BetStatus.FINAL;
			}
		}
		return betStatus;
	}

	private boolean isUnderTimeGap(Bet bet, Duration timeGap) {
		Duration delta = Duration.between(bet.getTimeStamp(), LocalDateTime.now());
		return !delta.isNegative() && delta.compareTo(timeGap) < 0;
	}
}
