package com.shekhawat.journalApp.repository;

import com.shekhawat.journalApp.entity.ConfigEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigRepository extends MongoRepository<ConfigEntity, ObjectId> {
}
