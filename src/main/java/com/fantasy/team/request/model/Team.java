package com.fantasy.team.request.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Team {
	private String teamName;
	List<PlayerInfo> playerListforResponse;
	private double totalFantasyPointOfTeam = 0.0;
}
