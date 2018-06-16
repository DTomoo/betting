package com.dt.betting.db.repository.inmemory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.DomainObject;
import com.dt.betting.db.repository.DataAlreadyExistsException;
import com.dt.betting.db.repository.DataNotExistsInRepositoryException;
import com.dt.betting.db.repository.DataRepository;

@Component
class InMemoryDataRepository<T extends DomainObject<T>> implements DataRepository<T> {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IdGenerator idGenerator;

	private List<T> innerList = new ArrayList<>();

	@Override
	public T addData(T data) throws DataAlreadyExistsException {
		validateDataDuplication(data);
		data.setId(idGenerator.createId(data.getClass()));

		innerList.add(data);

		LOGGER.info("{} is added.", data);
		return data;
	}

	@Override
	public List<T> listData() {
		LOGGER.info("list: {}", innerList);
		return innerList;
	}

	@Override
	public T getById(Long id) throws DataNotExistsInRepositoryException {
		Optional<T> result = filterOnId(innerList, id).findFirst();
		LOGGER.info("id: {}, data: {}", id, result);
		return result.orElseThrow(DataNotExistsInRepositoryException::new);
	}

	@Override
	public T getByName(String name) throws DataNotExistsInRepositoryException {
		Optional<T> result = filterOnName(innerList, name).findFirst();
		LOGGER.info("name: {}, data: {}", name, result);
		return result.orElseThrow(DataNotExistsInRepositoryException::new);
	}

	@Override
	public void update(T data) {
	}

	private Stream<T> filterOnId(List<T> data, Long id) {
		if (id == null) {
			return Stream.empty();
		}
		return data.stream().filter(t -> t.equalsId(id));
	}

	private Stream<T> filterOnName(List<T> data, String name) {
		if (name == null) {
			return Stream.empty();
		}
		return data.stream().filter(t -> t.equalsName(name));
	}

	public void clean() {
		innerList.clear();
		idGenerator.reset();
	}

	private void validateDataDuplication(T newData) throws DataAlreadyExistsException {
		if (newData != null && listData().contains(newData)) {
			throw new DataAlreadyExistsException(newData);
		}
	}
}
