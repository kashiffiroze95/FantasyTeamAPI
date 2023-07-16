package com.fantasy.team.service.helper;

import java.util.ArrayList;
import java.util.List;

import com.fantasy.team.request.model.PlayerInfo;
import com.fantasy.team.request.model.Team;

public class TeamCombinationGeneratorHelper {
	public static List<Team> generateAllTeamCombinations(List<PlayerInfo> playerList) {
		List<Team> allCombinations = new ArrayList<>();
		generateTeamCombinations(playerList, new ArrayList<PlayerInfo>(), allCombinations, "Team");
		return allCombinations;
	}

	private static void generateTeamCombinations(List<PlayerInfo> remainingPlayers, List<PlayerInfo> currentCombination,
			List<Team> allCombinations, String teamNamePrefix) {
		if (currentCombination.size() == 11) {
			Team team = new Team();
			team.setTeamName(teamNamePrefix + "-" + (allCombinations.size() + 1));
			team.setPlayerListforResponse(new ArrayList<>(currentCombination));
			team.setTotalFantasyPointOfTeam(currentCombination.stream().mapToDouble(PlayerInfo::getOverallScore).sum());
			allCombinations.add(team);
			return;
		}

		for (int i = 0; i < remainingPlayers.size(); i++) {
			PlayerInfo player = remainingPlayers.get(i);
			List<PlayerInfo> newRemainingPlayers = new ArrayList<>(
					remainingPlayers.subList(i + 1, remainingPlayers.size()));
			List<PlayerInfo> newCurrentCombination = new ArrayList<>(currentCombination);
			newCurrentCombination.add(player);
			generateTeamCombinations(newRemainingPlayers, newCurrentCombination, allCombinations, teamNamePrefix);
		}
	}
}
