package me.topu.api.repository;

import me.topu.api.model.PrefixGrant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
public interface PrefixGrantRepository extends MongoRepository<PrefixGrant, String> {

    PrefixGrant findById(String id);
    List<PrefixGrant> findByUuid(String uuid);

}
