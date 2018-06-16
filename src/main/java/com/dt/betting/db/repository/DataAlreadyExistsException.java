package com.dt.betting.db.repository;

import com.dt.betting.db.domain.DomainObject;

public class DataAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	private DomainObject<?> object;

	public DataAlreadyExistsException(DomainObject<?> object) {
		super("The data already exist. " + object.getName());
		this.object = object;
	}

	public DomainObject<?> getObject() {
		return object;
	}
}
