package com.fantasy.team.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fantasy.team.request.model.ErrorInfo;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MatchInfo {

	@Id
	private String id;

	private String matchId;
	private String matchName;
	private String Venue;
	private double pitchesInFavorOfBatsman = 50.0;
	private double pitchesInFavorOfBowler = 50.0;

	private List<String> team1PlayerList;
	private List<String> team2PlayerList;
	
	TeamInfo team1Info;
	TeamInfo team2Info;

	private Date createdDate;
	private String createdDateString;
	private List<ErrorInfo> errorList;
}
