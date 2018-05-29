package com.dt.betting.db.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.DomainObject;
import com.dt.betting.db.repository.DataNotExistsInRepositoryException;
import com.dt.betting.db.repository.DataRepository;

@Component
class InMemoryDataRepository<T extends DomainObject<T>> implements DataRepository<T> {

	@Autowired
	private IdGenerator idGenerator;

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

	@Override
	public T getById(Long id) throws DataNotExistsInRepositoryException {
		Optional<T> result = filterOnId(innerList, id).findFirst();
		return result.orElseThrow(DataNotExistsInRepositoryException::new);
	}
	
	@Override
	public void update(T data) {
	}

	private Stream<T> filterOnId(List<T> data, Long id) {
		if (id == null) {
			return Stream.empty();
		}
		return data.stream().filter(t -> id.equals(t.getId()));
	}

	public void clean() {
		innerList.clear();
		idGenerator.reset();
	}
}
