package com.dt.betting.match;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.DataAlreadyExistsException;
import com.dt.betting.db.repository.DataNotExistsInRepositoryException;
import com.dt.betting.db.repository.inmemory.BetDataRepository;
import com.dt.betting.db.repository.inmemory.MatchDataRepository;
import com.dt.betting.user.UserDoesNotExistsException;
import com.dt.betting.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class BettingController {

	private static final Logger LOGGER = LoggerFactory.getLogger(BettingController.class);

	private static final String VIEW_NAME = "betting/bettingPage";
	private static final String FRONTEND_VARIABLE_MATCH_DATA = "match";
	private static final String FRONTEND_VARIABLE_USER_BET = "userBet";
	private static final String FRONTEND_VARIABLE_OTHER_BETS = "otherBets";
	private static final String FRONTEND_VARIABLE_ERROR_MESSAGES = "errorMessages";

	@Autowired
	private MatchDataRepository matchRepository;
	@Autowired
	private BetDataRepository betRepository;
	@Autowired
	private UserService userService;

	@RequestMapping("/ds/betting/match")
	public ModelAndView displayBettingPage(Long matchId, HttpServletRequest request) {
		BettingDataDTO bettingDataDTO = getMatchData(matchId, request);
		return createMAV(bettingDataDTO);
	}

	@RequestMapping("/ps/betting/match")
	public void psMatchData(Long matchId, HttpServletRequest request, HttpServletResponse response) {
		BettingDataDTO bettingDataDTO = getMatchData(matchId, request);
		sendJsonResponse(bettingDataDTO, response);
	}

	@RequestMapping("/ds/betting/addBet")
	public ModelAndView addBet(Bet newBet, HttpServletRequest request) {
		BettingDataDTO bettingDataDTO = getMatchData(newBet.getMatchId(), request, (bettingData, match, myUser) -> {
			newBet.setOwner(myUser);

			Bet bet = betRepository.addBet(newBet);
			match.getBets().add(bet);

		});
		return createMAV(bettingDataDTO);
	}

	private BettingDataDTO getMatchData(Long matchId, HttpServletRequest request) {
		return getMatchData(matchId, request, (bettingData, match, user) -> {
		});
	}

	private BettingDataDTO getMatchData(Long matchId, HttpServletRequest request, MethodLogic function) {
		BettingDataDTO bettingData = new BettingDataDTO();

		try {
			User myUser = userService.getLoggedUser(request);
			Match match = matchRepository.getById(matchId);

			function.fillBettingData(bettingData, match, myUser);

			bettingData.setMatch(match, myUser);

		} catch (DataNotExistsInRepositoryException ex) {
			bettingData.addErrorMsg(ex.getMessage());
		} catch (UserDoesNotExistsException e) {
			bettingData.addErrorMsg(e.getMessage());
		} catch (DataAlreadyExistsException e) {
			bettingData.addErrorMsg(e.getMessage());
		}
		return bettingData;
	}

	private ModelAndView createMAV(BettingDataDTO bettingData) {
		ModelAndView mav = new ModelAndView(VIEW_NAME);
		mav.addObject(FRONTEND_VARIABLE_MATCH_DATA, bettingData.getMatch());
		mav.addObject(FRONTEND_VARIABLE_USER_BET, bettingData.getUserBet());
		mav.addObject(FRONTEND_VARIABLE_OTHER_BETS, bettingData.getOtherBets());
		mav.addObject(FRONTEND_VARIABLE_ERROR_MESSAGES, bettingData.getErrorMessages());
		return mav;
	}

	private void sendJsonResponse(BettingDataDTO bettingDataDTO, HttpServletResponse response) {
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

	private String createJsonString(BettingDataDTO bettingDataDTO) throws JsonProcessingException {
		return new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(bettingDataDTO);
	}

	private static interface MethodLogic {
		public void fillBettingData(BettingDataDTO bettingDataDTO, Match match, User myUser)
				throws DataNotExistsInRepositoryException, UserDoesNotExistsException, DataAlreadyExistsException;
	}
}
