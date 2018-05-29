package com.dt.betting.controller.userbet;

import java.util.List;

public class UserDataDTO {

	private String name;
	private List<BetDTO> bets;

	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}

	public List<BetDTO> getBets() {
		return bets;
	}

	void setBets(List<BetDTO> bets) {
		this.bets = bets;
	}
}