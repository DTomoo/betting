package com.dt.betting.db.repository;

import java.util.List;

public interface DataRepository<T> {

	public T addData(T data);

	public List<T> listData();
}
