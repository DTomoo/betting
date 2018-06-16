package com.dt.betting.controller.matchlist;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dt.betting.controller.BettingAppController;
import com.dt.betting.controller.admin.AddMatchParam;
import com.dt.betting.controller.admin.AdminController;
import com.dt.betting.controller.championship.RoundController;
import com.dt.betting.controller.matchdetails.MatchDTOConverter;
import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.inmemory.MatchDataRepository;
import com.dt.betting.db.repository.inmemory.TeamDataRepository;
import com.dt.betting.user.UserDoesNotExistsException;

@Controller
public class MatchListController extends BettingAppController {

	private static final String VIEW_NAME = "matchListPage";

	public static final String FRONTEND_VARIABLE_MATCH_LIST = "dataset";

	@Autowired
	private MatchDataRepository matchRepository;
	@Autowired
	private TeamDataRepository teamRepository;
	@Autowired
	private MatchDTOConverter matchDTOConverter;
	@Autowired
	private RoundController roundController;
	@Autowired
	private AdminController adminController;

	@RequestMapping("/ds/matchList")
	public ModelAndView displayBettingPage(HttpServletRequest request) {
		List<String> errorMessages = new ArrayList<>();

		MatchesDTO matchesDTO = new MatchesDTO();

		try {
			User loggedUser = userService.getLoggedUser(request);

			matchRepository.listData().stream().map(m -> matchDTOConverter.convert(m, loggedUser)).forEach(matchesDTO::addMatch);

			matchesDTO.setTeams(teamRepository.listData());
			matchesDTO.setAdmin(loggedUser.isAdmin());

		} catch (UserDoesNotExistsException ex) {
			errorHandler(errorMessages, ex);
		}

		return createMAV(matchesDTO);
	}

	@RequestMapping("/ds/championship/addMatch")
	public ModelAndView addMatch(AddMatchParam parameter, HttpServletRequest request) {
		List<String> errorMessages = adminController.addMatch(parameter, request);

		ModelAndView roundDetails = roundController.getRoundDetails(parameter.getRoundId(), errorMessages, request);
		return roundDetails;
	}

	private ModelAndView createMAV(MatchesDTO matchesDTO) {
		ModelAndView mav = new ModelAndView(VIEW_NAME);
		mav.addObject(FRONTEND_VARIABLE_MATCH_LIST, matchesDTO);
		return mav;
	}
}
