package com.fantasy.team.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fantasy.team.domain.MatchInfo;
import com.fantasy.team.domain.TeamInfo;
import com.fantasy.team.repository.MatchInfoRepository;
import com.fantasy.team.repository.PlayerInfoRepository;
import com.fantasy.team.repository.TeamInfoRepository;
import com.fantasy.team.request.model.ErrorInfo;
import com.fantasy.team.request.model.PlayerInfo;
import com.fantasy.team.request.model.PlayerInfoListOfMatch;
import com.fantasy.team.service.JsonGeneratorService;

@Service
public class JsonGeneratorServiceImpl implements JsonGeneratorService {

	@Autowired
	TeamInfoRepository teamInfoRepository;

	@Autowired
	MatchInfoRepository matchInfoRepository;
	
	@Autowired
	PlayerInfoRepository playerInfoRepository;

	@Override
	public TeamInfo createPlayer11Json(String playerNameSeperatedByComma, TeamInfo player11Json) {
		
		String[] playerNameList = playerNameSeperatedByComma.split(",");
		// Gson gson = new GsonBuilder().setPrettyPrinting().create();
		// String json = gson.toJson(playerNameList);
		player11Json.setMatchId(playerNameList[0].trim());
		player11Json.setTeamName(playerNameList[1].trim());
		player11Json.setPlayer1(playerNameList[2].trim());
		player11Json.setPlayer2(playerNameList[3].trim());
		player11Json.setPlayer3(playerNameList[4].trim());
		player11Json.setPlayer4(playerNameList[5].trim());
		player11Json.setPlayer5(playerNameList[6].trim());
		player11Json.setPlayer6(playerNameList[7].trim());
		player11Json.setPlayer7(playerNameList[8].trim());
		player11Json.setPlayer8(playerNameList[9].trim());
		player11Json.setPlayer9(playerNameList[10].trim());
		player11Json.setPlayer10(playerNameList[11].trim());
		player11Json.setPlayer11(playerNameList[12].trim());

		return player11Json;
	}

	
	
	@Override
	public MatchInfo saveMatchInfo(String matchId) {

		List<ErrorInfo> errorList = new ArrayList<ErrorInfo>();
		
		MatchInfo matchInfo = matchInfoRepository.findByMatchId(matchId);
		List<TeamInfo> player11JsonList = teamInfoRepository.findByMatchId(matchId);
		
		if (matchInfo == null) {

			matchInfo = new MatchInfo();

			if (player11JsonList.size() == 2) {
				matchInfo = new MatchInfo();
				matchInfo.setMatchId(matchId);
				matchInfo.setMatchName(
						player11JsonList.get(0).getTeamName() + " vs " + player11JsonList.get(1).getTeamName());				
				matchInfo.setTeam1Info(player11JsonList.get(0));
				matchInfo.setTeam2Info(player11JsonList.get(1));
				matchInfoRepository.save(matchInfo);
			} else if (player11JsonList.size() == 1) {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorMessage("Only one team is found with the give matchId. TeamName : "
						+ player11JsonList.get(0).getTeamName() + ", MatchId : "
						+ player11JsonList.get(0).getMatchId());
				errorList.add(errorInfo);
				matchInfo.setErrorList(errorList);
			} else {
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorMessage("No team is found with the given matchId : " + matchId);
				errorList.add(errorInfo);
				matchInfo.setErrorList(errorList);
			}
			
			return matchInfo;
		}else {
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorMessage("MatchInfo is already saved with the given matchId : "+ matchId);
			errorList.add(errorInfo);
			matchInfo.setErrorList(errorList);
		}
		
		return matchInfo;
	}

	
	
	@Override
	public TeamInfo savePlayer11Json(TeamInfo player11Json) {

		List<ErrorInfo> errorList = new ArrayList<ErrorInfo>();
		
		List<TeamInfo> player11JsonList = teamInfoRepository.findByMatchId(player11Json.getMatchId());

		if (player11JsonList.size() < 2) {
			
			//If JSON for team is already saved
			if (player11JsonList.size() == 1 && player11JsonList.get(0) != null
					&& player11JsonList.get(0).getTeamName().equalsIgnoreCase(player11Json.getTeamName())) 
			{
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorMessage("JSON for this Team already saved. TeamName : "
						+ player11JsonList.get(0).getTeamName() + ", MatchId : " + player11JsonList.get(0).getMatchId());
				errorList.add(errorInfo);
				player11Json.setErrorList(errorList);
				return player11Json;
			}
			player11Json.setCreatedDate(new Date());
			player11Json.setCreatedDateString(new Date().toString());
			player11Json = teamInfoRepository.save(player11Json);
		}else 
		{
			ErrorInfo errorInfo = new ErrorInfo();
			errorInfo.setErrorMessage("Two team with this matchId is already saved" + ", MatchId : "
					+ player11JsonList.get(0).getMatchId());
			errorList.add(errorInfo);
			player11Json.setErrorList(errorList);
		}

		return player11Json;
	}



	@Override
	public List<TeamInfo> getPlayer11Json(String matchId) {

		return teamInfoRepository.findByMatchId(matchId);
	}



	@Override
	public MatchInfo getMatchInfo(String matchId) {

		return matchInfoRepository.findByMatchId(matchId);
	}



	@Override
	public PlayerInfoListOfMatch savePlayerInfoListOfMatch(String matchId) {
		
		MatchInfo matchInfo = matchInfoRepository.findByMatchId(matchId);
		PlayerInfoListOfMatch playerInfoListOfMatch = playerInfoRepository.findByMatchId(matchId);
		List<PlayerInfo> playerInfoList = new ArrayList<PlayerInfo>();
		
		if (matchInfo != null && playerInfoListOfMatch == null) {
			playerInfoListOfMatch = new PlayerInfoListOfMatch();
			TeamInfo team1Info;
			TeamInfo team2Info;

			if(matchInfo.getTeam1Info()!=null) {
				team1Info= matchInfo.getTeam1Info();
				playerInfoList = addPlayerToPlayerInfoList(team1Info, playerInfoList);
			}
			
			if(matchInfo.getTeam2Info()!=null) {
				team2Info= matchInfo.getTeam2Info();
				playerInfoList = addPlayerToPlayerInfoList(team2Info, playerInfoList);
			}		

			playerInfoListOfMatch.setMatchId(matchId);
			playerInfoListOfMatch.setPlayersList(playerInfoList);
			playerInfoListOfMatch.setCreatedDate(new Date());
			playerInfoListOfMatch.setCreatedDateString(new Date().toString());
			
			playerInfoListOfMatch = playerInfoRepository.save(playerInfoListOfMatch);
		}
		
		return playerInfoListOfMatch;
	}



	private List<PlayerInfo> addPlayerToPlayerInfoList(TeamInfo teamInfo, List<PlayerInfo> playerInfoList) {
		String[] playerNames = { teamInfo.getPlayer1(), teamInfo.getPlayer2(), teamInfo.getPlayer3(),
				teamInfo.getPlayer4(), teamInfo.getPlayer5(), teamInfo.getPlayer6(), teamInfo.getPlayer7(),
				teamInfo.getPlayer8(), teamInfo.getPlayer9(), teamInfo.getPlayer10(), teamInfo.getPlayer11() };

		for (int i = 0; i < 11; i++) {
			PlayerInfo playerInfo = new PlayerInfo();
			if (playerNames[i] != null && playerNames[i] != "") {
				playerInfo.setPlayerName(playerNames[i]);
				playerInfo.setPlayerId(i + 1);
			}
			playerInfoList.add(playerInfo);
		}

		return playerInfoList;
	}

}
