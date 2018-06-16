//package com.dt.betting.db.repository.inmemory;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.dt.betting.db.domain.Bet;
//import com.dt.betting.db.domain.Match;
//import com.dt.betting.db.domain.User;
//
//public class MatchFunctionTest {
//
//	private MatchFunction matchFunction;
//
//	@Before
//	public void setUp() {
//		matchFunction = new MatchFunction();
//	}
//
//	@Test
//	public void testUserHasBetOnMatchWhenMatchIsNull() {
//		// given
//		Match match = null;
//		User user = new User();
//
//		// when
//		boolean userHasBetOnMatch = matchFunction.userHasBetOnMatch(match, user);
//
//		// then
//		Assert.assertFalse(userHasBetOnMatch);
//	}
//
//	@Test
//	public void testUserHasBetOnMatchWhenUserIsNull() {
//		// given
//		Match match = new Match();
//		User user = null;
//
//		// when
//		boolean userHasBetOnMatch = matchFunction.userHasBetOnMatch(match, user);
//
//		// then
//		Assert.assertFalse(userHasBetOnMatch);
//	}
//
//	@Test
//	public void testUserHasBetOnMatchWhenUserHasNoBetOnMatch() {
//		// given
//		Match match = new Match();
//		User user = new User();
//
//		// when
//		boolean userHasBetOnMatch = matchFunction.userHasBetOnMatch(match, user);
//
//		// then
//		Assert.assertFalse(userHasBetOnMatch);
//	}
//
//	@Test
//	public void testUserHasBetOnMatchWhenUserHasBetOnMatch() {
//		// given
//		Match match = new Match();
//		User user = new User();
//
//		Bet bet = new Bet();
//		bet.setOwner(user);
//		match.getBets().add(bet);
//
//		// when
//		boolean userHasBetOnMatch = matchFunction.userHasBetOnMatch(match, user);
//
//		// then
//		Assert.assertTrue(userHasBetOnMatch);
//	}
//}
