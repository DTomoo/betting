package com.dt.betting.controller.championship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dt.betting.controller.BettingAppController;
import com.dt.betting.controller.admin.AddChampionshipParam;
import com.dt.betting.controller.admin.AddGroupParam;
import com.dt.betting.controller.admin.AddRoundParam;
import com.dt.betting.controller.admin.AdminController;
import com.dt.betting.db.domain.Championship;
import com.dt.betting.db.repository.inmemory.ChampionshipDataRepository;
import com.dt.betting.user.UserDoesNotExistsException;

@Controller
public class ChampionshipListController extends BettingAppController {

	@Autowired
	private ChampionshipDataRepository championshipDataRepository;
	@Autowired
	private AdminController adminController;

	@RequestMapping("/ds/championship")
	public ModelAndView listChampionships(HttpServletRequest request) {
		return listChampionships(Arrays.asList(), request);
	}

	ModelAndView listChampionships(List<String> previousMessages, HttpServletRequest request) {
		List<String> errorMessages = new ArrayList<>(previousMessages);
		List<Championship> championships = Arrays.asList();
		boolean admin = false;
		try {
			championships = championshipDataRepository.listData();

			admin = isAdmin(request);

		} catch (RuntimeException | UserDoesNotExistsException ex) {
			errorHandler(errorMessages, ex);
		}

		ModelAndView mav = new ModelAndView("championshipListPage");
		mav.addObject("championships", championships);
		mav.addObject("errorMessages", errorMessages);
		mav.addObject("admin", admin);
		return mav;
	}

	@RequestMapping("/ds/championship/addChampionship")
	public ModelAndView addChampionship(AddChampionshipParam addChampionshipParam, HttpServletRequest request) {

		List<String> errorMessages = adminController.addChampionship(addChampionshipParam, request);
		return listChampionships(errorMessages, request);
	}

	@RequestMapping("/ds/championship/addRound")
	public ModelAndView addChampionship(AddRoundParam addRoundParam, HttpServletRequest request) {

		List<String> errorMessages = adminController.addRound(addRoundParam, request);
		ModelAndView mav = listChampionships(errorMessages, request);
		mav.addObject("expandedChampionshipId", addRoundParam.getChampionshipId());
		return mav;
	}

	@RequestMapping("/ds/championship/addGroup")
	public ModelAndView addChampionship(AddGroupParam addGroupParam, HttpServletRequest request) {

		List<String> errorMessages = adminController.addGroup(addGroupParam, request);
		ModelAndView mav = listChampionships(errorMessages, request);
		mav.addObject("expandedChampionshipId", addGroupParam.getChampionshipId());
		return mav;
	}
}
