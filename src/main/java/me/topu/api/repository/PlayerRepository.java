package me.topu.api.repository;

import me.topu.api.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PlayerRepository extends MongoRepository<Player, String> {

    Player findByUuid(String uuid);

}
