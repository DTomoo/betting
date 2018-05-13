package com.dt.betting.db.repository;

public class DataNotExistsInRepositoryException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataNotExistsInRepositoryException() {
		super("The requested data does not exist in the repository.");
	}
}
