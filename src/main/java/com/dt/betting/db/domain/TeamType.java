package com.dt.betting.db.domain;

public enum TeamType implements EnumWithText {

	CLUB(0, "Klubcsapat"),
	COUNTRY(1, "Válogatott");

	private String text;
	private long code;

	private TeamType(long code, String text) {
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

	public static TeamType getByCode(long code) {
		for (TeamType e : values()) {
			if (e.getCode() == code) {
				return e;
			}
		}
		throw new IllegalArgumentException();
	}
}
