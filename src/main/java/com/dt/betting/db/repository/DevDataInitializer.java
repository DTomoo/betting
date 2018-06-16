package com.dt.betting.db.repository;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.Championship;
import com.dt.betting.db.domain.Group;
import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.MatchStatus;
import com.dt.betting.db.domain.Round;
import com.dt.betting.db.domain.Team;
import com.dt.betting.db.domain.User;
import com.dt.betting.db.domain.bet.FinalResultBetPiece;
import com.dt.betting.db.domain.bet.ScoreDifferenceBetPiece;
import com.dt.betting.db.domain.bet.ScoreGuestBetPiece;
import com.dt.betting.db.domain.bet.ScoreHomeBetPiece;
import com.dt.betting.db.domain.bet.WinnerBetPiece;
import com.dt.betting.db.repository.inmemory.BetDataRepository;
import com.dt.betting.db.repository.inmemory.ChampionshipDataRepository;
import com.dt.betting.db.repository.inmemory.GroupDataRepository;
import com.dt.betting.db.repository.inmemory.MatchDataRepository;
import com.dt.betting.db.repository.inmemory.RoundDataRepository;
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
	@Autowired
	private ChampionshipDataRepository championshipDataRepository;
	@Autowired
	private RoundDataRepository roundDataRepository;
	@Autowired
	private GroupDataRepository groupDataRepository;

	@Override
	public void afterPropertiesSet() throws Exception {
		userDataRepository.clean();
		teamDataRepository.clean();
		matchDataRepository.clean();
		betDataRepository.clean();

		User karesz = createUser("Karesz");

		Championship championship = championshipDataRepository.addChampionship("Foci EB 2016");

		Round round1 = createRound("Csoportmérkőzések", championship);
		Round round2 = createRound("Egyenes kiesés", championship);

		{
			Group group1 = createGroup("A-csoport", championship, round1);

			Team franciao = createTeam(championship, "Franciaország");
			Team romania = createTeam(championship, "Románia");
			Team albania = createTeam(championship, "Albánia");
			Team svajc = createTeam(championship, "Svájc");
			group1.getTeams().addAll(Arrays.asList(franciao, romania, albania, svajc));

			Match match = addMatchToGroup(group1, franciao, romania); //createMatchWithResult(group1, franciao, romania, 2, 1); // addMatchToGroup(group1, franciao, romania);
			createUserBet(match, karesz, true, 2, 0, 2, 0, franciao.getId(), 2);

			match = createMatchWithResult(group1, albania, svajc, 0, 1);
			createUserBet(match, karesz, false, 0, 0, 0, 0, null, 0);

			match = createMatchWithResult(group1, romania, svajc, 1, 1);
			createUserBet(match, karesz, false, 0, 0, 0, 0, null, 0);

			match = createMatchWithResult(group1, franciao, albania, 2, 0);
			createUserBet(match, karesz, false, 2, 0, 2, 0, franciao.getId(), 2);

			match = createMatchWithResult(group1, svajc, franciao, 0, 0);
			createUserBet(match, karesz, false, 0, 1, 0, 1, franciao.getId(), 1);

			match = createMatchWithResult(group1, romania, albania, 0, 1);
			createUserBet(match, karesz, false, 2, 0, 2, 0, romania.getId(), 2);
		}

		{
			Group group2 = createGroup("B-csoport", championship, round1);

			Team wales = createTeam(championship, "Wales");
			Team szlovakia = createTeam(championship, "Szlovákia");
			Team anglia = createTeam(championship, "Anglia");
			Team oroszo = createTeam(championship, "Oroszország");
			group2.getTeams().addAll(Arrays.asList(wales, szlovakia, anglia, oroszo));

			Match match = createMatchWithResult(group2, wales, szlovakia, 2, 1);
			createUserBet(match, karesz, true, 0, 0, 0, 0, null, 0);

			match = createMatchWithResult(group2, anglia, oroszo, 1, 1);
			createUserBet(match, karesz, false, 1, 0, 1, 0, anglia.getId(), 1);

			match = createMatchWithResult(group2, oroszo, szlovakia, 1, 2);
			createUserBet(match, karesz, false, 0, 1, 0, 1, szlovakia.getId(), 1);

			match = createMatchWithResult(group2, anglia, wales, 2, 1);
			createUserBet(match, karesz, false, 1, 0, 1, 0, anglia.getId(), 1);

			match = createMatchWithResult(group2, szlovakia, anglia, 0, 0);
			createUserBet(match, karesz, false, 1, 1, 1, 1, null, 0);

			match = createMatchWithResult(group2, oroszo, wales, 0, 3);
			createUserBet(match, karesz, false, 1, 0, 1, 0, oroszo.getId(), 1);
		}

		{
			Group group3 = createGroup("C-csoport", championship, round1);

			Team lengyelo = createTeam(championship, "Lengyelország");
			Team eszakiro = createTeam(championship, "Észak-Írország");
			Team nemeto = createTeam(championship, "Németország");
			Team ukrajna = createTeam(championship, "Ukrajna");
			group3.getTeams().addAll(Arrays.asList(lengyelo, eszakiro, nemeto, ukrajna));

			Match match = createMatchWithResult(group3, lengyelo, eszakiro, 1, 0);
			createUserBet(match, karesz, false, 1, 1, 1, 1, null, 0);

			match = createMatchWithResult(group3, nemeto, ukrajna, 2, 0);
			createUserBet(match, karesz, false, 0, 0, 0, 0, null, 0);

			match = createMatchWithResult(group3, ukrajna, eszakiro, 0, 2);
			createUserBet(match, karesz, true, 2, 0, 2, 0, ukrajna.getId(), 2);

			match = createMatchWithResult(group3, nemeto, lengyelo, 0, 0);
			createUserBet(match, karesz, false, 0, 0, 0, 0, null, 0);

			match = createMatchWithResult(group3, ukrajna, lengyelo, 0, 1);
			createUserBet(match, karesz, false, 0, 2, 0, 2, lengyelo.getId(), 2);

			match = createMatchWithResult(group3, eszakiro, nemeto, 0, 1);
			createUserBet(match, karesz, false, 0, 1, 0, 1, nemeto.getId(), 1);
		}

		{
			Group group4 = createGroup("D-csoport", championship, round1);
			Team torokO = createTeam(championship, "Törökország");
			Team horvatO = createTeam(championship, "Horvátország");
			Team spanyolO = createTeam(championship, "Spanyolország");
			Team csehO = createTeam(championship, "Csehország");

			group4.getTeams().addAll(Arrays.asList(torokO, horvatO, spanyolO, csehO));

			Match match = createMatchWithResult(group4, torokO, horvatO, 0, 1);
			createUserBet(match, karesz, false, 1, 0, 1, 0, torokO.getId(), 1);

			match = createMatchWithResult(group4, spanyolO, csehO, 1, 0);
			createUserBet(match, karesz, false, 2, 0, 2, 0, spanyolO.getId(), 2);

			match = createMatchWithResult(group4, csehO, horvatO, 2, 2);
			createUserBet(match, karesz, true, 1, 1, 1, 1, null, 0);

			match = createMatchWithResult(group4, spanyolO, torokO, 3, 0);
			createUserBet(match, karesz, false, 2, 1, 2, 1, spanyolO.getId(), 1);

			match = createMatchWithResult(group4, horvatO, spanyolO, 2, 1);
			createUserBet(match, karesz, false, 0, 1, 0, 1, spanyolO.getId(), 1);

			match = createMatchWithResult(group4, csehO, torokO, 0, 2);
			createUserBet(match, karesz, false, 1, 1, 1, 1, null, 0);
		}

		{
			Group group5 = createGroup("E-csoport", championship, round1);

			Team irorszag = createTeam(championship, "Írország");
			Team svedO = createTeam(championship, "Svédország");
			Team belgium = createTeam(championship, "Belgium");
			Team olaszO = createTeam(championship, "Olaszország");

			group5.getTeams().addAll(Arrays.asList(irorszag, svedO, belgium, olaszO));

			Match match = createMatchWithResult(group5, irorszag, svedO, 1, 1);
			createUserBet(match, karesz, false, 0, 1, 0, 1, svedO.getId(), 1);

			match = createMatchWithResult(group5, belgium, olaszO, 0, 2);
			createUserBet(match, karesz, false, 1, 0, 1, 0, belgium.getId(), 1);

			match = createMatchWithResult(group5, olaszO, svedO, 1, 0);
			createUserBet(match, karesz, false, 2, 0, 2, 0, olaszO.getId(), 2);

			match = createMatchWithResult(group5, belgium, irorszag, 3, 0);
			createUserBet(match, karesz, false, 0, 1, 0, 1, irorszag.getId(), 1);

			match = createMatchWithResult(group5, olaszO, irorszag, 0, 1);
			createUserBet(match, karesz, false, 2, 0, 2, 0, olaszO.getId(), 2);

			match = createMatchWithResult(group5, svedO, belgium, 0, 1);
			createUserBet(match, karesz, false, 1, 0, 1, 1, svedO.getId(), 1);
		}

		{
			Group group6 = createGroup("F-csoport", championship, round1);

			Team ausztria = createTeam(championship, "Ausztria");
			Team magyarO = createTeam(championship, "Magyarország");
			Team portugalia = createTeam(championship, "Portugália");
			Team izland = createTeam(championship, "Izland");

			group6.getTeams().addAll(Arrays.asList(ausztria, magyarO, portugalia, izland));

			Match match = createMatchWithResult(group6, ausztria, magyarO, 0, 2);
			createUserBet(match, karesz, false, 1, 0, 1, 0, ausztria.getId(), 1);

			match = createMatchWithResult(group6, portugalia, izland, 1, 1);
			createUserBet(match, karesz, false, 2, 0, 2, 0, portugalia.getId(), 2);

			match = createMatchWithResult(group6, izland, magyarO, 1, 1);
			createUserBet(match, karesz, false, 0, 0, 0, 0, null, 0);

			match = createMatchWithResult(group6, portugalia, ausztria, 0, 0);
			createUserBet(match, karesz, false, 1, 0, 1, 0, portugalia.getId(), 1);

			match = createMatchWithResult(group6, magyarO, portugalia, 3, 3);
			createUserBet(match, karesz, false, 0, 2, 0, 2, portugalia.getId(), 2);

			match = createMatchWithResult(group6, izland, ausztria, 2, 1);
			createUserBet(match, karesz, false, 0, 1, 0, 1, ausztria.getId(), 1);
		}

		User user2 = createUser("Player2");
		User user3 = createUser("Player3");

		// Match match1 = createMatch(team1, team2, 2, 3);
		// match1.setEnded(true);
		//
		// Bet bet1 = createBet(match1, user2, 1, 3);
		// Bet bet2 = createBet(match1, user3, 5, 2);
	}

	private Bet createUserBet(Match match, User user, boolean joker, long scoreHome, long scoreGuest, long final1, long final2, Long winnerId, long diff)
			throws DataAlreadyExistsException {
		Bet bet = new Bet();
		bet.setOwner(user);
		bet.setTimeStamp(LocalDateTime.now());
		bet.setMatchId(match.getId());
		bet.setJoker(joker);
		bet.putBetPiece(new ScoreHomeBetPiece(scoreHome));
		bet.putBetPiece(new ScoreGuestBetPiece(scoreGuest));
		bet.putBetPiece(new FinalResultBetPiece(final1, final2));
		bet.putBetPiece(new WinnerBetPiece(winnerId));
		bet.putBetPiece(new ScoreDifferenceBetPiece(diff));

		bet = betDataRepository.addBet(bet);

		match.getBets().add(bet);
		user.getBetIds().add(bet.getId());
		return bet;
	}

	private Match createMatchWithResult(Group group1, Team franciao, Team romania, long score1, long score2) throws DataAlreadyExistsException {
		Match m1 = addMatchToGroup(group1, franciao, romania);
		Bet r1 = addResult(m1, score1, score2);
		r1.setMatchId(m1.getId());
		return m1;
	}

	private Bet addResult(Match match, long score1, long score2) throws DataAlreadyExistsException {
		Bet result = new Bet(score1, score2);
		Long teamId = null;
		if (score1 > score2) {
			teamId = match.getTeam1().getId();
		} else if (score1 < score2) {
			teamId = match.getTeam2().getId();
		}

		result.setMatchId(match.getId());
		result.putBetPiece(new WinnerBetPiece(teamId));
		match.setResult(result);
//		match.setStatus(MatchStatus.FINISHED);
		 match.setStatus(MatchStatus.values()[(int) (Math.random() * 4)]);

		result = betDataRepository.addBet(result);
		return result;
	}

	private Match addMatchToGroup(Group group, Team team1, Team team2) throws DataAlreadyExistsException {
		Match match = createMatch(group, team1, team2);
		group.getMatches().add(match);
		return match;
	}

	private Match createMatch(Group group, Team team1, Team team2) throws DataAlreadyExistsException {
		Match match = new Match();
		match.setGroupId(group.getId());
		match.setTeam1(team1);
		match.setTeam2(team2);
		matchDataRepository.addData(match);
		return match;
	}

	private Group createGroup(String name, Championship championship, Round round) throws DataAlreadyExistsException {
		Group group = groupDataRepository.addGroup(name, championship.getId(), round.getId());
		round.getGroups().add(group);
		return group;
	}

	private Round createRound(String name, Championship championship) throws DataAlreadyExistsException {
		Round round = roundDataRepository.addRound(name, championship.getId());
		championship.getRounds().add(round);
		return round;
	}

	private User createUser(String userName) throws DataAlreadyExistsException {
		return userDataRepository.addUser(userName);
	}

	private Team createTeam(Championship championship, String name) throws DataAlreadyExistsException {
		Team team = teamDataRepository.addTeam(name);
		championship.getTeams().add(team);
		return team;
	}
	//
	// private Match createMatch(Team team1, Team team2, int score1, int score2)
	// throws DataAlreadyExistsException {
	// Match match = new Match();
	// match.setTeam1(team1);
	// match.setTeam2(team2);
	// match.getGameStatistics().setScore1(score1);
	// match.getGameStatistics().setScore2(score2);
	// return matchDataRepository.addData(match);
	// }
	//
	// private Bet createBet(Match match, User user, int score1, int score2) throws
	// DataAlreadyExistsException {
	// Bet bet = new Bet();
	// match.getBets().add(bet);
	// bet.setMatchId(match.getId());
	// bet.setOwner(user);
	// bet.setScore1(score1);
	// bet.setScore2(score2);
	// return betDataRepository.addBet(bet);
	// }
}
