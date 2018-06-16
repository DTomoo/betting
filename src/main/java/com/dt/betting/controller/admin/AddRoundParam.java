package com.dt.betting.controller.admin;

public class AddRoundParam {

	private String roundName;
	private Long championshipId;

	public String getRoundName() {
		return roundName;
	}

	public void setRoundName(String roundName) {
		this.roundName = roundName;
	}

	public Long getChampionshipId() {
		return championshipId;
	}

	public void setChampionshipId(Long championshipId) {
		this.championshipId = championshipId;
	}
}
