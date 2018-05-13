package com.dt.betting.db.domain;

public interface DomainObject<T> {

	Long getId();
	
	boolean equalsId(T data);
}
