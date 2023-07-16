package com.fantasy.team.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fantasy.team.domain.MatchInfo;
import com.fantasy.team.domain.TeamInfo;
import com.fantasy.team.domain.TopTeam;
import com.fantasy.team.request.model.PlayerInfo;
import com.fantasy.team.request.model.PlayerInfoListOfMatch;
import com.fantasy.team.service.FantasyTeamService;
import com.fantasy.team.service.JsonGeneratorService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@RestController
@RequestMapping("/api")
public class FantasyTeamController {

	@Autowired
	FantasyTeamService fantasyTeamService;

	@Autowired
	JsonGeneratorService jsonGeneratorService;

	@GetMapping("/get")
	public String getMessage() {
		return "test...";
	}

	
	//api to create top fantasy team
	@GetMapping("/create/teams/{matchId}")
	public ResponseEntity<Object> createFantasyTeam(@PathVariable String matchId) {
		
		List<TopTeam> topFantasyTeamsList = new ArrayList<TopTeam>();
		// call service method
		topFantasyTeamsList = fantasyTeamService.createListOfFantasyTeam(matchId);

		return new ResponseEntity<Object>(topFantasyTeamsList, HttpStatus.OK);
	}

	
	
	//api to create Team JSON - containing teamName and list of playerName
	@GetMapping("/create/playing11/json")
	public ResponseEntity<Object> createPlaying11Json(@RequestBody String playerNameSeperatedByComma) {
		
		TeamInfo player11Json = new TeamInfo();
		// call service method
		player11Json = jsonGeneratorService.createPlayer11Json(playerNameSeperatedByComma, player11Json);	

		return new ResponseEntity<Object>(player11Json, HttpStatus.OK);
	}
	
	
	//save Team JSON
	@PostMapping("/save/playing11/json")
	public ResponseEntity<Object> createPlaying11Json(@RequestBody TeamInfo player11Json) {
		
		// call service method to save
		player11Json = jsonGeneratorService.savePlayer11Json(player11Json);	
		return new ResponseEntity<Object>(player11Json, HttpStatus.OK);
	}
	
	
	//get Team JSON
	@GetMapping("/get/playing11/json/{matchId}")
	public ResponseEntity<Object> getPlaying11Json(@PathVariable String matchId) {
		List<TeamInfo> player11JsonList = new ArrayList<TeamInfo>();
		player11JsonList = jsonGeneratorService.getPlayer11Json(matchId);	
		if (player11JsonList != null && player11JsonList.size() > 0) {
			return new ResponseEntity<Object>(player11JsonList, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("No record found", HttpStatus.OK);
		}
	}
	
	
	// api to save MatchInfo
	@PutMapping("/save/matchinfo/{matchId}")
	public ResponseEntity<Object> saveMatchInfo(@PathVariable String matchId) {

		MatchInfo matchInfo = new MatchInfo();
		
		// call service method to save MatchInfo
		matchInfo = jsonGeneratorService.saveMatchInfo(matchId);

		return new ResponseEntity<Object>(matchInfo, HttpStatus.OK);
	}

	
	// get save MatchInfo by matchId
	@GetMapping("/get/matchinfo/{matchId}")
	public ResponseEntity<Object> getMatchInfo(@PathVariable String matchId) {

		MatchInfo matchInfo = new MatchInfo();
		matchInfo = jsonGeneratorService.getMatchInfo(matchId);
		if (matchInfo != null) {
			return new ResponseEntity<Object>(matchInfo, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("No record found", HttpStatus.OK);
		}	
	}

	
	// update playerName and default value to PlayerInfoListOfMatch for given matchId
	@PutMapping("/update/playerInfoList/{matchId}")
	public ResponseEntity<Object> savePlayerInfoListOfMatch(@PathVariable String matchId) {

		PlayerInfoListOfMatch playerInfoListOfMatch = new PlayerInfoListOfMatch();
		playerInfoListOfMatch = jsonGeneratorService.savePlayerInfoListOfMatch(matchId);
		if (playerInfoListOfMatch != null) {
			return new ResponseEntity<Object>(playerInfoListOfMatch, HttpStatus.OK);
		} else {
			return new ResponseEntity<Object>("No record was updated", HttpStatus.OK);
		}	
	}
}
