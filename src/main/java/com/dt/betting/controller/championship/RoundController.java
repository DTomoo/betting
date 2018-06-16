package com.dt.betting.controller.championship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dt.betting.controller.BettingAppController;
import com.dt.betting.db.domain.Championship;
import com.dt.betting.db.domain.Group;
import com.dt.betting.db.domain.Round;
import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.DataNotExistsInRepositoryException;
import com.dt.betting.db.repository.inmemory.ChampionshipDataRepository;
import com.dt.betting.db.repository.inmemory.RoundDataRepository;
import com.dt.betting.user.UserDoesNotExistsException;

@Controller
public class RoundController extends BettingAppController {

	@Autowired
	private RoundDataRepository roundDataRepository;
	@Autowired
	private ChampionshipDataRepository championshipDataRepository;
	@Autowired
	private ChampionshipListController championshipListController;
	@Autowired
	private GroupDTOConverter groupDTOConverter;

	@RequestMapping(value = "/ds/championship/round", params = "roundId")
	public ModelAndView getRoundDetails(@RequestParam("roundId") Long roundId, HttpServletRequest request) {
		return getRoundDetails(roundId, Arrays.asList(), request);
	}

	public ModelAndView getRoundDetails(@RequestParam("roundId") Long roundId, List<String> previousErrorMessages, HttpServletRequest request) {
		if (roundId == null) {
			return championshipListController.listChampionships(Arrays.asList("A kiválasztott forduló nem ismert."), request);
		}

		List<String> errorMessages = previousErrorMessages == null ? new ArrayList<>() : new ArrayList<>(previousErrorMessages);
		Championship championship = null;
		Round round = null;
		boolean admin = false;
		RoundDTO roundDTO = new RoundDTO();

		try {
			User loggedUser = userService.getLoggedUser(request);

			admin = loggedUser.isAdmin();

			round = roundDataRepository.getById(roundId);

			championship = championshipDataRepository.getById(round.getChampionshipId());

			roundDTO.setId(round.getId());
			roundDTO.setName(round.getName());
			roundDTO.setChampionshipId(round.getChampionshipId());
			for (Group group : round.getGroups()) {
				roundDTO.getGroups().add(groupDTOConverter.convert(group, loggedUser));
			}
			roundDTO.setTeamsOfChampionship(championship.getTeams());

		} catch (DataNotExistsInRepositoryException | UserDoesNotExistsException ex) {
			errorHandler(errorMessages, ex);
			return championshipListController.listChampionships(errorMessages, request);
		}

		ModelAndView mav = new ModelAndView("roundDetailsPage");
		mav.addObject("errorMessages", errorMessages);
		mav.addObject("round", roundDTO);
		mav.addObject("admin", admin);
		return mav;
	}
}
