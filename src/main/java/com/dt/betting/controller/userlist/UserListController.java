package com.dt.betting.controller.userlist;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dt.betting.controller.BettingAppController;
import com.dt.betting.controller.InsufficientRightException;
import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.MatchStatus;
import com.dt.betting.db.domain.User;
import com.dt.betting.db.repository.DataAlreadyExistsException;
import com.dt.betting.db.repository.inmemory.MatchDataRepository;
import com.dt.betting.db.repository.inmemory.UserDataRepository;
import com.dt.betting.score.ScoreCalculator;
import com.dt.betting.user.UserDoesNotExistsException;

@Controller
public class UserListController extends BettingAppController {

	private static final String VIEW_NAME = "userListPage";

	private static final String FRONTEND_VARIABLE_ALLOW_NEW_DATA_TO_ADD = "allowNewDataToAdd";
	private static final String FRONTEND_VARIABLE_USERS = "users";
	private static final String FRONTEND_VARIABLE_ERROR_MESSAGES = "errorMessages";

	@Autowired
	private UserDataRepository userDataRepository;
	@Autowired
	private MatchDataRepository matchDataRepository;
	@Autowired
	private ScoreCalculator scoreCalculator;

	@RequestMapping(path = "/ds/user/add")
	public ModelAndView addUser(@RequestParam("userName") String userName, HttpServletRequest request) {
		return handleMethod(addUser(userName), request);
	}

	@RequestMapping("/ds/users")
	public ModelAndView display(HttpServletRequest request) {
		return handleMethod(doNothing(), request);
	}

	private ModelAndView handleMethod(UserExtraFunction userFunction, HttpServletRequest request) {
		User loggedUser = null;
		List<User> listData = new ArrayList<>();
		List<String> errorMessages = new ArrayList<>();
		try {
			loggedUser = userService.getLoggedUser(request);

			userFunction.run(loggedUser);

			listData = userDataRepository.listData();
			calculateUserScores(listData);

		} catch (UserDoesNotExistsException | DataAlreadyExistsException | InsufficientRightException ex) {
			errorHandler(errorMessages, ex);
		}
		return createMAV(loggedUser.isAdmin(), listData, errorMessages);
	}

	private UserExtraFunction addUser(String userName) {
		return loggedUser -> {

			if (!loggedUser.isAdmin()) {
				throw new InsufficientRightException();
			}
			userDataRepository.addUser(userName);
		};
	}

	private static UserExtraFunction doNothing() {
		return (o) -> {
		};
	}

	private void calculateUserScores(List<User> users) {
		List<Match> matches = matchDataRepository.listData();
		Map<Long, AtomicLong> scores = users.stream().collect(Collectors.toMap(User::getId, u -> new AtomicLong(0)));
		
		
		matches
			.stream()
			.filter(match -> match.getStatus() != MatchStatus.NEW && match.getResult() != null)
			.forEach(match -> {
				match.getBets().stream().forEach(userBet -> {
				
					long score = scoreCalculator.calculateScore(match.getPossibleBetPieces(), match.getResult(), userBet);
					scores.get(userBet.getOwner().getId()).addAndGet(score);
				
				});
			});

		users.stream().forEach(user -> {
			user.setScores(scores.get(user.getId()).longValue());
		});

	}

	public ModelAndView createMAV(boolean allowNewDataToAdd, List<User> listData, List<String> errorMessages) {
		ModelAndView mav = new ModelAndView(VIEW_NAME);
		mav.addObject(FRONTEND_VARIABLE_USERS, listData);
		mav.addObject(FRONTEND_VARIABLE_ALLOW_NEW_DATA_TO_ADD, allowNewDataToAdd);
		mav.addObject(FRONTEND_VARIABLE_ERROR_MESSAGES, errorMessages);
		return mav;
	}

	private static interface UserExtraFunction {
		public void run(User loggedUser) throws DataAlreadyExistsException, InsufficientRightException;
	}
}
