package com.dt.betting.db.domain;

public enum MatchStatus implements EnumWithText {

	NEW(0, ""),
	BET_CLOSED(1, "Fogadás lezárva"),
	ONGOING(2, "Folyamatban"),
	FINISHED(3, "Vége");

	private String text;
	private long code;

	private MatchStatus(long code, String text) {
		this.code = code;
		this.text = text;
	}

	@Override
	public long getCode() {
		return code;
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

	public static MatchStatus getByCode(long code) {
		for (MatchStatus e : values()) {
			if (e.getCode() == code) {
				return e;
			}
		}
		throw new IllegalArgumentException();
	}
}
