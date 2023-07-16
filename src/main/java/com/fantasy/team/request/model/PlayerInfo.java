package com.fantasy.team.request.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PlayerInfo {
	private String playerName;
	private int playerId;
	private int matchesPlayed = 1;
	private double avgFantasyPoints = 1.0;
	private double selectionPercentage = 1.0;
	private double performanceLast4Matches = 1.0;
	private int consistencyInLast4Match = 1;
	private double gutFeeling = 1.0;
	private Boolean isBatsman;
	private Boolean isBowler;
	private Boolean isAllRounder;
	private double overallScore = 1.0;
}
