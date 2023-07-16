package com.fantasy.team.request.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PlayerInfoListOfMatch {
	@Id
	private String id;
	private String matchId;
	
	List<PlayerInfo> playersList;
	private double pitchesInFavorOfBatsman = 50.0;
	private double pitchesInFavorOfBowler = 50.0;

	private Date createdDate;
	private String createdDateString;
	private List<ErrorInfo> errorList;
}
