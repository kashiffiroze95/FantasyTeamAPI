package com.fantasy.team.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasy.team.domain.TopTeam;
import com.fantasy.team.repository.MatchInfoRepository;
import com.fantasy.team.repository.PlayerInfoRepository;
import com.fantasy.team.repository.TeamInfoRepository;
import com.fantasy.team.request.model.PlayerInfo;
import com.fantasy.team.request.model.PlayerInfoListOfMatch;
import com.fantasy.team.request.model.Team;
import com.fantasy.team.service.FantasyTeamService;
import com.fantasy.team.service.helper.TeamCombinationGeneratorHelper;

@Service
public class FantasyTeamServiceImpl implements FantasyTeamService {
	
	@Autowired
	TeamInfoRepository teamInfoRepository;

	@Autowired
	MatchInfoRepository matchInfoRepository;
	
	@Autowired
	PlayerInfoRepository playerInfoRepository;

	private List<Team> allPossibleCombinationTeamList;

	@Override
	public List<TopTeam> createListOfFantasyTeam(String matchId) {
		PlayerInfoListOfMatch playerInfoListOfMatch = new PlayerInfoListOfMatch();
		
		//fetch playInfoList
		playerInfoListOfMatch = playerInfoRepository.findByMatchId(matchId);
		
		// create a method to calculate overall score of a player
		this.calculateOverallScoreForEachPlayer(playerInfoListOfMatch);

		// Select the top 18 players from the playerList based on overallScore
		List<PlayerInfo> Top18PlayersList = selectTop18Players(playerInfoListOfMatch.getPlayersList());

		// Generate all possible team combinations of 11 players teamSize-11
		// Calculate the total fantasy points for each team.
		allPossibleCombinationTeamList = TeamCombinationGeneratorHelper.generateAllTeamCombinations(Top18PlayersList);

		// Select the top 30 teams based on their total fantasy points.
		List<Team> top30Teams = selectTop30Teams(allPossibleCombinationTeamList);

		//map List<Team> to List<TopTeam>
		List<TopTeam> topTeamList = mapToListTopTeams(top30Teams);

		return topTeamList;
	}

	public void calculateOverallScoreForEachPlayer(PlayerInfoListOfMatch playerList) {
		double overallScore = 0.0;
		double pitchesInFavorOfBatsman = playerList.getPitchesInFavorOfBatsman();
		double pitchesInFavorOfBowler = playerList.getPitchesInFavorOfBowler();

		if (playerList != null) {
			for (PlayerInfo playerInfo : playerList.getPlayersList()) {

				// calculate overallScore based on AvgFantasyPoints
				overallScore = overallScore + 0.5 * playerInfo.getAvgFantasyPoints();

				double consistencyInLast4Match = playerInfo.getConsistencyInLast4Match();
				double avgPointInLast4Matches = playerInfo.getPerformanceLast4Matches();

				// calculate overallScore based on Consitency and Performance in last 4 match
				overallScore = overallScore + (avgPointInLast4Matches * (consistencyInLast4Match / 4));

				// calculate overallScore based on gut feeling out of 10
				overallScore = overallScore + overallScore * (playerInfo.getGutFeeling() / 10) * 0.5;

				// calculate overallScore based on pitch report
				if (playerInfo.getIsBatsman() != null && playerInfo.getIsBatsman().equals(Boolean.TRUE)) {
					overallScore = overallScore + overallScore * ((pitchesInFavorOfBatsman / 100) * 3);
				}
				if (playerInfo.getIsBowler() != null && playerInfo.getIsBowler().equals(Boolean.TRUE)) {
					overallScore = overallScore + overallScore * ((pitchesInFavorOfBowler / 100) * 2);
				} else// isAllRounder
				{
					overallScore = overallScore * 2;
				}

				// set overallScore for each player
				playerInfo.setOverallScore(overallScore);
			}

		}

	}

	public static List<PlayerInfo> selectTop18Players(List<PlayerInfo> playerList) {
		// Sort the players in descending order of their overall score
		playerList.sort(Comparator.comparingDouble(PlayerInfo::getOverallScore).reversed());

		// Select the top 18 players from the sorted list
		List<PlayerInfo> top18Players = new ArrayList<>();
		for (int i = 0; i < 18; i++) {
			top18Players.add(playerList.get(i));
		}

		return top18Players;
	}

	public static List<Team> selectTop30Teams(List<Team> allPossibleCombinationTeamList) {
		List<Team> top30Teams = allPossibleCombinationTeamList.stream()
				.sorted(Comparator.comparingDouble(Team::getTotalFantasyPointOfTeam).reversed()).limit(30)
				.collect(Collectors.toList());
		return top30Teams;
	}
	
	
	public static List<TopTeam> mapToListTopTeams(List<Team> teams) {
		int[] i = { 1 };
		List<TopTeam> topTeams = teams.stream().map(team -> {
			TopTeam topTeam = new TopTeam();
			topTeam.setTeamName("Team-" + i[0]);
			List<String> playerNameList = team.getPlayerListforResponse().stream().map(PlayerInfo::getPlayerName)
					.collect(Collectors.toList());
			topTeam.setPlayerNameList(playerNameList);
			i[0]++;
			return topTeam;
		}).collect(Collectors.toList());
		return topTeams;
	}

}
