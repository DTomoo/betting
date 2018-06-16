package com.dt.betting.controller.userbet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dt.betting.controller.BettingAppController;
import com.dt.betting.controller.matchdetails.BetStatus;
import com.dt.betting.controller.matchdetails.BetStatusCalculator;
import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.DataNotExistsInRepositoryException;
import com.dt.betting.db.repository.inmemory.MatchDataRepository;
import com.dt.betting.db.repository.inmemory.UserDataRepository;
import com.dt.betting.user.UserDoesNotExistsException;

@Controller
public class UserBetController extends BettingAppController {

	private static final String VIEW_NAME = "userBetPage";

	private static final String FRONTEND_VARIABLE_USER_DATA = "userData";
	private static final String FRONTEND_VARIABLE_ERROR_MESSAGES = "errorMessages";

	@Autowired
	private UserDataRepository userRepository;
	@Autowired
	private MatchDataRepository matchRepository;
	@Autowired
	private BetStatusCalculator betStatusCalculator;
	@Autowired
	private UserBetDtoConverter userBetController;

	@RequestMapping("/ds/userBets")
	public ModelAndView dsUserData(@RequestParam("userId") Long userId, HttpServletRequest request) {
		List<String> errorMessages = new ArrayList<>();
		UserDataDTO userDataDTO = null;
		try {
			User loggedUser = userService.getLoggedUser(request);

			userDataDTO = getUserBets(userId, loggedUser);

		} catch (DataNotExistsInRepositoryException | UserDoesNotExistsException ex) {
			errorHandler(errorMessages, ex);
		}

		return createMAV(userDataDTO, errorMessages);
	}

	private UserDataDTO getUserBets(Long userId, User loggedUser) throws DataNotExistsInRepositoryException {

		User user = userRepository.getById(userId);

		UserDataDTO userDataDTO = new UserDataDTO();
		userDataDTO.setName(user.getName());
		userDataDTO.setBets(filterBetsBy(userId, loggedUser));
		return userDataDTO;
	}

	private List<BetDTO> filterBetsBy(Long userId, User loggedUser) {
		if (userId == null) {
			return Arrays.asList();
		}

		List<BetDTO> bets = new ArrayList<>();
		for (Match match : matchRepository.listData()) {

			Optional<Bet> betOfUser = getBetOfUser(match, userId);
			if (betOfUser.isPresent()) {
				Optional<Bet> loggedUserBet = getBetOfUser(match, loggedUser.getId());
				BetStatus loggedUserBetStatus = betStatusCalculator.getStatusOf(loggedUserBet.orElse(null));
				bets.add(userBetController.convert(match, betOfUser.get(), loggedUserBetStatus));
			}
		}
		return bets;
	}

	private Optional<Bet> getBetOfUser(Match match, Long userId) {
		return match.getBets().stream().filter(bet -> bet.isTheBetOfUser(userId)).findFirst();
	}

	private ModelAndView createMAV(UserDataDTO userDTO, List<String> errorMessages) {
		ModelAndView mav = new ModelAndView(VIEW_NAME);
		mav.addObject(FRONTEND_VARIABLE_USER_DATA, userDTO);
		mav.addObject(FRONTEND_VARIABLE_ERROR_MESSAGES, errorMessages);
		return mav;
	}
}
