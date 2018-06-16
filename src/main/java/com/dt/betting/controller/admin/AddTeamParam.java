package com.dt.betting.controller.admin;

public class AddTeamParam {

	private Long teamId;
	private String teamName;
	private Long championshipId;
	private Long roundId;
	private Long groupId;

	public Long getTeamId() {
		return teamId;
	}

	public void setTeamId(Long teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
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

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
}
