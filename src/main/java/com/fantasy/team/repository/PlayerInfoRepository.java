package com.fantasy.team.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.fantasy.team.request.model.PlayerInfoListOfMatch;

@Repository
public interface PlayerInfoRepository extends MongoRepository<PlayerInfoListOfMatch, String> {

	PlayerInfoListOfMatch findByMatchId(String matchId);

}
