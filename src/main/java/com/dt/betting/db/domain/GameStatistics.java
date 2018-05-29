package com.dt.betting.db.domain;

public class GameStatistics {

	private int score1;
	private int score2;

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

	@Override
	public String toString() {
		return score1 + " - " + score2;
	}
}
