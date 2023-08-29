package me.topu.api.repository;

import me.topu.api.model.ChatFilter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ChatFilterRepository extends MongoRepository<ChatFilter, String> {
}
