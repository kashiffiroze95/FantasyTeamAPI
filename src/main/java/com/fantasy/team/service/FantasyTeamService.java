package com.fantasy.team.service;

import java.util.List;

import com.fantasy.team.domain.TopTeam;
import com.fantasy.team.request.model.PlayerInfoListOfMatch;

public interface FantasyTeamService {

	List<TopTeam> createListOfFantasyTeam(String matchId);

}
