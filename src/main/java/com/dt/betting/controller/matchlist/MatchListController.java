package com.dt.betting.controller.matchlist;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dt.betting.controller.matchdetails.MatchDTOConverter;
import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.inmemory.MatchDataRepository;
import com.dt.betting.user.UserDoesNotExistsException;
import com.dt.betting.user.UserService;

@Controller
public class MatchListController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MatchListController.class);

	private static final String VIEW_NAME = "matchListPage";

	private static final String FRONTEND_VARIABLE_MATCH_LIST = "dataset";

	@Autowired
	private MatchDataRepository matchRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private MatchDTOConverter matchDTOConverter;

	@RequestMapping("/ds/matchList")
	public ModelAndView displayBettingPage(HttpServletRequest request) {
		List<String> errorMessages = new ArrayList<>();

		MatchesDTO matchesDTO = new MatchesDTO();

		try {
			User loggedUser = userService.getLoggedUser(request);

			matchRepository.listData().stream().map(m -> matchDTOConverter.convert(m, loggedUser)).forEach(matchesDTO::addMatch);

		} catch (UserDoesNotExistsException ex) {
			errorMessages.add(ex.getMessage());
			LOGGER.error(ex.getMessage());
		}

		return createMAV(matchesDTO);
	}

	private ModelAndView createMAV(MatchesDTO matchesDTO) {
		ModelAndView mav = new ModelAndView(VIEW_NAME);
		mav.addObject(FRONTEND_VARIABLE_MATCH_LIST, matchesDTO);
		return mav;
	}
}
