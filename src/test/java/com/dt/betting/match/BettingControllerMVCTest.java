//package com.dt.betting.match;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.hamcrest.Matchers;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import com.dt.betting.db.domain.User;
//import com.dt.betting.db.repository.DevDataInitializer;
//import com.dt.betting.db.repository.inmemory.BetDataRepository;
//import com.dt.betting.db.repository.inmemory.IdGenerator;
//import com.dt.betting.db.repository.inmemory.MatchDataRepository;
//import com.dt.betting.db.repository.inmemory.TeamDataRepository;
//import com.dt.betting.db.repository.inmemory.UserDataRepository;
//import com.dt.betting.user.DevUserService;
//import com.dt.betting.user.UserService;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@WebAppConfiguration
//@ContextConfiguration(classes = { MatchListController.class, UserDataRepository.class, MatchDataRepository.class,
//		TeamDataRepository.class, BetDataRepository.class, DevDataInitializer.class, DevUserService.class,
//		IdGenerator.class })
//public class BettingControllerMVCTest {
//
//	private static final String EXPECTED_VIEW_NAME = "betting/bettingPage";
//	private static final String MATCH_ID_EXISTING = "0";
//	private static final String MATCH_ID_NOT_EXISTING = "1";
//
//	@Autowired
//	private WebApplicationContext wac;
//	@Autowired
//	private UserService devUserService;
//	@Autowired
//	private DevDataInitializer devDataInitializer;
//
//	private MockMvc mockMvc;
//
//	@Before
//	public void setUp() throws Exception {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
//		devDataInitializer.afterPropertiesSet();
//	}
//
//	@Test
//	public void testBetting() throws Exception {
//		// given
//
//		// when
//		mockMvc.perform(get("/ds/betting"))
//
//				// then
//				.andExpect(status().isNotFound());
//	}
//
//	@Test
//	public void testMatchWhenMatchIdExists() throws Exception {
//		// given
//
//		// when
//		mockMvc.perform(get("/ds/betting/match").param("matchId", MATCH_ID_EXISTING))
//
//				// then
//				.andExpect(status().isOk())//
//				.andExpect(view().name(EXPECTED_VIEW_NAME))//
//				.andExpect(forwardedUrl(EXPECTED_VIEW_NAME))//
//				.andExpect(model().attributeExists("match", "otherBets", "errorMessages"))//
//				.andExpect(model().attribute("errorMessages", Matchers.empty()))//
//				.andExpect(model().attributeDoesNotExist("userBet"));
//	}
//
//	@Test
//	public void testMatchWhenMatchIdDoesNotExist() throws Exception {
//		// given
//
//		// when
//		mockMvc.perform(get("/ds/betting/match").param("matchId", MATCH_ID_NOT_EXISTING))
//
//				// then
//				.andExpect(status().isOk())//
//				.andExpect(view().name(EXPECTED_VIEW_NAME))//
//				.andExpect(forwardedUrl(EXPECTED_VIEW_NAME))//
//				.andExpect(model().attributeExists("otherBets", "errorMessages"))//
//				.andExpect(model().attribute("errorMessages",
//						Matchers.contains("The requested data does not exist in the repository.")))//
//				.andExpect(model().attributeDoesNotExist("match", "userBet"));
//	}
//
//	@Test
//	public void testAddBetWhenMatchIdExists() throws Exception {
//		// given
//		User loggedUser = devUserService.getLoggedUser(Mockito.mock(HttpServletRequest.class));
//
//		// when
//		mockMvc.perform(get("/ds/betting/addBet")//
//				.param("matchId", MATCH_ID_EXISTING)//
//				.param("score1", "2")//
//				.param("score2", "3"))
//
//				// then
//				.andExpect(status().isOk())//
//				.andExpect(view().name(EXPECTED_VIEW_NAME))//
//				.andExpect(forwardedUrl(EXPECTED_VIEW_NAME))//
//				.andExpect(model().attributeExists("match", "userBet", "otherBets", "errorMessages"))//
//				.andExpect(model().attribute("userBet", Matchers.hasProperty("owner", Matchers.is(loggedUser))))//
//				.andExpect(model().attribute("userBet", Matchers.hasProperty("score1", Matchers.is(2))))//
//				.andExpect(model().attribute("userBet", Matchers.hasProperty("score2", Matchers.is(3))))//
//				.andExpect(model().attribute("errorMessages", Matchers.empty()));//
//	}
//
//	@Test
//	public void testAddBetWhenMatchIdDoesNotExist() throws Exception {
//		// given
//
//		// when
//		mockMvc.perform(get("/ds/betting/addBet")//
//				.param("matchId", MATCH_ID_NOT_EXISTING)//
//				.param("score1", "2")//
//				.param("score2", "3"))
//
//				// then
//				.andExpect(status().isOk())//
//				.andExpect(view().name(EXPECTED_VIEW_NAME))//
//				.andExpect(forwardedUrl(EXPECTED_VIEW_NAME))//
//				.andExpect(model().attributeExists("otherBets", "errorMessages"))//
//				.andExpect(model().attribute("errorMessages",
//						Matchers.contains("The requested data does not exist in the repository.")))//
//				.andExpect(model().attributeDoesNotExist("match", "userBet"));
//	}
//}
