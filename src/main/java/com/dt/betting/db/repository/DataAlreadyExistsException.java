package com.dt.betting.db.repository;

public class DataAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataAlreadyExistsException() {
		super("The data already exist.");
	}
}
