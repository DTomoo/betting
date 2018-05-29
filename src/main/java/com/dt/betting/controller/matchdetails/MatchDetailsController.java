package com.dt.betting.controller.matchdetails;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Optional;

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
import com.dt.betting.db.repository.inmemory.UserDataRepository;
import com.dt.betting.user.UserDoesNotExistsException;
import com.dt.betting.user.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class MatchDetailsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MatchDetailsController.class);

	private static final String VIEW_NAME = "bettingPage";
	private static final String FRONTEND_VARIABLE_MATCH_DATA = "matchData";

	@Autowired
	private MatchDataRepository matchRepository;
	@Autowired
	private BetDataRepository betRepository;
	@Autowired
	private UserDataRepository userRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private MatchDTOConverter matchDTOConverter;

	@RequestMapping("/ds/matchDetails")
	public ModelAndView displayBettingPage(Long matchId, HttpServletRequest request) {
		MatchDataDTO bettingDataDTO = getMatchData(matchId, request);
		return createMAV(bettingDataDTO);
	}

	@RequestMapping("/ps/matchDetails")
	public void psMatchData(Long matchId, HttpServletRequest request, HttpServletResponse response) {
		MatchDataDTO bettingDataDTO = getMatchData(matchId, request);
		sendJsonResponse(bettingDataDTO, response);
	}

	@RequestMapping("/ds/matchAction/addBet")
	public ModelAndView addBet(Bet newBet, HttpServletRequest request) {
		LOGGER.info("Add new bet: " + newBet);
		MatchDataDTO bettingDataDTO = getMatchData(newBet.getMatchId(), request, saveBet(newBet));
		return createMAV(bettingDataDTO);
	}

	@RequestMapping("/ds/matchAction/editBet")
	public ModelAndView editBet(EditBetParam editParam, HttpServletRequest request) {
		LOGGER.info("Edit bet:" + editParam);
		MatchDataDTO bettingDataDTO = getMatchData(editParam.getMatchId(), request, editBet(editParam));
		return createMAV(bettingDataDTO);
	}

	private MatchDataDTO getMatchData(Long matchId, HttpServletRequest request) {
		return getMatchData(matchId, request, doNothing());
	}

	private MatchDataDTO getMatchData(Long matchId, HttpServletRequest request, MatchDataFunction function) {
		MatchDataDTO bettingData = new MatchDataDTO();

		try {
			User loggedUser = userService.getLoggedUser(request);
			Match match = matchRepository.getById(matchId);

			function.run(match, loggedUser);

			bettingData = matchDTOConverter.convert(match, loggedUser);

		} catch (DataNotExistsInRepositoryException | UserDoesNotExistsException | DataAlreadyExistsException ex) {
			bettingData.addErrorMessage(ex.getMessage());
			LOGGER.error(ex.getMessage());
		}
		return bettingData;
	}

	private MatchDataFunction saveBet(Bet newBet) {
		return (match, myUser) -> {
			newBet.setOwner(myUser);
			newBet.setTimeStamp(LocalDateTime.now());

			Bet bet = betRepository.addBet(newBet);
			match.getBets().add(bet);

			userRepository.update(myUser);
			matchRepository.update(match);
		};
	}

	private MatchDataFunction editBet(EditBetParam editParam) {
		return (match, myUser) -> {
			Long userId = Long.valueOf(editParam.getUserId());

			if (myUser.isAdmin() || !userId.equals(myUser.getId())) {
				LOGGER.error("{} wanted to hack: {}", myUser.getName(), editParam.toString());
				throw new RuntimeException("Nincs jogod a művelethez!");
			}

			Optional<Bet> userBet = match.getBets().stream().filter(bet -> bet.isTheBetOfUser(userId)).findFirst();
			if (userBet.isPresent()) {
				Bet bet = userBet.get();
				bet.setScore1(editParam.getScore1());
				bet.setScore2(editParam.getScore2());
				bet.setTimeStamp(LocalDateTime.now());
				betRepository.update(bet);
			} else {
				throw new RuntimeException("Nincs még ilyen fogadás rögzítve");
			}
		};
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
