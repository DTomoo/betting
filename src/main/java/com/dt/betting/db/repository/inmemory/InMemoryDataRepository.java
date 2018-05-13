package com.dt.betting.db.repository.inmemory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.DomainObject;
import com.dt.betting.db.domain.IdGenerator;
import com.dt.betting.db.repository.DataRepository;

@Component
class InMemoryDataRepository<T extends DomainObject<T>> implements DataRepository<T> {

	@Autowired
	protected IdGenerator idGenerator;

	private List<T> innerList = new ArrayList<>();

	@Override
	public T addData(T data) {
		data.setId(idGenerator.createId(data.getClass()));

		innerList.add(data);
		return data;
	}

	@Override
	public List<T> listData() {
		return new ArrayList<>(innerList);
	}
}
