package com.dt.betting.controller.matchlist;

import java.util.ArrayList;
import java.util.List;

import com.dt.betting.controller.BaseDTO;
import com.dt.betting.controller.matchdetails.MatchDataDTO;

public class MatchesDTO extends BaseDTO {

	private List<MatchDataDTO> matches = new ArrayList<>();

	public List<MatchDataDTO> getMatches() {
		return matches;
	}

	public void addMatch(MatchDataDTO matchDataDTO) {
		matches.add(matchDataDTO);
	}
}