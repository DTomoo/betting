package com.dt.betting.db.repository.inmemory;

import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.User;

class MatchFunction {

	public boolean userHasBetOnMatch(Match match, User user) {
		if (match == null || user == null) {
			return false;
		}
		return match.getBets().stream().filter(b -> b.equalsOwner(user)).findFirst().isPresent();
	}
}
