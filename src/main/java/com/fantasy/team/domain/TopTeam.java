package com.fantasy.team.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TopTeam {
	private String teamName;
	List<String> playerNameList;
}
