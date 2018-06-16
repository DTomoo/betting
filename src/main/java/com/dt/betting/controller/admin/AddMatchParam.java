package com.dt.betting.controller.admin;

public class AddMatchParam {

	private Long teamId1;
	private Long teamId2;
	private Long championshipId;
	private Long roundId;
	private Long groupId;

	public Long getTeamId1() {
		return teamId1;
	}

	public void setTeamId1(Long teamId1) {
		this.teamId1 = teamId1;
	}

	public Long getTeamId2() {
		return teamId2;
	}

	public void setTeamId2(Long teamId2) {
		this.teamId2 = teamId2;
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
