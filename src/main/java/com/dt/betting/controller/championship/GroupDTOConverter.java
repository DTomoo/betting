package com.dt.betting.controller.championship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dt.betting.controller.matchdetails.MatchDTOConverter;
import com.dt.betting.db.domain.Group;
import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.User;

@Component
public class GroupDTOConverter {

	@Autowired
	private MatchDTOConverter matchDTOConverter;

	public GroupDTO convert(Group source, User loggedUser) {
		GroupDTO target = new GroupDTO();
		target.setId(source.getId());
		target.setName(source.getName());
		target.setTeams(source.getTeams());

		for (Match match : source.getMatches()) {
			target.getMatches().add(matchDTOConverter.convert(match, loggedUser));
		}
		return target;
	}
}
