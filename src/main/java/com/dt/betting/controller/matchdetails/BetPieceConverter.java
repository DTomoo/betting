package com.dt.betting.controller.matchdetails;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dt.betting.db.domain.Team;
import com.dt.betting.db.domain.bet.BetPiece;
import com.dt.betting.db.domain.bet.BetPieceType;
import com.dt.betting.db.domain.bet.WinnerBetPiece;
import com.dt.betting.db.repository.DataNotExistsInRepositoryException;
import com.dt.betting.db.repository.inmemory.TeamDataRepository;

@Component
public class BetPieceConverter {

	@Autowired
	private TeamDataRepository teamDataRepository;

	public Map<String, String> convert(Map<BetPieceType, BetPiece<?>> map) {
		return map.entrySet().stream().collect(Collectors.toMap(keyMapper(), valueMapper()));
	}

	private Function<Map.Entry<BetPieceType, BetPiece<?>>, String> keyMapper() {
		return entry -> entry.getKey().toString();
	}

	private Function<Map.Entry<BetPieceType, BetPiece<?>>, String> valueMapper() {
		return entry -> {
			BetPiece<?> bp = entry.getValue();
			Object obj;
			if (bp instanceof WinnerBetPiece) {
				Long teamId = ((WinnerBetPiece) bp).getValue();
				obj = getTeamName(teamId);
			} else {
				obj = bp.getValue();
			}
			return obj != null ? String.valueOf(obj) : "";
		};
	}

	private Object getTeamName(Long teamId) {
		Object obj;
		Team team;
		try {
			team = teamDataRepository.getById(teamId);
			obj = team.getName();
		} catch (DataNotExistsInRepositoryException e) {
			obj = null;
		}
		return obj;
	}
}
