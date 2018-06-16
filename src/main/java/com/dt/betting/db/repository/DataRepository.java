package com.dt.betting.db.repository;

import java.util.List;

public interface DataRepository<T> {

	public T addData(T data) throws DataAlreadyExistsException;

	public List<T> listData();

	public T getById(Long id) throws DataNotExistsInRepositoryException;

	public T getByName(String name) throws DataNotExistsInRepositoryException;

	public void update(T data);
}
