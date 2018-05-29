package com.dt.betting.controller.matchdetails;

public enum BetStatus {

	NONE("?"),
	MODIFIABLE("Módosítható"),
	FINAL("Lezárt");

	private String text;

	private BetStatus(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
