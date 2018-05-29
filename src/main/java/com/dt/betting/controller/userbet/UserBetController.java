package com.dt.betting.controller.userbet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.DataNotExistsInRepositoryException;
import com.dt.betting.db.repository.inmemory.MatchDataRepository;
import com.dt.betting.db.repository.inmemory.UserDataRepository;

@Controller
public class UserBetController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserBetController.class);

	private static final String VIEW_NAME = "userBetPage";

	private static final String FRONTEND_VARIABLE_USER_DATA = "userData";
	private static final String FRONTEND_VARIABLE_ERROR_MESSAGES = "errorMessages";

	@Autowired
	private UserDataRepository userRepository;
	@Autowired
	private MatchDataRepository matchRepository;

	@RequestMapping("/ds/userBets")
	public ModelAndView dsUserData(@RequestParam("userId") Long userId, HttpServletRequest request, HttpServletResponse response) {
		List<String> errorMessages = new ArrayList<>();
		UserDataDTO userDataDTO = null;
		try {

			userDataDTO = getUserBets(userId);

		} catch (DataNotExistsInRepositoryException ex) {
			errorMessages.add(ex.getMessage());
			LOGGER.error(ex.getMessage());
		}

		return createMAV(userDataDTO, errorMessages);
	}

	private List<BetDTO> filterBetsBy(Long userId) {
		if (userId == null) {
			return Arrays.asList();
		}

		List<BetDTO> bets = new ArrayList<>();
		for (Match match : matchRepository.listData()) {
			for (Bet bet : match.getBets()) {
				if (bet.isTheBetOfUser(userId)) {
					bets.add(new BetDTO(match, bet));
				}
			}
		}
		return bets;
	}

	private UserDataDTO getUserBets(Long userId) throws DataNotExistsInRepositoryException {

		User user = userRepository.getById(userId);

		UserDataDTO userDataDTO = new UserDataDTO();
		userDataDTO.setName(user.getName());
		userDataDTO.setBets(filterBetsBy(userId));
		return userDataDTO;
	}

	private ModelAndView createMAV(UserDataDTO userDTO, List<String> errorMessages) {
		ModelAndView mav = new ModelAndView(VIEW_NAME);
		mav.addObject(FRONTEND_VARIABLE_USER_DATA, userDTO);
		mav.addObject(FRONTEND_VARIABLE_ERROR_MESSAGES, errorMessages);
		return mav;
	}
}
