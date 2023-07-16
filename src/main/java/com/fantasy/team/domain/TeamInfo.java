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
public class TeamInfo {
	
	@Id
	private String id;

	private String matchId;
	
	private String teamName;
	private String player1;
	private String player2;
	private String player3;
	private String player4;
	private String player5;
	private String player6;
	private String player7;
	private String player8;
	private String player9;
	private String player10;
	private String player11;
	
	private Date createdDate;
	private String createdDateString;
	private List<ErrorInfo> errorList;
}
