package me.topu.api.repository;

import me.topu.api.model.ServerGroup;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ServerGroupRepository extends MongoRepository<ServerGroup, String> {
}
