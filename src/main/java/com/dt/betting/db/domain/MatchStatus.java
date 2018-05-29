package com.dt.betting.db.domain;

public enum MatchStatus {

	NEW(""),
	BET_CLOSED("Fogadás lezárva"),
	ONGOING("Folyamatban"),
	FINISHED("Vége");

	private String text;

	private MatchStatus(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public boolean isClosedBet() {
		return this != NEW;
	}
	
	public boolean isFinished() {
		return this == FINISHED;
	}
}
