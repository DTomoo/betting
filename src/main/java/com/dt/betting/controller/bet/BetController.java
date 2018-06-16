package com.dt.betting.controller.bet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dt.betting.controller.BettingAppController;
import com.dt.betting.controller.matchdetails.MatchDetailsController;
import com.dt.betting.db.domain.Bet;
import com.dt.betting.db.domain.Match;
import com.dt.betting.db.domain.User;
import com.dt.betting.db.domain.bet.BetPieceType;
import com.dt.betting.db.domain.bet.FinalResultBetPiece;
import com.dt.betting.db.domain.bet.ScoreDifferenceBetPiece;
import com.dt.betting.db.domain.bet.ScoreGuestBetPiece;
import com.dt.betting.db.domain.bet.ScoreHomeBetPiece;
import com.dt.betting.db.domain.bet.WinnerBetPiece;
import com.dt.betting.db.repository.DataAlreadyExistsException;
import com.dt.betting.db.repository.DataNotExistsInRepositoryException;
import com.dt.betting.db.repository.inmemory.BetDataRepository;
import com.dt.betting.db.repository.inmemory.MatchDataRepository;
import com.dt.betting.db.repository.inmemory.UserDataRepository;
import com.dt.betting.user.UserDoesNotExistsException;
import com.dt.betting.user.UserService;

@Controller
public class BetController extends BettingAppController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MatchDetailsController.class);

	@Autowired
	private MatchDetailsController matchDetailsController;
	@Autowired
	private UserService userService;
	@Autowired
	private BetDataRepository betDataRepository;
	@Autowired
	private UserDataRepository userDataRepository;
	@Autowired
	private MatchDataRepository matchDataRepository;

	@RequestMapping("/ds/matchAction/addBet")
	public ModelAndView addBet(NewBetParam newBetParam, HttpServletRequest request) {
		LOGGER.info("Add new bet: " + newBetParam);
		List<String> errorMessages = new ArrayList<>();
		try {
			User loggedUser = userService.getLoggedUser(request);

			Match match = matchDataRepository.getById(newBetParam.getMatchId());

			Bet bet = convert(new Bet(), newBetParam, match, request);
			bet.setMatchId(match.getId());
			bet.setOwner(loggedUser);
			bet.setTimeStamp(LocalDateTime.now());
			bet = betDataRepository.addBet(bet);

			match.getBets().add(bet);
			matchDataRepository.update(match);

			loggedUser.getBetIds().add(bet.getId());
			userDataRepository.update(loggedUser);

		} catch (UserDoesNotExistsException | DataNotExistsInRepositoryException | RuntimeException | DataAlreadyExistsException ex) {
			errorHandler(errorMessages, ex);
		}

		// TODO: allow only the possible bets!
		return matchDetailsController.displayBettingPage(errorMessages, newBetParam.getMatchId(), request);
	}

	@RequestMapping("/ds/matchAction/editBet")
	public ModelAndView editBet(NewBetParam newBetParam, HttpServletRequest request) {
		LOGGER.info("Edit existing bet" + newBetParam);
		List<String> errorMessages = new ArrayList<>();
		try {
			User loggedUser = userService.getLoggedUser(request);

			Match match = matchDataRepository.getById(newBetParam.getMatchId());

			Bet betOfUser = match.getBets().stream().filter(b -> b.isTheBetOf(loggedUser)).findFirst().orElseThrow(DataNotExistsInRepositoryException::new);

			Bet bet = convert(betOfUser, newBetParam, match, request);

			betDataRepository.update(bet);

		} catch (UserDoesNotExistsException | DataNotExistsInRepositoryException | RuntimeException ex) {
			errorHandler(errorMessages, ex);
		}

		// TODO: allow only the possible bets!
		return matchDetailsController.displayBettingPage(errorMessages, newBetParam.getMatchId(), request);
	}

	private Bet convert(Bet bet, NewBetParam parameter, Match match, HttpServletRequest request) {
		try {

			if (parameter.getBet().containsKey(BetPieceType.SCORE_HOME)) {
				String scoreHomeStr = parameter.getBet().get(BetPieceType.SCORE_HOME);
				bet.putBetPiece(new ScoreHomeBetPiece(Long.parseLong(scoreHomeStr)));
			}

			if (parameter.getBet().containsKey(BetPieceType.SCORE_GUEST)) {
				String scoreGuestStr = parameter.getBet().get(BetPieceType.SCORE_GUEST);
				bet.putBetPiece(new ScoreGuestBetPiece(Long.parseLong(scoreGuestStr)));
			}

			if (parameter.getResultScore1() != null && parameter.getResultScore2() != null) {
				bet.putBetPiece(new FinalResultBetPiece(parameter.getResultScore1(), parameter.getResultScore2()));
			}

			if (parameter.getBet().containsKey(BetPieceType.SCORE_DIFFERENCE)) {
				String scoreDiffStr = parameter.getBet().get(BetPieceType.SCORE_DIFFERENCE);
				bet.putBetPiece(new ScoreDifferenceBetPiece(Long.parseLong(scoreDiffStr)));
			}

			if (parameter.getBet().containsKey(BetPieceType.SCORE_SUMMARY)) {
				String scoreSumStr = parameter.getBet().get(BetPieceType.SCORE_SUMMARY);
				bet.putBetPiece(new ScoreDifferenceBetPiece(Long.parseLong(scoreSumStr)));
			}

			if (parameter.getBet().containsKey(BetPieceType.WINNER)) {
				String winner = parameter.getBet().get(BetPieceType.WINNER);
				Long winnerTeamId = null;
				if (StringUtils.isNotBlank(winner)) {
					winnerTeamId = Long.parseLong(winner);
					if (!match.getTeam1().getId().equals(winnerTeamId) && !match.getTeam2().getId().equals(winnerTeamId)) {
						throw new RuntimeException();
					}
				}
				bet.putBetPiece(new WinnerBetPiece(winnerTeamId));
			}
			return bet;
		} catch (

		Exception ex) {
			throw new RuntimeException("Hiba történt a tipp létrehozásakor!");
		}
	}

	// @RequestMapping("/ds/matchAction/editBet")
	// public ModelAndView editBet(EditBetParam editParam, HttpServletRequest
	// request) {
	// LOGGER.info("Edit bet:" + editParam);
	// MatchDataDTO bettingDataDTO = getMatchData(editParam.getMatchId(), request,
	// editBet(editParam));
	// return createMAV(bettingDataDTO);
	// }
}
