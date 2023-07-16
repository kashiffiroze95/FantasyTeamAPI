package com.fantasy.team.service;

import java.util.List;

import com.fantasy.team.domain.MatchInfo;
import com.fantasy.team.domain.TeamInfo;
import com.fantasy.team.request.model.PlayerInfoListOfMatch;

public interface JsonGeneratorService {

	TeamInfo createPlayer11Json(String playerNameSeperatedByComma, TeamInfo player11Json);

	MatchInfo saveMatchInfo(String matchId);

	TeamInfo savePlayer11Json(TeamInfo player11Json);

	List<TeamInfo> getPlayer11Json(String matchId);

	MatchInfo getMatchInfo(String matchId);

	PlayerInfoListOfMatch savePlayerInfoListOfMatch(String matchId);


}
