package com.fantasy.team.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fantasy.team.domain.TeamInfo;

@Repository
public interface TeamInfoRepository extends MongoRepository<TeamInfo, String> {

	List<TeamInfo> findByMatchId(String matchId);


}
