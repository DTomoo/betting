package com.dt.betting.db.repository.inmemory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.internal.util.reflection.Whitebox;

import com.dt.betting.db.domain.IdGenerator;
import com.dt.betting.db.domain.Team;

public class TeamDataRepositoryTest {

	private TeamDataRepository teamDataRepository;

	@Before
	public void setUp() {
		teamDataRepository = new TeamDataRepository();
		Whitebox.setInternalState(teamDataRepository, "idGenerator", Mockito.mock(IdGenerator.class));
	}

	@Test
	public void testAddTeam() {
		// given
		String teamName = "testTeam";

		// when
		Team actualTeam = teamDataRepository.addTeam(teamName);

		// then
		Assert.assertNotNull(actualTeam);
		Assert.assertEquals(teamName, actualTeam.getName());
	}
}
