package com.dt.betting.match;

import java.util.ArrayList;
import java.util.List;

import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.User;

public class BettingDataDTO {

	private User user;
	private Match match;
	private Bet userBet;
	private List<Bet> otherBets = new ArrayList<>();
	private List<String> errorMessages = new ArrayList<>();

	private void divideBets() {
		if (match != null) {
			for (Bet bet : match.getBets()) {
				if (bet.equalsOwner(user)) {
					userBet = bet;
				} else {
					otherBets.add(bet);
				}
			}
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		divideBets();
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
		divideBets();
	}

	public Bet getUserBet() {
		return userBet;
	}

	public void setUserBet(Bet userBet) {
		this.userBet = userBet;
	}

	public List<Bet> getOtherBets() {
		return otherBets;
	}

	public void addOtherBet(Bet bet) {
		otherBets.add(bet);
	}

	public void addErrorMsg(String msg) {
		errorMessages.add(msg);
	}

	public void setOtherBets(List<Bet> otherBets) {
		this.otherBets = otherBets;
	}

	public List<String> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
}