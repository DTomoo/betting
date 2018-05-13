package com.dt.betting.db.repository.inmemory;

import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.User;

public class AddBetDTO {

	private Match match;
	private User user;
	private int score1;
	private int score2;

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getScore1() {
		return score1;
	}

	public void setScore1(int score1) {
		this.score1 = score1;
	}

	public int getScore2() {
		return score2;
	}

	public void setScore2(int score2) {
		this.score2 = score2;
	}
}
