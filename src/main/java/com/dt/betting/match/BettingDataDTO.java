package com.dt.betting.match;

import java.util.ArrayList;
import java.util.List;

import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.User;

public class BettingDataDTO {

	private Match match;
	private Bet userBet;
	private List<Bet> otherBets = new ArrayList<>();
	private List<String> errorMessages = new ArrayList<>();

	private void setBets(List<Bet> bets, User myUser) {
		for (Bet bet : bets) {
			addBet(bet, myUser);
		}
	}

	private void addBet(Bet bet, User myUser) {
		if (bet.isTheBetOf(myUser)) {
			userBet = bet;
		} else {
			otherBets.add(bet);
		}
	}

	public Bet getUserBet() {
		return userBet;
	}

	public List<Bet> getOtherBets() {
		return otherBets;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match, User myUser) {
		this.match = match;

		otherBets.clear();
		userBet = null;
		setBets(match.getBets(), myUser);
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public void addErrorMsg(String msg) {
		errorMessages.add(msg);
	}
}