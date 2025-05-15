package net.learnSpringBoot.journalApp.repository;

import net.learnSpringBoot.journalApp.entity.Journal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalRepo extends MongoRepository<Journal, ObjectId> {
}
