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
import com.dt.betting.controller.admin.AddTeamParam;
import com.dt.betting.controller.admin.AdminController;
import com.dt.betting.db.domain.Championship;
import com.dt.betting.db.repository.DataNotExistsInRepositoryException;
import com.dt.betting.db.repository.inmemory.ChampionshipDataRepository;
import com.dt.betting.user.UserDoesNotExistsException;

@Controller
public class ChampionshipController extends BettingAppController {

	@Autowired
	private ChampionshipDataRepository championshipDataRepository;
	@Autowired
	private ChampionshipListController championshipListController;
	@Autowired
	private RoundController roundController;
	@Autowired
	private AdminController adminController;

	@RequestMapping(value = "/ds/championship", params = "championshipId")
	public ModelAndView getChampionshipDetails(@RequestParam("championshipId") Long championshipId, HttpServletRequest request) {

		return getChampionshipDetails(championshipId, Arrays.asList(), request);
	}

	@RequestMapping("/ds/championship/addTeam")
	public ModelAndView addTeam(AddTeamParam param, HttpServletRequest request) {

		List<String> errorMessages = adminController.addTeam(param, request);

		if (param.getRoundId() != null) {
			return roundController.getRoundDetails(param.getRoundId(), request);
		}
		return getChampionshipDetails(param.getChampionshipId(), errorMessages, request);
	}

	public ModelAndView getChampionshipDetails(Long championshipId, List<String> previousErrorMessages, HttpServletRequest request) {
		if (championshipId == null) {
			return championshipListController.listChampionships(Arrays.asList("A kiválasztott bajnokság nem ismert."), request);
		}

		List<String> errorMessages = previousErrorMessages == null ? new ArrayList<>() : new ArrayList<>(previousErrorMessages);
		Championship championship = null;
		boolean admin = false;
		try {
			championship = championshipDataRepository.getById(championshipId);

			admin = isAdmin(request);

		} catch (DataNotExistsInRepositoryException | UserDoesNotExistsException ex) {
			errorHandler(errorMessages, ex);
			return championshipListController.listChampionships(errorMessages, request);
		}

		ModelAndView mav = new ModelAndView(getViewName(request));
		mav.addObject("errorMessages", errorMessages);
		mav.addObject("championship", championship);
		mav.addObject("admin", admin);
		return mav;
	}

	private String getViewName(HttpServletRequest request) {
		return isOnlyDetailsPage(request) ? "championshipMainPage" : "championshipDetailsPage";
	}

	private boolean isOnlyDetailsPage(HttpServletRequest request) {
		return request.getParameter("onlyDetails") == null;
	}
}
