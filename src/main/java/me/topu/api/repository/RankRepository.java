package me.topu.api.repository;

import me.topu.api.model.Rank;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RankRepository extends MongoRepository<Rank, String> {

    Rank findByRankid(String rankid);

}
