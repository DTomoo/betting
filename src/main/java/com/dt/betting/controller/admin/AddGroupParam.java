package com.dt.betting.controller.admin;

public class AddGroupParam {

	private String groupName;
	private Long championshipId;
	private Long roundId;

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getChampionshipId() {
		return championshipId;
	}

	public void setChampionshipId(Long championshipId) {
		this.championshipId = championshipId;
	}

	public Long getRoundId() {
		return roundId;
	}

	public void setRoundId(Long roundId) {
		this.roundId = roundId;
	}
}
