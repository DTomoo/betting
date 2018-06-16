package com.dt.betting.controller.admin;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dt.betting.controller.BettingAppController;
import com.dt.betting.controller.InsufficientRightException;
import com.dt.betting.db.domain.Championship;
import com.dt.betting.db.domain.Group;
import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.Round;
import com.dt.betting.db.domain.Team;
import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.DataAlreadyExistsException;
import com.dt.betting.db.repository.DataNotExistsInRepositoryException;
import com.dt.betting.db.repository.inmemory.ChampionshipDataRepository;
import com.dt.betting.db.repository.inmemory.GroupDataRepository;
import com.dt.betting.db.repository.inmemory.MatchDataRepository;
import com.dt.betting.db.repository.inmemory.RoundDataRepository;
import com.dt.betting.db.repository.inmemory.TeamDataRepository;
import com.dt.betting.user.UserDoesNotExistsException;

@Controller
public class AdminController extends BettingAppController {

	@Autowired
	private ChampionshipDataRepository championshipDataRepository;
	@Autowired
	private RoundDataRepository roundDataRepository;
	@Autowired
	private GroupDataRepository groupDataRepository;
	@Autowired
	private TeamDataRepository teamDataRepository;
	@Autowired
	private MatchDataRepository matchDataRepository;

	@RequestMapping(method = RequestMethod.POST, value = "/ps/admin/toggle")
	public void toggleAdmin(HttpServletRequest request, HttpServletResponse response) {

		try {
			User loggedUser = userService.getLoggedUser(request);
			boolean admin = loggedUser.isAdmin();
			loggedUser.setAdmin(!admin);
		} catch (UserDoesNotExistsException e) {
			errorHandler(new ArrayList<>(), e);
			try {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/ps/admin/addChampionship")
	public List<String> addChampionship(AddChampionshipParam parameter, HttpServletRequest request) {
		return adminMethod(request, () -> {

			championshipDataRepository.addChampionship(parameter.getChampionshipName());
		});
	}

	@RequestMapping(method = RequestMethod.POST, value = "/ps/admin/addRound")
	public List<String> addRound(AddRoundParam parameter, HttpServletRequest request) {
		return adminMethod(request, () -> {

			Championship championship = getAndValidateChampionship(parameter.getChampionshipId());

			Round round = roundDataRepository.addRound(parameter.getRoundName(), parameter.getChampionshipId());

			updateChampionshipWithRound(round, championship);
		});
	}

	@RequestMapping(method = RequestMethod.POST, value = "/ps/admin/addGroup")
	public List<String> addGroup(AddGroupParam parameter, HttpServletRequest request) {
		return adminMethod(request, () -> {

			Championship championship = getAndValidateChampionship(parameter.getChampionshipId());
			Round round = getAndValidateRound(championship, parameter.getRoundId());

			Group group = groupDataRepository.addGroup(parameter.getGroupName(), championship.getId(), parameter.getRoundId());

			updateRoundWithGroup(group, round);
		});
	}

	@RequestMapping(method = RequestMethod.POST, value = "/ps/admin/addTeam")
	public List<String> addTeam(AddTeamParam param, HttpServletRequest request) {
		List<String> errorMessages = new ArrayList<>();
		List<String> adminMethodErrorMessages = adminMethod(request, () -> {

			if (param.getChampionshipId() != null) {
				if (param.getRoundId() != null) {
					if (param.getGroupId() != null) {
						errorMessages.addAll(addTeamToGroup(param, request));
					} else {
						throw new RuntimeException("Hiányzik a csoport azonosító.");
					}
				} else {
					if (param.getGroupId() != null) {
						throw new RuntimeException("Hiányzik a forduló azonosító.");
					} else {
						errorMessages.addAll(addTeamToChampionship(param, request));
					}
				}
			} else {
				errorMessages.addAll(addTeam(param.getTeamName(), request));
			}

		});
		errorMessages.addAll(adminMethodErrorMessages);
		return errorMessages;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/ps/admin/addMatch")
	public List<String> addMatch(AddMatchParam param, HttpServletRequest request) {
		List<String> errorMessages = new ArrayList<>();
		List<String> adminMethodErrorMessages = adminMethod(request, () -> {

			Championship championship = getAndValidateChampionship(param.getChampionshipId());
			Round round = getAndValidateRound(championship, param.getRoundId());
			Group group = getAndValidateGroup(round, param.getGroupId());
			Team team1 = teamDataRepository.getById(param.getTeamId1());
			Team team2 = teamDataRepository.getById(param.getTeamId2());

			Match match = new Match();
			match.setTeam1(team1);
			match.setTeam2(team2);
			match.setDateTime(LocalDateTime.now());
			match.setGroupId(group.getId());

			match = matchDataRepository.addData(match);
			group.getMatches().add(match);
			groupDataRepository.update(group);

		});
		errorMessages.addAll(adminMethodErrorMessages);
		return errorMessages;
	}

	private List<String> addTeam(String teamName, HttpServletRequest request) {
		return adminMethod(request, () -> {

			teamDataRepository.addTeam(teamName);
		});
	}

	private List<String> addTeamToChampionship(AddTeamParam parameter, HttpServletRequest request) {

		return adminMethod(request, () -> {

			Team team = getOrCreateTeam(parameter);

			Championship championship = getAndValidateChampionship(parameter.getChampionshipId());

			updateChampionshipWithTeam(team, championship);
		});
	}

	private List<String> addTeamToGroup(AddTeamParam parameter, HttpServletRequest request) {

		return adminMethod(request, () -> {

			Team team = getOrCreateTeam(parameter);

			Championship championship = getAndValidateChampionship(parameter.getChampionshipId());
			Round round = getAndValidateRound(championship, parameter.getRoundId());
			Group group = getAndValidateGroup(round, parameter.getGroupId());

			updateChampionshipWithTeam(team, championship);
			updateGroup(team, group);
		});
	};

	private Team getOrCreateTeam(AddTeamParam parameter) throws DataAlreadyExistsException {
		Team team;
		try {
			if (parameter.getTeamId() != null) {
				team = teamDataRepository.getById(parameter.getTeamId());
			} else {
				team = teamDataRepository.getByName(parameter.getTeamName());
			}
		} catch (DataNotExistsInRepositoryException ex) {
			team = teamDataRepository.addTeam(parameter.getTeamName());
		}
		return team;
	}

	private void updateChampionshipWithTeam(Team team, Championship championship) {
		if (!championship.getTeams().contains(team)) {
			championship.getTeams().add(team);
		}
		championshipDataRepository.update(championship);
	}

	private void updateChampionshipWithRound(Round round, Championship championship) {
		championship.getRounds().add(round);
		championshipDataRepository.update(championship);
	}

	private void updateRoundWithGroup(Group group, Round round) {
		round.getGroups().add(group);
		roundDataRepository.update(round);
	}

	private void updateGroup(Team team, Group group) {
		group.getTeams().add(team);
		groupDataRepository.update(group);
	}

	private Championship getAndValidateChampionship(Long championshipId) throws DataNotExistsInRepositoryException {
		return validateChampionship(championshipId);
	}

	private Championship validateChampionship(Long championshipId) throws DataNotExistsInRepositoryException {
		Championship championship = championshipDataRepository.getById(championshipId);
		validateDataExistency(championship);
		return championship;
	}

	private Round getAndValidateRound(Championship championship, Long roundId) throws DataNotExistsInRepositoryException {
		return championship.getRounds().stream().filter(r -> r.equalsId(roundId)).findFirst().orElseThrow(DataNotExistsInRepositoryException::new);
	}

	private Group getAndValidateGroup(Round round, Long groupId) throws DataNotExistsInRepositoryException {
		return round.getGroups().stream().filter(g -> g.equalsId(groupId)).findFirst().orElseThrow(DataNotExistsInRepositoryException::new);
	}

	private void validateDataExistency(Object mandatoryData) throws DataNotExistsInRepositoryException {
		if (mandatoryData == null) {
			throw new DataNotExistsInRepositoryException();
		}
	}

	private List<String> adminMethod(HttpServletRequest request, ValidatedFunction validatedFunction) {
		List<String> errorMessages = new ArrayList<>();
		try {
			validateLoggedUser(request);

			validatedFunction.call();

		} catch (InsufficientRightException | UserDoesNotExistsException | DataAlreadyExistsException | DataNotExistsInRepositoryException e) {
			errorHandler(errorMessages, e);
		}
		return errorMessages;
	}

	private void validateLoggedUser(HttpServletRequest request) throws UserDoesNotExistsException, InsufficientRightException {
		if (!isAdmin(request)) {
			throw new InsufficientRightException();
		}
	}

	private static interface ValidatedFunction {
		void call() throws UserDoesNotExistsException, DataAlreadyExistsException, DataNotExistsInRepositoryException;
	}
}
