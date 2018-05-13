package com.dt.betting.db.repository.inmemory;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import com.dt.betting.db.domain.DomainObject;
import com.dt.betting.db.domain.IdGenerator;

public class InMemoryDataRepositoryTest {

	private InMemoryDataRepository<TestObject> inMemoryDataRepository;

	@Before
	public void setUp() {
		inMemoryDataRepository = new InMemoryDataRepository<TestObject>();
		Whitebox.setInternalState(inMemoryDataRepository, "idGenerator", Mockito.mock(IdGenerator.class));
	}

	@Test
	public void testAddData() {
		// given
		TestObject testObject = new TestObject();
		List<TestObject> mockedInnerList = Mockito.mock(List.class);
		Whitebox.setInternalState(inMemoryDataRepository, "innerList", mockedInnerList);

		// when
		TestObject actualObject = inMemoryDataRepository.addData(testObject);

		// then
		Assert.assertSame(testObject, actualObject);
		Mockito.verify(mockedInnerList, Mockito.times(1)).add(testObject);
	}

	@Test
	public void testListData() {
		// given
		inMemoryDataRepository.addData(new TestObject());

		List<TestObject> innerList = (List<TestObject>) Whitebox.getInternalState(inMemoryDataRepository, "innerList");

		// when
		List<TestObject> actualObjectList = inMemoryDataRepository.listData();

		// then
		Assert.assertNotSame(innerList, actualObjectList);
		Assert.assertEquals(innerList, actualObjectList);
	}

	private static class TestObject extends DomainObject<TestObject> {
	}
}
