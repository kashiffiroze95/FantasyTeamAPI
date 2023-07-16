package com.fantasy.team.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fantasy.team.domain.MatchInfo;

@Repository
public interface MatchInfoRepository extends MongoRepository<MatchInfo, String> {

	MatchInfo findByMatchId(String matchId);


}
