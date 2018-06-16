package com.dt.betting.controller.matchdetails;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dt.betting.controller.BettingAppController;
import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.DataAlreadyExistsException;
import com.dt.betting.db.repository.DataNotExistsInRepositoryException;
import com.dt.betting.db.repository.inmemory.MatchDataRepository;
import com.dt.betting.score.ScoreCalculator;
import com.dt.betting.user.UserDoesNotExistsException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MatchDetailsController extends BettingAppController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MatchDetailsController.class);

	private static final String VIEW_NAME = "bettingPage";
	private static final String FRONTEND_VARIABLE_MATCH_DATA = "matchData";

	@Autowired
	private MatchDataRepository matchRepository;
	@Autowired
	private MatchDTOConverter matchDTOConverter;
	@Autowired
	private ScoreCalculator scoreCalculator;

	@RequestMapping("/ps/matchDetails")
	public void psMatchData(Long matchId, HttpServletRequest request, HttpServletResponse response) {
		MatchDataDTO bettingDataDTO = getMatchData(new ArrayList<String>(), matchId, request);
		sendJsonResponse(bettingDataDTO, response);
	}

	@RequestMapping("/ds/matchDetails")
	public ModelAndView displayBettingPage(Long matchId, HttpServletRequest request) {
		MatchDataDTO bettingDataDTO = getMatchData(new ArrayList<String>(), matchId, request);
		return createMAV(bettingDataDTO);
	}

	public ModelAndView displayBettingPage(List<String> previousMessages, Long matchId, HttpServletRequest request) {
		MatchDataDTO bettingDataDTO = getMatchData(previousMessages, matchId, request);
		return createMAV(bettingDataDTO);
	}

	private MatchDataDTO getMatchData(List<String> previousMessages, Long matchId, HttpServletRequest request) {
		return getMatchData(previousMessages, matchId, request, doNothing());
	}

	private MatchDataDTO getMatchData(List<String> previousMessages, Long matchId, HttpServletRequest request, MatchDataFunction function) {
		MatchDataDTO bettingData = new MatchDataDTO();
		if (previousMessages != null) {
			bettingData.getErrorMessages().addAll(previousMessages);
		}

		try {
			User loggedUser = userService.getLoggedUser(request);
			Match match = matchRepository.getById(matchId);

			function.run(match, loggedUser);

			Bet matchResult = match.getResult();
			if (matchResult != null) {
				for (Bet bet : match.getBets()) {
					bet.setScore(scoreCalculator.calculateScore(match.getPossibleBetPieces(), matchResult, bet));
				}
			}

			bettingData = matchDTOConverter.convert(match, loggedUser);

		} catch (DataNotExistsInRepositoryException | UserDoesNotExistsException | DataAlreadyExistsException ex) {
			errorHandler(bettingData.getErrorMessages(), ex);
		}
		return bettingData;
	}

	private MatchDataFunction doNothing() {
		return (match, user) -> {
		};
	}

	private ModelAndView createMAV(MatchDataDTO bettingData) {
		ModelAndView mav = new ModelAndView(VIEW_NAME);
		mav.addObject(FRONTEND_VARIABLE_MATCH_DATA, bettingData);
		return mav;
	}

	private void sendJsonResponse(MatchDataDTO bettingDataDTO, HttpServletResponse response) {
		try (PrintWriter writer = response.getWriter()) {
			writer.write(createJsonString(bettingDataDTO));
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
			try {
				response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			} catch (IOException e1) {
				LOGGER.error(e1.getMessage());
			}
		}
	}

	private String createJsonString(MatchDataDTO bettingDataDTO) throws JsonProcessingException {
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(bettingDataDTO);
	}

	private static interface MatchDataFunction {
		public void run(Match match, User myUser) throws DataNotExistsInRepositoryException, UserDoesNotExistsException, DataAlreadyExistsException;
	}
}
