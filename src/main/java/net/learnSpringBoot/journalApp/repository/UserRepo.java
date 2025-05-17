package net.learnSpringBoot.journalApp.repository;
import net.learnSpringBoot.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<User, ObjectId>{
    User findByUserName(String userName);
    void deleteByUserName(String userName);
}
