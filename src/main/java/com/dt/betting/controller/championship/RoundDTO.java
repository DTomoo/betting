package com.dt.betting.controller.championship;

import java.util.ArrayList;
import java.util.List;

import com.dt.betting.db.domain.Team;

public class RoundDTO {

	private Long id;
	private String name;
	private Long championshipId;
	private List<Team> teamsOfChampionship = new ArrayList<>();
	private List<GroupDTO> groups = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getChampionshipId() {
		return championshipId;
	}

	public void setChampionshipId(Long championshipId) {
		this.championshipId = championshipId;
	}

	public List<Team> getTeamsOfChampionship() {
		return teamsOfChampionship;
	}

	public void setTeamsOfChampionship(List<Team> teamsOfChampionship) {
		this.teamsOfChampionship = teamsOfChampionship;
	}

	public List<GroupDTO> getGroups() {
		return groups;
	}

	public void setGroups(List<GroupDTO> groups) {
		this.groups = groups;
	}

}
