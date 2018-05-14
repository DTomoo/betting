package com.dt.betting.match;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dt.betting.db.repository.DataAlreadyExistsException;
import com.dt.betting.db.repository.DataNotExistsInRepositoryException;
import com.dt.betting.db.repository.inmemory.AddBetDTO;
import com.dt.betting.db.repository.inmemory.BetDataRepository;
import com.dt.betting.db.repository.inmemory.MatchDataRepository;
import com.dt.betting.user.UserDoesNotExistsException;
import com.dt.betting.user.UserService;

@Controller
public class BettingController {

	private static final String VIEW_NAME = "betting/bettingPage";
	private static final String FRONTEND_VARIABLE_MATCH_DATA = "match";
	private static final String FRONTEND_VARIABLE_USER = "user";
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
		return handleMethod((bettingData) -> {

			bettingData.setUser(userService.getLoggedUser(request));
			bettingData.setMatch(matchRepository.getById(matchId));
		});
	}

	@RequestMapping("/ds/betting/addBet")
	public ModelAndView addBet(AddBetParam addBetParam, HttpServletRequest request) {
		return handleMethod((bettingData) -> {

			bettingData.setUser(userService.getLoggedUser(request));
			bettingData.setMatch(matchRepository.getById(addBetParam.getMatchId()));
			bettingData.setUserBet(betRepository.addBet(createAddBetDTO(addBetParam, bettingData)));
		});
	}

	private ModelAndView handleMethod(MethodLogic function) {
		BettingDataDTO bettingData = new BettingDataDTO();

		try {
			function.fillBettingData(bettingData);

		} catch (DataNotExistsInRepositoryException ex) {
			bettingData.addErrorMsg(ex.getMessage());
		} catch (UserDoesNotExistsException e) {
			bettingData.addErrorMsg(e.getMessage());
		} catch (DataAlreadyExistsException e) {
			bettingData.addErrorMsg(e.getMessage());
		}
		return createMAV(bettingData);
	}

	private ModelAndView createMAV(BettingDataDTO bettingData) {
		ModelAndView mav = new ModelAndView(VIEW_NAME);
		mav.addObject(FRONTEND_VARIABLE_USER, bettingData.getUser());
		mav.addObject(FRONTEND_VARIABLE_MATCH_DATA, bettingData.getMatch());
		mav.addObject(FRONTEND_VARIABLE_USER_BET, bettingData.getUserBet());
		mav.addObject(FRONTEND_VARIABLE_OTHER_BETS, bettingData.getOtherBets());
		mav.addObject(FRONTEND_VARIABLE_ERROR_MESSAGES, bettingData.getErrorMessages());
		return mav;
	}

	private AddBetDTO createAddBetDTO(AddBetParam addBetParam, BettingDataDTO bettingData) {
		AddBetDTO addBetDTO = new AddBetDTO();
		addBetDTO.setScore1(addBetParam.getScore1());
		addBetDTO.setScore2(addBetParam.getScore2());
		addBetDTO.setMatch(bettingData.getMatch());
		addBetDTO.setUser(bettingData.getUser());
		return addBetDTO;
	}

	private static interface MethodLogic {
		public void fillBettingData(BettingDataDTO bettingDataDTO)
				throws DataNotExistsInRepositoryException, UserDoesNotExistsException, DataAlreadyExistsException;
	}
}
