package me.topu.api.repository;

import me.topu.api.model.Punishment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface PunishmentRepository extends MongoRepository<Punishment, String> {

    List<Punishment> findByUuid(String uuid);
    List<Punishment> findByUserIp(String userIp);

}
