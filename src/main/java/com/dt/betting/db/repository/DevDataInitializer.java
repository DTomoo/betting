package com.dt.betting.db.repository;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.Team;
import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.inmemory.AddBetDTO;
import com.dt.betting.db.repository.inmemory.BetDataRepository;
import com.dt.betting.db.repository.inmemory.MatchDataRepository;
import com.dt.betting.db.repository.inmemory.TeamDataRepository;
import com.dt.betting.db.repository.inmemory.UserDataRepository;

@Component
@Profile("!prod")
public class DevDataInitializer implements InitializingBean {

	@Autowired
	private UserDataRepository userDataRepository;
	@Autowired
	private TeamDataRepository teamDataRepository;
	@Autowired
	private MatchDataRepository matchDataRepository;
	@Autowired
	private BetDataRepository betDataRepository;

	@Override
	public void afterPropertiesSet() throws Exception {
		User user1 = createUser("DT");
		User user2 = createUser("Nooob");
		User user3 = createUser("Player3");

		Team team1 = createTeam("Manchester United");
		Team team2 = createTeam("Barcelona");

		Match match1 = createMatch(team1, team2, 2, 3);

		Bet bet1 = createBet(match1, user2, 1, 3);
		Bet bet2 = createBet(match1, user3, 5, 2);
	}

	private User createUser(String userName) {
		return userDataRepository.addUser(userName);
	}

	private Team createTeam(String name) {
		return teamDataRepository.addTeam(name);
	}

	private Match createMatch(Team team1, Team team2, int score1, int score2) {
		Match match = new Match();
		match.setTeam1(team1);
		match.setTeam2(team2);
		match.getGameStatistics().setScore1(score1);
		match.getGameStatistics().setScore2(score2);
		return matchDataRepository.addData(match);
	}

	private Bet createBet(Match match, User user, int score1, int score2) throws DataAlreadyExistsException {
		AddBetDTO addBetDTO = new AddBetDTO();
		addBetDTO.setMatch(match);
		addBetDTO.setUser(user);
		addBetDTO.setScore1(score1);
		addBetDTO.setScore2(score2);
		return betDataRepository.addBet(addBetDTO);
	}
}
